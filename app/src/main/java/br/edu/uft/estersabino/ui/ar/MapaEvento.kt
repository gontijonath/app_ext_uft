package br.edu.uft.estersabino.ui.ar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.edu.uft.estersabino.R

/**
 * Miniatura do mapa do evento, sempre visível no canto da tela de RA. Toque
 * para abrir em tela cheia; toque de novo (ou no X) para fechar.
 *
 * Fica escondida durante a captura de foto, junto com o resto dos controles
 * (ver [ConviteArTela]) — a foto compartilhada mostra só o convite.
 */
@Composable
fun MiniMapaEvento(modifier: Modifier = Modifier) {
    var expandido by remember { mutableStateOf(false) }

    Box(modifier.fillMaxSize()) {
        Box(
            Modifier
                .align(Alignment.BottomStart)
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(start = 16.dp, bottom = 32.dp)
                .width(96.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(18.dp))
                .background(Color.Black.copy(alpha = 0.25f))
                .clickable { expandido = true },
        ) {
            Image(
                painter = painterResource(R.drawable.mapa_evento),
                contentDescription = "Ver mapa de como chegar ao evento",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
            Box(
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.55f))
                    .padding(vertical = 5.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Icon(
                        Icons.Filled.Place,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(12.dp),
                    )
                    Text(
                        text = "Mapa",
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 3.dp),
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = expandido,
            enter = fadeIn() + scaleIn(initialScale = 0.92f),
            exit = fadeOut() + scaleOut(targetScale = 0.92f),
        ) {
            MapaExpandido(aoFechar = { expandido = false })
        }
    }
}

private const val ZOOM_MINIMO = 1f
private const val ZOOM_MAXIMO = 5f

/**
 * Fica no centro da tela, sobre um fundo escurecido. Não tem botão de fechar
 * próprio — os cantos já são ocupados pelos botões de fechar/capturar da
 * [ConviteArTela], então tocar em qualquer lugar fora do mapa fecha (padrão
 * comum de "toque fora para fechar").
 *
 * A imagem aceita pinça para dar zoom (até 5x) e arrastar com o dedo depois
 * de ampliada, pra dar pra ler os nomes dos blocos menores. Como o estado de
 * zoom é `remember` simples, ele reseta sozinho toda vez que o mapa é
 * reaberto (o `AnimatedVisibility` descarta o conteúdo ao fechar).
 */
@Composable
private fun MapaExpandido(aoFechar: () -> Unit) {
    var escala by remember { mutableFloatStateOf(1f) }
    var deslocamento by remember { mutableStateOf(Offset.Zero) }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.82f))
            .clickable(onClick = aoFechar),
    ) {
        Column(
            Modifier
                .align(Alignment.Center)
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .clickable(enabled = false) {},
                color = Color.Transparent,
            ) {
                Image(
                    painter = painterResource(R.drawable.mapa_evento),
                    contentDescription = "Mapa de como chegar ao evento",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            scaleX = escala
                            scaleY = escala
                            translationX = deslocamento.x
                            translationY = deslocamento.y
                        }
                        .pointerInput(Unit) {
                            detectTransformGestures { _, pan, zoom, _ ->
                                val novaEscala = (escala * zoom).coerceIn(ZOOM_MINIMO, ZOOM_MAXIMO)
                                escala = novaEscala
                                deslocamento = if (novaEscala <= ZOOM_MINIMO) {
                                    Offset.Zero
                                } else {
                                    // Limita o arrasto para a imagem não sumir de vez da tela.
                                    val limiteX = size.width * (novaEscala - 1) / 2
                                    val limiteY = size.height * (novaEscala - 1) / 2
                                    Offset(
                                        x = (deslocamento.x + pan.x * novaEscala).coerceIn(-limiteX, limiteX),
                                        y = (deslocamento.y + pan.y * novaEscala).coerceIn(-limiteY, limiteY),
                                    )
                                }
                            }
                        },
                )
            }
            Text(
                text = if (escala > ZOOM_MINIMO) {
                    "Belisque para ajustar o zoom"
                } else {
                    "Belisque a imagem para ampliar · toque fora para fechar"
                },
                color = Color.White.copy(alpha = 0.75f),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(top = 14.dp),
            )
        }
    }
}
