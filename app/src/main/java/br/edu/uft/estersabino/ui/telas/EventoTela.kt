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
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.edu.uft.estersabino.data.Conteudo
import br.edu.uft.estersabino.ui.comum.EspacoSecao
import br.edu.uft.estersabino.ui.comum.SecaoParagrafo
import br.edu.uft.estersabino.ui.comum.Selo
import br.edu.uft.estersabino.ui.theme.CoresUft

@Composable
fun EventoTela(
    aoAbrirRa: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val evento = Conteudo.evento
    val projeto = Conteudo.projetoPorId(evento.projetoId)
    val numeroProjeto = Conteudo.projetos.indexOfFirst { it.id == evento.projetoId } + 1

    Column(
        modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        CabecalhoEvento(
            selo = evento.selo,
            titulo = projeto?.titulo ?: evento.chamada,
            chamada = evento.chamada,
        )

        Column(Modifier.padding(horizontal = 20.dp, vertical = 24.dp)) {

            LinhaInfo(Icons.Filled.CalendarMonth, "Data", evento.data)
            Spacer(Modifier.height(12.dp))
            LinhaInfo(Icons.Filled.Schedule, "Horário", evento.horario)
            Spacer(Modifier.height(12.dp))
            LinhaInfo(Icons.Filled.LocationOn, "Local", evento.local, evento.localDetalhe)

            EspacoSecao()

            BotaoRealidadeAumentada(
                textoConvite = evento.convite,
                onClick = aoAbrirRa,
            )

            EspacoSecao()
            SecaoParagrafo("Programação", evento.programacao)

            EspacoSecao()
            SecaoParagrafo("Sobre o projeto apresentado", evento.descricao)

            EspacoSecao()

            if (projeto != null) {
                FichaProjeto(
                    projeto = projeto,
                    numero = if (numeroProjeto > 0) numeroProjeto else 1,
                    mostrarCapa = false,
                )
            }
        }
    }
}

@Composable
private fun CabecalhoEvento(selo: String, titulo: String, chamada: String) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(Brush.verticalGradient(listOf(CoresUft.Verde, CoresUft.VerdeEscuro)))
    ) {
        Column(Modifier.padding(20.dp)) {
            Selo(selo)
            Spacer(Modifier.height(16.dp))
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
    icone: ImageVector,
    rotulo: String,
    valor: String,
    detalhe: String? = null,
) {
    Row(verticalAlignment = Alignment.Top) {
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = RoundedCornerShape(10.dp),
        ) {
            Icon(
                imageVector = icone,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(8.dp)
                    .size(20.dp),
            )
        }
        Spacer(Modifier.width(14.dp))
        Column {
            Text(
                text = rotulo.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
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
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CoresUft.Amarelo,
                contentColor = CoresUft.Grafite,
            ),
        ) {
            Icon(
                imageVector = Icons.Filled.ViewInAr,
                contentDescription = null,
                modifier = Modifier.size(26.dp),
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = "Ver o convite em RA",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
        }
        Text(
            text = textoConvite,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
