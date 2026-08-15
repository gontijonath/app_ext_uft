package br.edu.uft.estersabino.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

private val EsquemaClaro = lightColorScheme(
    primary = CoresUft.Verde,
    onPrimary = CoresUft.Branco,
    primaryContainer = CoresUft.VerdeSuave,
    onPrimaryContainer = CoresUft.VerdeEscuro,

    secondary = CoresUft.AzulInstitucional,
    onSecondary = CoresUft.Branco,

    tertiary = CoresUft.Amarelo,
    onTertiary = CoresUft.Grafite,
    tertiaryContainer = CoresUft.AmareloSuave,
    onTertiaryContainer = CoresUft.AmareloEscuro,

    background = CoresUft.Branco,
    onBackground = CoresUft.Grafite,
    surface = CoresUft.Branco,
    onSurface = CoresUft.Grafite,
    surfaceVariant = CoresUft.OffWhite,
    onSurfaceVariant = CoresUft.Cinza,

    outline = Color(0xFFD3D6D6),
    outlineVariant = Color(0xFFE7EAEA),
)

private val EsquemaEscuro = darkColorScheme(
    primary = Color(0xFF63C7BB),
    onPrimary = Color(0xFF00332E),
    primaryContainer = CoresUft.VerdeEscuro,
    onPrimaryContainer = CoresUft.VerdeClaro,

    secondary = Color(0xFF87BEE0),
    onSecondary = Color(0xFF00293F),

    tertiary = CoresUft.Amarelo,
    onTertiary = Color(0xFF3B2A00),
    tertiaryContainer = Color(0xFF5B4300),
    onTertiaryContainer = CoresUft.AmareloSuave,

    background = CoresUft.FundoEscuro,
    onBackground = CoresUft.TextoEscuroClaro,
    surface = CoresUft.SuperficieEscura,
    onSurface = CoresUft.TextoEscuroClaro,
    surfaceVariant = Color(0xFF263133),
    onSurfaceVariant = Color(0xFFAFBAB9),

    outline = Color(0xFF3D4A4C),
    outlineVariant = Color(0xFF2A3436),
)

/**
 * Tipografia com hierarquia um pouco mais marcada que o padrão do Material, para
 * dar peso aos títulos das seções num app majoritariamente de leitura.
 */
private val TipografiaUft = Typography().run {
    copy(
        displaySmall = displaySmall.copy(fontWeight = FontWeight.Bold),
        headlineMedium = headlineMedium.copy(fontWeight = FontWeight.Bold),
        headlineSmall = headlineSmall.copy(fontWeight = FontWeight.Bold),
        titleLarge = titleLarge.copy(fontWeight = FontWeight.Bold),
        titleMedium = titleMedium.copy(fontWeight = FontWeight.SemiBold),
        bodyLarge = bodyLarge.copy(lineHeight = 26.sp),
        bodyMedium = bodyMedium.copy(lineHeight = 22.sp),
        labelLarge = labelLarge.copy(fontWeight = FontWeight.SemiBold),
    )
}

/** Estilo do texto animado da RA — precisa ser legível sobre qualquer fundo. */
val EstiloConviteRa = TextStyle(
    fontSize = 34.sp,
    lineHeight = 40.sp,
    fontWeight = FontWeight.Black,
    color = Color.White,
)

@Composable
fun TemaEsterSabino(
    escuro: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val esquema = if (escuro) EsquemaEscuro else EsquemaClaro
    val view = LocalView.current

    if (!view.isInEditMode) {
        val janelaEscura = escuro
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !janelaEscura
        }
    }

    MaterialTheme(
        colorScheme = esquema,
        typography = TipografiaUft,
        content = content,
    )
}
