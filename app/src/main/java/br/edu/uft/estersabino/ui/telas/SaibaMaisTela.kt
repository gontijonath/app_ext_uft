package br.edu.uft.estersabino.ui.telas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.edu.uft.estersabino.R
import br.edu.uft.estersabino.data.Conteudo
import br.edu.uft.estersabino.ui.comum.AvisoSuave
import br.edu.uft.estersabino.ui.comum.EspacoSecao
import br.edu.uft.estersabino.ui.theme.CoresUft
import br.edu.uft.estersabino.ui.theme.TemaEsterSabino

@Composable
fun SaibaMaisTela(modifier: Modifier = Modifier) {
    // `remember` puro: ao sair da aba e voltar, a tela sempre reabre do topo.
    val rolagem = remember { ScrollState(0) }
    Column(
        modifier
            .fillMaxWidth()
            .verticalScroll(rolagem)
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp, bottom = 32.dp)
    ) {
        RetratoHomenageada()

        EspacoSecao()
        SecaoComPonto(Conteudo.SAIBA_TITULO_NOME, Conteudo.SAIBA_TEXTO_NOME, CoresUft.Roxo)

        EspacoSecao()
        SecaoComPonto(Conteudo.SAIBA_TITULO_QUEM, Conteudo.SAIBA_TEXTO_QUEM, CoresUft.Turquesa)

        Spacer(Modifier.height(14.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Conteudo.saibaFeitos.forEach { feito ->
                Surface(
                    color = CoresUft.CremeCard,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(Modifier.padding(12.dp)) {
                        Text("🏆", modifier = Modifier.padding(end = 8.dp))
                        Text(
                            text = feito,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }

        EspacoSecao()
        SecaoComPonto(Conteudo.SAIBA_TITULO_LIGACAO, Conteudo.SAIBA_TEXTO_LIGACAO, CoresUft.AmareloEscuro)

        EspacoSecao()
        AvisoSuave("💡 ${Conteudo.SAIBA_AVISO_DADOS}")

        EspacoSecao()
        TituloComPonto(Conteudo.SAIBA_TITULO_CREDITOS, CoresUft.Coral)
        Spacer(Modifier.height(14.dp))
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(22.dp),
            border = BorderStroke(1.dp, CoresUft.BordaCard),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(Modifier.padding(6.dp)) {
                Conteudo.saibaCreditos.forEachIndexed { indice, (rotulo, valor) ->
                    if (indice > 0) {
                        HorizontalDivider(color = CoresUft.BordaCard)
                    }
                    Row(Modifier.fillMaxWidth().padding(12.dp)) {
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
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }

        EspacoSecao()
        FontesConsultadas()

        EspacoSecao()
        Text(
            text = Conteudo.SAIBA_RODAPE_FONTES,
            style = MaterialTheme.typography.labelSmall,
            fontStyle = FontStyle.Italic,
            color = CoresUft.CinzaClaro,
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = Conteudo.RODAPE_VERSAO,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

/**
 * Lista das fontes usadas pra escrever sobre cada projeto — fica fechada por
 * padrão (é material de referência, não leitura principal) e os links abrem
 * no navegador ao toque.
 */
@Composable
private fun FontesConsultadas() {
    var expandido by remember { mutableStateOf(false) }
    val uriHandler = LocalUriHandler.current

    Surface(
        color = CoresUft.CremeCard,
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expandido = !expandido },
    ) {
        Column(Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = Conteudo.SAIBA_TITULO_FONTES,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = if (expandido) "▲" else "▼",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            if (expandido) {
                Spacer(Modifier.height(12.dp))
                Conteudo.fontesConsultadas.forEach { (projeto, fontes) ->
                    Text(
                        text = projeto,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = CoresUft.Verde,
                    )
                    Spacer(Modifier.height(4.dp))
                    fontes.forEach { (rotulo, url) ->
                        Text(
                            text = "•  $rotulo",
                            style = MaterialTheme.typography.bodySmall,
                            color = CoresUft.AzulInstitucional,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .clickable { uriHandler.openUri(url) },
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                }
            }
        }
    }
}

/**
 * Retrato ilustrado da homenageada, encomendado pela equipe pra essa versão
 * do app — não é uma foto real, é uma ilustração de estilo autoral, o que
 * evita a questão de licença de uso de imagem de pessoa real.
 */
@Composable
private fun RetratoHomenageada() {
    Image(
        painter = painterResource(R.drawable.ester_sabino_retrato),
        contentDescription = "Ilustração de Ester Sabino em um laboratório, observando " +
            "um diagrama filogenético, cercada por equipamentos de sequenciamento genético.",
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .clip(RoundedCornerShape(26.dp)),
        contentScale = ContentScale.Crop,
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SaibaMaisTelaPreview() {
    TemaEsterSabino {
        Surface {
            SaibaMaisTela()
        }
    }
}
