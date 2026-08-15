package br.edu.uft.estersabino.ui.telas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.edu.uft.estersabino.data.Conteudo
import br.edu.uft.estersabino.ui.comum.EspacoSecao

/**
 * Aba "Projetos": abas superiores para alternar entre os três sem sair da tela.
 *
 * Com apenas três projetos, trocar por abas é mais direto que uma lista que
 * navega para outra tela — o visitante compara os projetos com um toque.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjetosTela(
    projetoSelecionadoId: String?,
    modifier: Modifier = Modifier,
) {
    val projetos = Conteudo.projetos
    val indiceInicial = remember(projetoSelecionadoId) {
        projetos.indexOfFirst { it.id == projetoSelecionadoId }.coerceAtLeast(0)
    }
    var abaSelecionada by rememberSaveable(projetoSelecionadoId) {
        mutableIntStateOf(indiceInicial)
    }

    Column(modifier.fillMaxSize()) {
        PrimaryTabRow(selectedTabIndex = abaSelecionada) {
            projetos.forEachIndexed { indice, projeto ->
                Tab(
                    selected = abaSelecionada == indice,
                    onClick = { abaSelecionada = indice },
                    text = {
                        Text(
                            text = projeto.titulo,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    },
                )
            }
        }

        val projeto = projetos[abaSelecionada]
        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp, bottom = 32.dp)
        ) {
            FichaProjeto(projeto = projeto, numero = abaSelecionada + 1)
            EspacoSecao()
            Spacer(Modifier.height(8.dp))
        }
    }
}
