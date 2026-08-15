package br.edu.uft.estersabino.ui.ar

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.ar.core.ArCoreApk
import kotlinx.coroutines.delay

/**
 * Como o convite será exibido neste aparelho.
 */
enum class ModoConvite {
    /** Ainda consultando o ARCore. */
    VERIFICANDO,

    /** ARCore disponível: o avatar fica ancorado no espaço e dá para andar em volta. */
    REALIDADE_AUMENTADA,

    /** Sem ARCore: câmera + avatar renderizado por cima, preso à tela. */
    SIMPLIFICADO,
}

/**
 * Decide o modo do convite consultando o ARCore.
 *
 * Escolha deliberada: só usamos RA quando o ARCore já está **instalado**. Se ele
 * for suportado mas ausente, caímos no modo simplificado em vez de pedir que o
 * visitante baixe um app de ~100 MB pelo Wi-Fi do campus no meio do evento.
 * Trocar [oferecerInstalacao] para true inverte essa política.
 */
@Composable
fun lembrarModoConvite(oferecerInstalacao: Boolean = false): ModoConvite {
    val context = LocalContext.current
    var modo by remember { mutableStateOf(ModoConvite.VERIFICANDO) }

    LaunchedEffect(context, oferecerInstalacao) {
        modo = consultarArCore(context, oferecerInstalacao)
    }
    return modo
}

private suspend fun consultarArCore(
    context: Context,
    oferecerInstalacao: Boolean,
): ModoConvite {
    // checkAvailability pode responder UNKNOWN_CHECKING nas primeiras chamadas,
    // enquanto consulta os serviços do Google. Tentamos por no máximo ~2s.
    repeat(10) {
        val disponibilidade = runCatching {
            ArCoreApk.getInstance().checkAvailability(context)
        }.getOrNull() ?: return ModoConvite.SIMPLIFICADO

        when {
            disponibilidade.isTransient -> delay(200)

            disponibilidade == ArCoreApk.Availability.SUPPORTED_INSTALLED ->
                return ModoConvite.REALIDADE_AUMENTADA

            oferecerInstalacao && disponibilidade.isSupported ->
                return ModoConvite.REALIDADE_AUMENTADA

            else -> return ModoConvite.SIMPLIFICADO
        }
    }
    return ModoConvite.SIMPLIFICADO
}
