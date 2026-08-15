package br.edu.uft.estersabino.ui.comum

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.uft.estersabino.ui.theme.CoresUft

/**
 * Substituto das fotos que ainda não chegaram.
 *
 * Um bloco de cor da paleta UFT com a marca do projeto. A escolha é deliberada:
 * um placeholder gráfico coerente parece proposital, enquanto um ícone de
 * "imagem quebrada" parece defeito — e este protótipo será apresentado.
 */
@Composable
fun ImagemPlaceholder(
    marca: String,
    modifier: Modifier = Modifier,
    altura: Int = 180,
    legenda: String? = null,
    larguraCheia: Boolean = true,
) {
    val paleta = listOf(
        listOf(CoresUft.Verde, CoresUft.VerdeEscuro),
        listOf(CoresUft.AzulInstitucional, CoresUft.AzulMarinho),
        listOf(CoresUft.AmareloEscuro, CoresUft.Amarelo),
    )
    val cores = paleta[(marca.hashCode().let { if (it < 0) -it else it }) % paleta.size]

    Box(
        modifier = modifier
            .then(if (larguraCheia) Modifier.fillMaxWidth() else Modifier)
            .height(altura.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Brush.linearGradient(cores)),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = marca,
                color = Color.White.copy(alpha = 0.95f),
                fontSize = 40.sp,
                fontWeight = FontWeight.Black,
            )
            if (legenda != null) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = legenda,
                    color = Color.White.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

/** Título de seção com um filete verde à esquerda. */
@Composable
fun TituloSecao(texto: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier
                .size(width = 4.dp, height = 20.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(MaterialTheme.colorScheme.primary)
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = texto,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

/** Bloco de "rótulo + texto" usado nas fichas dos projetos. */
@Composable
fun CampoTexto(
    rotulo: String,
    valor: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier.fillMaxWidth()) {
        Text(
            text = rotulo.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = valor,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

/** Seção com título e um parágrafo corrido. */
@Composable
fun SecaoParagrafo(
    titulo: String,
    texto: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier.fillMaxWidth()) {
        TituloSecao(titulo)
        Spacer(Modifier.height(8.dp))
        Text(
            text = texto,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

/** Lista com marcadores em verde. */
@Composable
fun ListaComMarcadores(
    itens: List<String>,
    modifier: Modifier = Modifier,
) {
    Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        itens.forEach { item ->
            Row(verticalAlignment = Alignment.Top) {
                Box(
                    Modifier
                        .padding(top = 7.dp)
                        .size(7.dp)
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.primary)
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

/** Aviso discreto — usado para marcar o que ainda precisa de conferência. */
@Composable
fun AvisoSuave(texto: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.tertiaryContainer,
        shape = RoundedCornerShape(12.dp),
    ) {
        Text(
            text = texto,
            modifier = Modifier.padding(14.dp),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
        )
    }
}

/** Selo pequeno de destaque (usado no "EVENTO"). */
@Composable
fun Selo(texto: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = CoresUft.Amarelo,
        shape = RoundedCornerShape(50),
    ) {
        Text(
            text = texto,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Black,
            color = CoresUft.Grafite,
        )
    }
}

/** Espaço vertical padrão entre blocos de conteúdo. */
@Composable
fun EspacoSecao() = Spacer(Modifier.height(24.dp))

/** Caixa centralizada — usada em estados vazios e de carregamento. */
@Composable
fun CaixaCentral(conteudo: @Composable () -> Unit) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { conteudo() }
}
