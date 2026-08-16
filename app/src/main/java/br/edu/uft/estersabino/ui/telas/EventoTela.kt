package br.edu.uft.estersabino.ui.telas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.edu.uft.estersabino.data.Conteudo
import br.edu.uft.estersabino.ui.comum.EspacoSecao
import br.edu.uft.estersabino.ui.comum.ManchaDecorativa
import br.edu.uft.estersabino.ui.theme.CoresUft
import br.edu.uft.estersabino.ui.theme.TemaEsterSabino

@Composable
fun EventoTela(
    aoAbrirRa: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val evento = Conteudo.evento
    val projeto = Conteudo.projetoPorId(evento.projetoId)

    // `remember` puro: ao sair da aba e voltar, a tela sempre reabre do topo.
    val rolagem = remember { ScrollState(0) }
    Column(
        modifier
            .fillMaxWidth()
            .verticalScroll(rolagem)
    ) {
        CabecalhoEvento(
            selo = evento.selo,
            titulo = projeto?.titulo ?: evento.chamada,
            chamada = evento.chamada,
        )

        Column(Modifier.padding(horizontal = 20.dp, vertical = 24.dp)) {

            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(22.dp),
                border = BorderStroke(1.dp, CoresUft.BordaCard),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column {
                    LinhaInfo("📅", "Data", evento.data, cor = CoresUft.Turquesa, corClara = CoresUft.TurquesaSuave)
                    HorizontalDivider(color = CoresUft.BordaCard)
                    LinhaInfo("🕒", "Horário", evento.horario, cor = CoresUft.AmareloEscuro, corClara = CoresUft.AmareloSuave)
                    HorizontalDivider(color = CoresUft.BordaCard)
                    LinhaInfo(
                        "📍", "Local", evento.local, evento.localDetalhe,
                        cor = CoresUft.Roxo, corClara = CoresUft.RoxoSuave,
                    )
                }
            }

            EspacoSecao()

            BotaoRealidadeAumentada(
                textoConvite = evento.convite,
                onClick = aoAbrirRa,
            )

            EspacoSecao()
            SecaoComPonto("Programação", evento.programacao, CoresUft.Turquesa)

            EspacoSecao()

            if (projeto != null) {
                FichaProjeto(
                    projeto = projeto,
                    mostrarCapa = false,
                )
            }
        }
    }
}

/**
 * Cabeçalho da tela de evento: mesmo gradiente e manchas decorativas do
 * cabeçalho do app, com o selo torto trazido do estilo importado.
 */
@Composable
private fun CabecalhoEvento(selo: String, titulo: String, chamada: String) {
    Box(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
            .background(Brush.verticalGradient(listOf(CoresUft.Turquesa, CoresUft.VerdeEscuro)))
    ) {
        ManchaDecorativa(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 34.dp, y = (-34).dp),
            tamanho = 150.dp,
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = 20.dp, y = (-14).dp)
                .size(10.dp)
                .clip(RoundedCornerShape(percent = 50))
                .background(CoresUft.Amarelo.copy(alpha = 0.7f))
        )
        Column(Modifier.padding(22.dp)) {
            Surface(
                modifier = Modifier.graphicsLayer { rotationZ = -3f },
                color = CoresUft.Amarelo,
                shape = RoundedCornerShape(percent = 50),
            ) {
                Text(
                    text = "🎉 $selo",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Black,
                    color = CoresUft.Grafite,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                )
            }
            Spacer(Modifier.height(18.dp))
            Text(
                text = titulo,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = chamada,
                style = MaterialTheme.typography.titleSmall,
                color = Color.White.copy(alpha = 0.85f),
            )
        }
    }
}

@Composable
private fun LinhaInfo(
    emoji: String,
    rotulo: String,
    valor: String,
    detalhe: String? = null,
    cor: Color,
    corClara: Color,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(corClara),
            contentAlignment = Alignment.Center,
        ) {
            Text(emoji, style = MaterialTheme.typography.titleMedium)
        }
        Spacer(Modifier.width(14.dp))
        Column {
            Text(
                text = rotulo.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = cor,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = valor,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
            if (detalhe != null) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = detalhe,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

/**
 * O único botão amarelo cheio do app. Ele carrega o efeito de convite, então
 * ganha o peso visual máximo que a paleta permite.
 */
@Composable
private fun BotaoRealidadeAumentada(
    textoConvite: String,
    onClick: () -> Unit,
) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
                .clip(RoundedCornerShape(percent = 50))
                .clickable(onClick = onClick),
            color = CoresUft.Amarelo,
            shape = RoundedCornerShape(percent = 50),
            shadowElevation = 6.dp,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("📦", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.width(12.dp))
                Text(
                    text = "Ver o convite em RA",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    color = CoresUft.Grafite,
                )
            }
        }
        Text(
            text = textoConvite,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun EventoTelaPreview() {
    TemaEsterSabino {
        Surface {
            EventoTela(aoAbrirRa = {})
        }
    }
}
