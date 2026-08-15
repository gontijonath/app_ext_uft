package br.edu.uft.estersabino.ui.ar

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.PixelCopy
import android.view.Window
import androidx.core.content.FileProvider
import androidx.core.graphics.createBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import kotlin.coroutines.resume

/**
 * Captura da tela do convite.
 *
 * Usamos [PixelCopy] sobre a janela, e não o desenho da View: o conteúdo 3D é
 * renderizado numa Surface pelo Filament, que não aparece em `View.draw()`.
 * PixelCopy lê o buffer real que foi para a tela, então a foto sai com o avatar
 * e a câmera juntos — que é o ponto.
 */
suspend fun capturarJanela(activity: Activity): Bitmap? {
    // A sobrecarga de PixelCopy.request que recebe uma Window só existe a
    // partir da API 26 — a que aceita SurfaceView é que veio na 24. Sem esta
    // guarda, o app quebra ao tirar a foto num Android 7.
    if (!capturaSuportada) return null

    val janela: Window = activity.window
    val view = janela.decorView
    if (view.width <= 0 || view.height <= 0) return null

    val bitmap = createBitmap(view.width, view.height)

    return suspendCancellableCoroutine { continuacao ->
        try {
            PixelCopy.request(
                janela,
                bitmap,
                { resultado ->
                    if (resultado == PixelCopy.SUCCESS) {
                        continuacao.resume(bitmap)
                    } else {
                        bitmap.recycle()
                        continuacao.resume(null)
                    }
                },
                Handler(Looper.getMainLooper()),
            )
        } catch (e: IllegalArgumentException) {
            // Janela ainda sem superfície válida (transição de tela).
            bitmap.recycle()
            continuacao.resume(null)
        }
    }
}

/**
 * Salva no cache do app e devolve um Intent de compartilhamento.
 *
 * Vai para o cache, não para a galeria, de propósito: assim o app não precisa
 * de permissão de armazenamento em nenhuma versão do Android. O visitante
 * escolhe no menu se quer salvar, mandar no WhatsApp ou postar.
 */
suspend fun prepararCompartilhamento(
    context: Context,
    bitmap: Bitmap,
): Intent? = withContext(Dispatchers.IO) {
    runCatching {
        val pasta = File(context.cacheDir, "convites").apply { mkdirs() }
        // Nome fixo: cada nova captura substitui a anterior, o cache não cresce.
        val arquivo = File(pasta, "convite-ester-sabino.png")

        FileOutputStream(arquivo).use { saida ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, saida)
        }

        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            arquivo,
        )

        Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }.getOrNull()
}

/**
 * PixelCopy sobre uma Window existe a partir da API 26 (Android 8).
 *
 * Nos aparelhos com Android 7 o botão da foto simplesmente não aparece — o
 * convite continua funcionando normalmente, só sem a captura.
 */
val capturaSuportada: Boolean
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
