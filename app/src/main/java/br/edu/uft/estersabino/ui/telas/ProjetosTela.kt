package br.edu.uft.estersabino.ui.telas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.edu.uft.estersabino.data.Conteudo
import br.edu.uft.estersabino.ui.comum.EspacoSecao
import br.edu.uft.estersabino.ui.theme.CoresUft
import br.edu.uft.estersabino.ui.theme.TemaEsterSabino

/**
 * Aba "Projetos": abas superiores (em pílula, estilo do Claude Design) para
 * alternar entre os três sem sair da tela.
 *
 * Com apenas três projetos, trocar por abas é mais direto que uma lista que
 * navega para outra tela — o visitante compara os projetos com um toque.
 */
@Composable
fun ProjetosTela(
    projetoSelecionadoId: String?,
    modifier: Modifier = Modifier,
) {
    val projetos = Conteudo.projetos
    val indiceInicial = remember(projetoSelecionadoId) {
        projetos.indexOfFirst { it.id == projetoSelecionadoId }.coerceAtLeast(0)
    }
    // `remember` puro, não `rememberSaveable`: o valor salvo pelo mecanismo de
    // saveState/restoreState do NavHost (ao trocar de aba) ficava "restaurando"
    // a pílula da visita anterior a esta tela, ignorando qual projeto a pessoa
    // acabou de escolher no Início — mesma causa do bug de rolagem corrigido
    // antes, agora afetando qual projeto abre.
    var abaSelecionada by remember(projetoSelecionadoId) {
        mutableIntStateOf(indiceInicial)
    }

    Column(modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            projetos.forEachIndexed { indice, projeto ->
                val ativo = abaSelecionada == indice
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { abaSelecionada = indice },
                    color = if (ativo) CoresUft.Verde else CoresUft.TurquesaSuave,
                    shape = RoundedCornerShape(percent = 50),
                ) {
                    Text(
                        text = projeto.sigla,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Black,
                        color = if (ativo) Color.White else CoresUft.Verde,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 11.dp),
                    )
                }
            }
        }

        val projeto = projetos[abaSelecionada]
        // Chaveado pelo projeto: trocar de aba sempre volta o texto pro topo,
        // em vez de manter a rolagem de onde a aba anterior foi deixada.
        val rolagem = remember(projeto.id) { ScrollState(0) }
        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(rolagem)
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp, bottom = 32.dp)
        ) {
            FichaProjeto(projeto = projeto)
            EspacoSecao()
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ProjetosTelaPreview() {
    TemaEsterSabino {
        Surface {
            ProjetosTela(projetoSelecionadoId = null)
        }
    }
}
