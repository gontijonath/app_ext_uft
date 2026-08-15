package br.edu.uft.estersabino.ui.ar

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
// A partir do Compose 1.7 o LocalLifecycleOwner vive em androidx.lifecycle.compose;
// o antigo, em ui.platform, foi removido.
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import io.github.sceneview.SceneView
import io.github.sceneview.SurfaceType
import io.github.sceneview.rememberModelInstance

private const val TAG = "CameraComAvatar"

/**
 * Modo simplificado do convite, para aparelhos sem ARCore.
 *
 * Duas camadas: o preview da CameraX ao fundo e uma cena 3D transparente por
 * cima. O avatar não fica ancorado no ambiente — acompanha a tela —, mas a
 * animação e o texto são exatamente os mesmos do modo com RA. Do ponto de
 * vista do visitante, o convite funciona.
 *
 * A cena usa TextureView em vez de SurfaceView justamente para poder ser
 * transparente e deixar a câmera aparecer atrás.
 */
@Composable
fun CameraComAvatar(
    arquivoModelo: String,
    animacao: String,
    modifier: Modifier = Modifier,
) {
    Box(modifier.fillMaxSize()) {
        PreviewCamera(Modifier.fillMaxSize())

        SceneView(
            modifier = Modifier.fillMaxSize(),
            surfaceType = SurfaceType.TextureSurface,
            isOpaque = false,
            autoCenterContent = true,
            autoFitContent = true,
        ) {
            val instancia = rememberModelInstance(
                modelLoader = modelLoader,
                assetFileLocation = arquivoModelo,
            )
            if (instancia != null) {
                ModelNode(
                    modelInstance = instancia,
                    autoAnimate = false,
                    animationName = animacao,
                    animationLoop = true,
                    isEditable = true,
                )
            }
        }
    }
}

@Composable
private fun PreviewCamera(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember {
        PreviewView(context).apply {
            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            scaleType = PreviewView.ScaleType.FILL_CENTER
        }
    }

    DisposableEffect(lifecycleOwner, previewView) {
        val futuro = ProcessCameraProvider.getInstance(context)
        var provedor: ProcessCameraProvider? = null

        futuro.addListener({
            runCatching {
                provedor = futuro.get()
                val preview = Preview.Builder().build().apply {
                    // Método explícito: a propriedade sintética depende de um
                    // getter que nem toda versão da CameraX expõe.
                    setSurfaceProvider(previewView.surfaceProvider)
                }
                provedor?.unbindAll()
                provedor?.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                )
            }.onFailure { erro ->
                Log.e(TAG, "Falha ao iniciar a câmera do modo simplificado", erro)
            }
        }, ContextCompat.getMainExecutor(context))

        onDispose { provedor?.unbindAll() }
    }

    AndroidView(factory = { previewView }, modifier = modifier)
}
