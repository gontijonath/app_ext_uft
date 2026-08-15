package br.edu.uft.estersabino.ui.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import br.edu.uft.estersabino.data.Projeto
import br.edu.uft.estersabino.ui.comum.EspacoSecao
import br.edu.uft.estersabino.ui.comum.ImagemPlaceholder
import br.edu.uft.estersabino.ui.comum.Selo
import br.edu.uft.estersabino.ui.comum.TituloSecao
import br.edu.uft.estersabino.ui.theme.CoresUft

@Composable
fun InicioTela(
    aoAbrirEvento: () -> Unit,
    aoAbrirProjeto: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp, bottom = 32.dp)
    ) {
        Cabecalho()

        EspacoSecao()

        BannerEvento(onClick = aoAbrirEvento)

        EspacoSecao()

        TituloSecao(Conteudo.INICIO_CHAMADA_PROJETOS)
        Spacer(Modifier.height(12.dp))

        Conteudo.projetos.forEachIndexed { indice, projeto ->
            CardProjetoResumo(
                projeto = projeto,
                numero = indice + 1,
                onClick = { aoAbrirProjeto(projeto.id) },
            )
            Spacer(Modifier.height(12.dp))
        }

        Spacer(Modifier.height(8.dp))
        Text(
            text = Conteudo.RODAPE_VERSAO,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun Cabecalho() {
    Column {
        Text(
            text = Conteudo.INICIO_SAUDACAO,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = Conteudo.NOME_PLATAFORMA,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = Conteudo.SUBTITULO_PLATAFORMA,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = Conteudo.INICIO_APRESENTACAO,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

/**
 * Banner do evento na tela inicial. É o único elemento da Início que usa o
 * amarelo cheio — o destaque só funciona porque o resto da tela não compete.
 */
@Composable
private fun BannerEvento(onClick: () -> Unit) {
    val evento = Conteudo.evento
    val projeto = Conteudo.projetoPorId(evento.projetoId)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick),
        color = Color.Transparent,
    ) {
        Box(
            Modifier.background(
                Brush.linearGradient(listOf(CoresUft.Verde, CoresUft.VerdeEscuro))
            )
        ) {
            Column(Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Selo(evento.selo)
                    Spacer(Modifier.width(10.dp))
                    Icon(
                        imageVector = Icons.Filled.AutoAwesome,
                        contentDescription = null,
                        tint = CoresUft.Amarelo,
                        modifier = Modifier.size(18.dp),
                    )
                }
                Spacer(Modifier.height(14.dp))
                Text(
                    text = projeto?.titulo ?: evento.chamada,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "${evento.data} · ${evento.local}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.85f),
                )
                Spacer(Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Ver o evento e o convite em RA",
                        style = MaterialTheme.typography.labelLarge,
                        color = CoresUft.Amarelo,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = CoresUft.Amarelo,
                        modifier = Modifier.size(18.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun CardProjetoResumo(
    projeto: Projeto,
    numero: Int,
    onClick: () -> Unit,
) {
    // Card + clickable em vez de Card(onClick=…): a sobrecarga com onClick já
    // esteve marcada como experimental no Material3, e não vale o risco aqui.
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Column {
            ImagemPlaceholder(
                marca = "$numero",
                altura = 120,
                legenda = "Imagem do projeto",
                modifier = Modifier.padding(10.dp),
            )
            Column(
                Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = projeto.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = projeto.subtitulo,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
