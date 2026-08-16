package br.edu.uft.estersabino.ui.telas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.edu.uft.estersabino.data.Conteudo
import br.edu.uft.estersabino.data.Projeto
import br.edu.uft.estersabino.ui.comum.AvisoSuave
import br.edu.uft.estersabino.ui.comum.EspacoSecao
import br.edu.uft.estersabino.ui.comum.ImagemPlaceholder
import br.edu.uft.estersabino.ui.comum.capaProjeto
import br.edu.uft.estersabino.ui.theme.CoresUft
import br.edu.uft.estersabino.ui.theme.TemaEsterSabino

/**
 * Ficha completa de um projeto de extensão. Usada tanto na aba "Projetos"
 * quanto (em versão resumida, sem a capa) na aba do evento.
 *
 * Um parágrafo corrido em "Sobre o projeto" em vez de várias seções curtas —
 * o texto real de cada projeto já vem pronto e contado, dividir ele em
 * objetivo/público/metodologia só quebraria o fio da história.
 */
@Composable
fun FichaProjeto(
    projeto: Projeto,
    modifier: Modifier = Modifier,
    mostrarCapa: Boolean = true,
) {
    Column(modifier.fillMaxWidth()) {
        if (mostrarCapa) {
            val capa = capaProjeto(projeto.id)
            if (capa != null) {
                Image(
                    painter = painterResource(capa),
                    contentDescription = "Capa do projeto ${projeto.titulo}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(26.dp)),
                    contentScale = ContentScale.Crop,
                )
            } else {
                ImagemPlaceholder(marca = projeto.sigla.take(2), altura = 200, legenda = "Imagem de capa do projeto")
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = projeto.legendaFoto,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.height(14.dp))
        }

        Text(
            text = projeto.titulo,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(Modifier.height(8.dp))
        Surface(
            color = CoresUft.TurquesaSuave,
            shape = RoundedCornerShape(percent = 50),
        ) {
            Text(
                text = projeto.subtitulo,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = CoresUft.Verde,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
            )
        }

        EspacoSecao()
        SecaoComPonto("Sobre o projeto", projeto.sobre, CoresUft.Verde)

        EspacoSecao()
        TituloComPonto("Contatos", CoresUft.Turquesa)
        Spacer(Modifier.height(12.dp))
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(1.dp, CoresUft.BordaCard),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column {
                projeto.contatos.forEachIndexed { indice, (rotulo, valor) ->
                    val (cor, corClara) = corContato(indice)
                    CampoIcone(emojiContato(rotulo), rotulo, valor, cor, corClara)
                    if (indice != projeto.contatos.lastIndex) {
                        HorizontalDivider(color = CoresUft.BordaCard)
                    }
                }
            }
        }

        if (projeto.observacao != null) {
            EspacoSecao()
            AvisoSuave("ℹ️ ${projeto.observacao}")
        }
    }
}

private fun emojiContato(rotulo: String): String = when (rotulo) {
    "Instagram" -> "📷"
    "E-mail" -> "✉️"
    "Telefone" -> "📞"
    "WhatsApp" -> "💬"
    "Local" -> "📍"
    else -> "🔗"
}

private val paletaContato = listOf(
    CoresUft.Turquesa to CoresUft.TurquesaSuave,
    CoresUft.AmareloEscuro to CoresUft.AmareloSuave,
    CoresUft.Roxo to CoresUft.RoxoSuave,
    CoresUft.Coral to CoresUft.CoralSuave,
)

private fun corContato(indice: Int): Pair<Color, Color> = paletaContato[indice % paletaContato.size]

/** Linha de dado com badge de ícone colorido. */
@Composable
private fun CampoIcone(
    emoji: String,
    rotulo: String,
    valor: String,
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
                .size(38.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(corClara),
            contentAlignment = Alignment.Center,
        ) {
            Text(emoji, style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text(
                text = rotulo.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = cor,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.height(3.dp))
            Text(
                text = valor,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

/**
 * Título de seção com um ponto colorido — a variante usada nas telas de
 * Projetos, Evento e Saiba Mais (não `private`: compartilhado no pacote).
 */
@Composable
fun TituloComPonto(texto: String, cor: Color, modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier
                .size(8.dp)
                .clip(RoundedCornerShape(percent = 50))
                .background(cor)
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = texto,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
fun SecaoComPonto(titulo: String, texto: String?, cor: Color) {
    Column(Modifier.fillMaxWidth()) {
        TituloComPonto(titulo, cor)
        if (texto != null) {
            Spacer(Modifier.height(10.dp))
            Text(
                text = texto,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FichaProjetoPreview() {
    TemaEsterSabino {
        Surface {
            FichaProjeto(
                projeto = Conteudo.projetos.first(),
                modifier = Modifier.padding(20.dp),
            )
        }
    }
}
