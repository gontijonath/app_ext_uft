package br.edu.uft.estersabino.ui.ar

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import br.edu.uft.estersabino.ui.theme.EstiloConviteRa

/**
 * "Venha nos conhecer!!" subindo em loop infinito sobre a imagem da câmera.
 *
 * É um overlay 2D, não um objeto 3D: o texto fica sempre nítido e legível,
 * independente de onde a pessoa aponta o celular, e não custa nada de
 * renderização. Um texto no espaço 3D serrilharia e sumiria ao virar a câmera.
 */
@Composable
fun TextoSubindo(
    texto: String,
    modifier: Modifier = Modifier,
    duracaoMs: Int = 4000,
    estilo: TextStyle = EstiloConviteRa,
) {
    val transicao = rememberInfiniteTransition(label = "convite")
    val progresso by transicao.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duracaoMs, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "subida",
    )

    // Entra nos primeiros 15% do ciclo e sai nos últimos 25%: o texto nunca
    // aparece ou some de forma abrupta na borda da tela.
    val opacidade = when {
        progresso < 0.15f -> progresso / 0.15f
        progresso > 0.75f -> (1f - progresso) / 0.25f
        else -> 1f
    }.coerceIn(0f, 1f)

    BoxWithConstraints(modifier.fillMaxSize()) {
        val alturaPx = constraints.maxHeight.toFloat()
        // Vai de 15% abaixo da base até 15% acima do topo.
        val deslocamentoY = alturaPx * (1.15f - progresso * 1.30f)

        Box(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            contentAlignment = Alignment.TopCenter,
        ) {
            Text(
                text = texto,
                style = estilo.copy(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.65f),
                        offset = Offset(0f, 3f),
                        blurRadius = 14f,
                    ),
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .graphicsLayer { translationY = deslocamentoY }
                    .alpha(opacidade),
            )
        }
    }
}
