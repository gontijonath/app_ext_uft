package br.edu.uft.estersabino.ui.telas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.edu.uft.estersabino.data.Projeto
import br.edu.uft.estersabino.ui.comum.CampoTexto
import br.edu.uft.estersabino.ui.comum.EspacoSecao
import br.edu.uft.estersabino.ui.comum.ImagemPlaceholder
import br.edu.uft.estersabino.ui.comum.ListaComMarcadores
import br.edu.uft.estersabino.ui.comum.SecaoParagrafo
import br.edu.uft.estersabino.ui.comum.TituloSecao

/**
 * Ficha completa de um projeto de extensão. Usada tanto na aba "Projetos"
 * quanto (em versão resumida) na aba do evento.
 */
@Composable
fun FichaProjeto(
    projeto: Projeto,
    numero: Int,
    modifier: Modifier = Modifier,
    mostrarCapa: Boolean = true,
) {
    Column(modifier.fillMaxWidth()) {
        if (mostrarCapa) {
            ImagemPlaceholder(
                marca = "$numero",
                altura = 190,
                legenda = "Imagem de capa do projeto",
            )
            Spacer(Modifier.height(20.dp))
        }

        Text(
            text = projeto.titulo,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = projeto.subtitulo,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
        )

        EspacoSecao()

        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                CampoTexto("Coordenação", projeto.coordenacao)
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                CampoTexto("Equipe", projeto.equipe)
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                Row {
                    CampoTexto("Local", projeto.localAtuacao, Modifier.weight(1f))
                    Spacer(Modifier.width(12.dp))
                    CampoTexto("Período", projeto.periodo, Modifier.weight(1f))
                }
            }
        }

        EspacoSecao()
        SecaoParagrafo("Objetivo", projeto.objetivo)

        EspacoSecao()
        SecaoParagrafo("Público-alvo", projeto.publicoAlvo)

        EspacoSecao()
        SecaoParagrafo("Como funciona", projeto.metodologia)

        EspacoSecao()
        TituloSecao("Resultados")
        Spacer(Modifier.height(10.dp))
        ListaComMarcadores(projeto.resultados)

        EspacoSecao()
        TituloSecao("Galeria")
        Spacer(Modifier.height(10.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(count = 3) { indice ->
                ImagemPlaceholder(
                    marca = "$numero.${indice + 1}",
                    altura = 110,
                    legenda = "Foto ${indice + 1}",
                    larguraCheia = false,
                    modifier = Modifier.width(160.dp),
                )
            }
        }

        EspacoSecao()
        CampoTexto("Contato", projeto.contato)
    }
}
