package br.edu.uft.estersabino.ui.telas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.uft.estersabino.R
import br.edu.uft.estersabino.data.Conteudo
import br.edu.uft.estersabino.data.Projeto
import br.edu.uft.estersabino.ui.comum.EspacoSecao
import br.edu.uft.estersabino.ui.comum.ImagemPlaceholder
import br.edu.uft.estersabino.ui.comum.ManchaDecorativa
import br.edu.uft.estersabino.ui.comum.MembroEquipe
import br.edu.uft.estersabino.ui.comum.capaProjeto
import br.edu.uft.estersabino.ui.comum.equipeGrupo
import br.edu.uft.estersabino.ui.comum.sombraSuave
import br.edu.uft.estersabino.ui.theme.CoresUft
import br.edu.uft.estersabino.ui.theme.TemaEsterSabino

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
            .padding(top = 8.dp, bottom = 32.dp)
    ) {
        CardEquipe()

        EspacoSecao()

        Cabecalho()

        Spacer(Modifier.height(32.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(CoresUft.AmareloSuave),
                contentAlignment = Alignment.Center,
            ) {
                Text("🧪", fontSize = 15.sp)
            }
            Spacer(Modifier.width(10.dp))
            Text(
                text = Conteudo.INICIO_CHAMADA_PROJETOS,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
        Spacer(Modifier.height(16.dp))

        Conteudo.projetos.forEachIndexed { indice, projeto ->
            CardProjetoResumo(
                projeto = projeto,
                numero = indice + 1,
                onClick = { aoAbrirProjeto(projeto.id) },
            )
            Spacer(Modifier.height(14.dp))
        }

        EspacoSecao()

        // O evento (com o convite em RA) fica por último — a turma primeiro
        // conhece os três projetos, e só depois vê qual deles vai visitar.
        BannerEvento(onClick = aoAbrirEvento)

        Spacer(Modifier.height(8.dp))
        Text(
            text = Conteudo.RODAPE_VERSAO,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

/**
 * Card de abertura: a ilustração da homenageada em seu laboratório. A
 * moldura em gradiente e o selo torto vieram da segunda rodada do Claude
 * Design; embaixo, o carrossel com o rosto de cada integrante.
 */
@Composable
private fun CardEquipe() {
    Column {
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1367f / 768f)
                    .clip(RoundedCornerShape(26.dp))
                    .background(
                        Brush.linearGradient(listOf(CoresUft.Amarelo, CoresUft.Coral, CoresUft.Roxo))
                    )
                    .padding(5.dp),
            ) {
                Image(
                    painter = painterResource(R.drawable.ester_sabino_retrato),
                    contentDescription = "Ilustração de Ester Sabino em um laboratório, " +
                        "observando um diagrama filogenético, cercada por equipamentos de " +
                        "sequenciamento genético.",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(22.dp)),
                    contentScale = ContentScale.Crop,
                )
            }
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = 14.dp, y = 14.dp)
                    .graphicsLayer { rotationZ = -2f },
                color = Color.White,
                shape = RoundedCornerShape(percent = 50),
                shadowElevation = 6.dp,
            ) {
                Text(
                    text = "👋 Conheça o grupo",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelLarge,
                    color = CoresUft.VerdeEscuro,
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            equipeGrupo.forEach { membro ->
                MembroAvatar(membro, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun MembroAvatar(membro: MembroEquipe, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(membro.foto),
            contentDescription = membro.nome,
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(percent = 50))
                .border(3.dp, Color.White, RoundedCornerShape(percent = 50)),
            contentScale = ContentScale.Crop,
        )
        Spacer(Modifier.height(5.dp))
        Text(
            // Quebra sempre entre nome e sobrenome — em vez de deixar o
            // Compose decidir (o que só quebrava quando o nome não coubesse
            // na coluna, deixando "Vivian Maria" numa linha só e destoando
            // das demais).
            text = membro.nome.replaceFirst(" ", "\n"),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center,
            minLines = 2,
            maxLines = 2,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(4.dp))
        Surface(
            color = membro.corClara,
            shape = RoundedCornerShape(8.dp),
        ) {
            Text(
                text = membro.papel,
                style = MaterialTheme.typography.labelSmall,
                fontSize = 8.5.sp,
                lineHeight = 10.sp,
                color = membro.cor,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 3.dp),
            )
        }
    }
}

@Composable
private fun Cabecalho() {
    Column {
        Surface(
            color = CoresUft.TurquesaSuave,
            shape = RoundedCornerShape(percent = 50),
        ) {
            Text(
                text = "✨ ${Conteudo.INICIO_SAUDACAO}",
                style = MaterialTheme.typography.labelLarge,
                color = CoresUft.Verde,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
            )
        }
        Spacer(Modifier.height(12.dp))
        Text(
            text = Conteudo.NOME_PLATAFORMA,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = Conteudo.SUBTITULO_PLATAFORMA,
            style = MaterialTheme.typography.titleSmall,
            color = CoresUft.Verde,
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
 * Banner do evento na tela inicial. O selo torto e o botão em pílula branca
 * vieram do estilo importado — mesma ideia do banner original, com mais
 * personalidade.
 */
@Composable
private fun BannerEvento(onClick: () -> Unit) {
    val evento = Conteudo.evento
    val projeto = Conteudo.projetoPorId(evento.projetoId)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .sombraSuave(raio = 28.dp)
            .clip(RoundedCornerShape(28.dp))
            .clickable(onClick = onClick),
        color = Color.Transparent,
    ) {
        Box(
            Modifier.background(
                Brush.linearGradient(listOf(CoresUft.Turquesa, CoresUft.VerdeEscuro))
            )
        ) {
            ManchaDecorativa(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 34.dp, y = (-34).dp),
                tamanho = 150.dp,
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 16.dp, y = 16.dp)
                    .size(8.dp)
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
                        text = "🎉 ${evento.selo}",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Black,
                        color = CoresUft.Grafite,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    )
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    text = projeto?.titulo ?: evento.chamada,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "📅 ${evento.data} · 📍 ${evento.local}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.85f),
                )
                Spacer(Modifier.height(18.dp))
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(percent = 50),
                    shadowElevation = 4.dp,
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Ver evento e convite em RA",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Black,
                            color = CoresUft.VerdeEscuro,
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("→", color = CoresUft.VerdeEscuro, fontWeight = FontWeight.Black)
                    }
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
            .sombraSuave(raio = 26.dp, elevacao = 6.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, CoresUft.BordaCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(126.dp)
                .clip(RoundedCornerShape(20.dp)),
        ) {
            val capa = capaProjeto(projeto.id)
            if (capa != null) {
                Image(
                    painter = painterResource(capa),
                    contentDescription = "Capa do projeto ${projeto.titulo}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            } else {
                ImagemPlaceholder(marca = "$numero", altura = 126, legenda = "Imagem do projeto")
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 10.dp, y = 10.dp)
                    .size(32.dp)
                    .clip(RoundedCornerShape(percent = 50))
                    .background(Color.White.copy(alpha = 0.55f)),
                contentAlignment = Alignment.Center,
            ) {
                Text("$numero", style = MaterialTheme.typography.titleSmall, color = Color.White)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = projeto.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = projeto.subtitulo,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Spacer(Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(percent = 50))
                    .background(CoresUft.OffWhite),
                contentAlignment = Alignment.Center,
            ) {
                Text("→", color = CoresUft.Verde, fontWeight = FontWeight.Black)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun InicioTelaPreview() {
    TemaEsterSabino {
        Surface {
            InicioTela(aoAbrirEvento = {}, aoAbrirProjeto = {})
        }
    }
}
