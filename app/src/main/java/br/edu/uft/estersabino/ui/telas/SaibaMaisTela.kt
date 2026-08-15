package br.edu.uft.estersabino.ui.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.edu.uft.estersabino.data.Conteudo
import br.edu.uft.estersabino.ui.comum.AvisoSuave
import br.edu.uft.estersabino.ui.comum.EspacoSecao
import br.edu.uft.estersabino.ui.comum.ListaComMarcadores
import br.edu.uft.estersabino.ui.comum.SecaoParagrafo
import br.edu.uft.estersabino.ui.comum.TituloSecao
import br.edu.uft.estersabino.ui.theme.CoresUft

@Composable
fun SaibaMaisTela(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp, bottom = 32.dp)
    ) {
        RetratoHomenageada()

        EspacoSecao()
        SecaoParagrafo(Conteudo.SAIBA_TITULO_NOME, Conteudo.SAIBA_TEXTO_NOME)

        EspacoSecao()
        SecaoParagrafo(Conteudo.SAIBA_TITULO_QUEM, Conteudo.SAIBA_TEXTO_QUEM)

        Spacer(Modifier.height(16.dp))
        ListaComMarcadores(Conteudo.saibaFeitos)

        EspacoSecao()
        SecaoParagrafo(Conteudo.SAIBA_TITULO_LIGACAO, Conteudo.SAIBA_TEXTO_LIGACAO)

        EspacoSecao()
        AvisoSuave(Conteudo.SAIBA_AVISO_DADOS)

        EspacoSecao()
        TituloSecao(Conteudo.SAIBA_TITULO_CREDITOS)
        Spacer(Modifier.height(12.dp))
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Conteudo.saibaCreditos.forEachIndexed { indice, (rotulo, valor) ->
                    if (indice > 0) {
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    }
                    Row(Modifier.fillMaxWidth()) {
                        Text(
                            text = rotulo,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.width(110.dp),
                        )
                        Text(
                            text = valor,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
            }
        }

        EspacoSecao()
        Text(
            text = Conteudo.RODAPE_VERSAO,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

/**
 * Retrato da homenageada.
 *
 * Enquanto não houver uma foto com licença de uso confirmada, mostramos uma
 * marca gráfica com as iniciais. Publicar foto de pessoa real sem autorização
 * não é uma decisão que o protótipo deva tomar sozinho.
 */
@Composable
private fun RetratoHomenageada() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.linearGradient(listOf(CoresUft.AzulInstitucional, CoresUft.AzulMarinho))
            ),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                color = Color.White.copy(alpha = 0.15f),
                shape = RoundedCornerShape(50),
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .padding(14.dp)
                        .size(40.dp),
                )
            }
            Spacer(Modifier.height(14.dp))
            Text(
                text = Conteudo.NOME_PLATAFORMA,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Espaço reservado para o retrato da homenageada",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.75f),
            )
        }
    }
}
