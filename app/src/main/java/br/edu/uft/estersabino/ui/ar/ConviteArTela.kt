package br.edu.uft.estersabino.ui.ar

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import br.edu.uft.estersabino.ui.theme.CoresUft
import com.google.ar.core.Config
import com.google.ar.core.TrackingState
import io.github.sceneview.ar.ARSceneView
import io.github.sceneview.math.Position
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/** Texto que sobe em loop sobre a câmera. */
private const val TEXTO_CONVITE = "Venha nos conhecer!!"

/**
 * Tela cheia do convite em realidade aumentada.
 *
 * Fluxo: pede a permissão de câmera → descobre se o aparelho tem ARCore →
 * mostra o avatar acenando com o texto subindo em loop. Sem QR Code, sem alvo,
 * sem detecção de superfície: um toque e o convite aparece.
 */
@Composable
fun ConviteArTela(
    aoFechar: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val view = LocalView.current
    val escopo = rememberCoroutineScope()

    var temPermissao by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED
        )
    }
    var permissaoNegada by remember { mutableStateOf(false) }
    var capturando by remember { mutableStateOf(false) }

    val pedirPermissao = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedida ->
        temPermissao = concedida
        permissaoNegada = !concedida
    }

    LaunchedEffect(Unit) {
        if (!temPermissao) pedirPermissao.launch(Manifest.permission.CAMERA)
    }

    // A cena ocupa a tela inteira; devolvemos as barras do sistema ao sair.
    DisposableEffect(view) {
        val janela = (view.context as? Activity)?.window
        val controlador = janela?.let { WindowCompat.getInsetsController(it, view) }
        controlador?.isAppearanceLightStatusBars = false
        onDispose {
            controlador?.isAppearanceLightStatusBars = true
        }
    }

    Box(modifier.fillMaxSize().background(Color.Black)) {
        when {
            !temPermissao -> AvisoPermissao(
                negadaDefinitivamente = permissaoNegada,
                aoPedirNovamente = { pedirPermissao.launch(Manifest.permission.CAMERA) },
            )

            else -> ConteudoConvite()
        }

        // Some com os controles no instante da captura: a foto que o visitante
        // compartilha sai só com o avatar e o convite, sem os botões do app.
        if (!capturando) {
            BarraSuperior(
                aoFechar = aoFechar,
                aoCapturar = if (temPermissao) {
                    {
                        escopo.launch {
                            val activity = view.context as? Activity
                            if (activity != null) {
                                capturando = true
                                // Um respiro para o frame sem os botões chegar à tela.
                                delay(150)
                                val bitmap = capturarJanela(activity)
                                capturando = false

                                if (bitmap != null) {
                                    prepararCompartilhamento(context, bitmap)?.let { intent ->
                                        context.startActivity(
                                            Intent.createChooser(intent, "Compartilhar convite")
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    null
                },
            )
        }
    }
}

/** Escolhe entre RA de verdade e modo simplificado, sem o visitante perceber. */
@Composable
private fun ConteudoConvite() {
    when (lembrarModoConvite()) {
        ModoConvite.VERIFICANDO -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(color = CoresUft.Amarelo)
        }

        ModoConvite.REALIDADE_AUMENTADA -> CenaRealidadeAumentada()

        ModoConvite.SIMPLIFICADO -> CenaSimplificada()
    }
}

/**
 * Modo principal: ARCore ativo, avatar ancorado no espaço.
 *
 * Toda a detecção de plano está desligada. A âncora nasce no ar, calculada a
 * partir da pose da câmera no primeiro quadro rastreado — por isso o avatar
 * aparece imediatamente, e permanece onde foi plantado quando a pessoa anda.
 */
@Composable
private fun CenaRealidadeAumentada() {
    var ancora by remember { mutableStateOf<com.google.ar.core.Anchor?>(null) }
    var rastreando by remember { mutableStateOf(false) }
    var reposicionar by remember { mutableStateOf(false) }

    // Entrada suave: o avatar cresce de 80% ao tamanho final e o texto surge
    // junto, em vez de os dois aparecerem de estalo no primeiro quadro.
    val entrada by animateFloatAsState(
        targetValue = if (ancora != null) 1f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "entradaAvatar",
    )

    Box(Modifier.fillMaxSize()) {
        ARSceneView(
            modifier = Modifier.fillMaxSize(),
            planeRenderer = false,
            planeFindingMode = Config.PlaneFindingMode.DISABLED,
            depthMode = Config.DepthMode.DISABLED,
            instantPlacementMode = Config.InstantPlacementMode.DISABLED,
            onSessionUpdated = { session, frame ->
                val camera = frame.camera
                rastreando = camera.trackingState == TrackingState.TRACKING

                if (rastreando && (ancora == null || reposicionar)) {
                    runCatching { session.createAnchor(poseNaFrenteDaCamera(camera)) }
                        .onSuccess { nova ->
                            ancora?.detach()
                            ancora = nova
                            reposicionar = false
                        }
                }
            },
        ) {
            val instancia = io.github.sceneview.rememberModelInstance(
                modelLoader = modelLoader,
                assetFileLocation = CenaAvatar.ARQUIVO_MODELO,
            )

            val ancoraAtual = ancora
            if (instancia != null && ancoraAtual != null) {
                AnchorNode(anchor = ancoraAtual) {
                    ModelNode(
                        modelInstance = instancia,
                        autoAnimate = false,
                        animationName = CenaAvatar.ANIMACAO,
                        animationLoop = true,
                        scaleToUnits = CenaAvatar.ALTURA_METROS * (0.8f + 0.2f * entrada),
                        // Alinha os pés à âncora em vez de centrar o corpo nela.
                        centerOrigin = Position(y = -1f),
                        isEditable = true,
                    )
                }
            }
        }

        TextoSubindo(
            texto = TEXTO_CONVITE,
            modifier = Modifier
                .fillMaxSize()
                .alpha(entrada),
        )

        if (ancora == null) {
            DicaInferior(
                texto = if (rastreando) {
                    "Preparando o convite…"
                } else {
                    "Mova o celular devagar para o convite aparecer"
                },
            )
        } else {
            BotaoReposicionar(onClick = { reposicionar = true })
        }
    }
}

/**
 * Modo simplificado, para aparelhos sem ARCore.
 *
 * Mantém o convite: mesma animação, mesmo texto. A diferença é que o avatar
 * acompanha a tela em vez de ficar plantado no ambiente. O visitante não vê
 * nenhuma mensagem de erro — só um convite um pouco menos mágico.
 */
@Composable
private fun CenaSimplificada() {
    Box(Modifier.fillMaxSize()) {
        CameraComAvatar(
            arquivoModelo = CenaAvatar.ARQUIVO_MODELO,
            animacao = CenaAvatar.ANIMACAO,
            modifier = Modifier.fillMaxSize(),
        )
        TextoSubindo(texto = TEXTO_CONVITE, modifier = Modifier.fillMaxSize())
        DicaInferior(texto = "Modo simplificado")
    }
}

// ------------------------------------------------------------------ Interface

@Composable
private fun BarraSuperior(
    aoFechar: () -> Unit,
    aoCapturar: (() -> Unit)?,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BotaoCircular(
            icone = { Icon(Icons.Filled.Close, contentDescription = "Fechar") },
            onClick = aoFechar,
        )
        if (aoCapturar != null && capturaSuportada) {
            BotaoCircular(
                icone = { Icon(Icons.Filled.PhotoCamera, contentDescription = "Capturar foto") },
                onClick = aoCapturar,
            )
        }
    }
}

@Composable
private fun BotaoCircular(
    icone: @Composable () -> Unit,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(48.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.Black.copy(alpha = 0.45f),
            contentColor = Color.White,
        ),
    ) {
        icone()
    }
}

@Composable
private fun DicaInferior(texto: String) {
    Box(
        Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(bottom = 32.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Surface(
            color = Color.Black.copy(alpha = 0.5f),
            shape = RoundedCornerShape(50),
        ) {
            Text(
                text = texto,
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
                style = MaterialTheme.typography.labelLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun BotaoReposicionar(onClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(bottom = 32.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black.copy(alpha = 0.55f),
                contentColor = Color.White,
            ),
        ) {
            Icon(Icons.Filled.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text("Trazer para a frente", style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
private fun AvisoPermissao(
    negadaDefinitivamente: Boolean,
    aoPedirNovamente: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "O convite usa a câmera",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = if (negadaDefinitivamente) {
                "Para ver o convite, autorize o acesso à câmera nas configurações do aparelho."
            } else {
                "Autorize o acesso à câmera para ver o convite."
            },
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = aoPedirNovamente,
            colors = ButtonDefaults.buttonColors(
                containerColor = CoresUft.Amarelo,
                contentColor = CoresUft.Grafite,
            ),
        ) {
            Text("Permitir câmera")
        }
    }
}
