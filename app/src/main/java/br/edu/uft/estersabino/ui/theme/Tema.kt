package br.edu.uft.estersabino.ui.theme

import android.app.Activity
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
import androidx.compose.ui.text.style.TextAlign
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

    background = CoresUft.Creme,
    onBackground = CoresUft.Grafite,
    surface = CoresUft.Branco,
    onSurface = CoresUft.Grafite,
    surfaceVariant = CoresUft.OffWhite,
    onSurfaceVariant = CoresUft.Cinza,

    outline = CoresUft.BordaCard,
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
 * Par tipográfico Sora (títulos) + Manrope (corpo) — importado do estilo
 * visual definido no Claude Design. Hierarquia mais marcada que o padrão do
 * Material, para dar peso aos títulos das seções num app majoritariamente de
 * leitura. Ver [FamiliaSora] e [FamiliaManrope] em `Tipografia.kt`.
 */
private val TipografiaUft = Typography().run {
    copy(
        displayLarge = displayLarge.copy(fontFamily = FamiliaSora),
        displayMedium = displayMedium.copy(fontFamily = FamiliaSora),
        displaySmall = displaySmall.copy(fontFamily = FamiliaSora, fontWeight = FontWeight.Bold),
        headlineLarge = headlineLarge.copy(fontFamily = FamiliaSora),
        headlineMedium = headlineMedium.copy(fontFamily = FamiliaSora, fontWeight = FontWeight.Bold),
        headlineSmall = headlineSmall.copy(fontFamily = FamiliaSora, fontWeight = FontWeight.Bold),
        titleLarge = titleLarge.copy(fontFamily = FamiliaSora, fontWeight = FontWeight.Bold),
        titleMedium = titleMedium.copy(fontFamily = FamiliaSora, fontWeight = FontWeight.SemiBold),
        titleSmall = titleSmall.copy(fontFamily = FamiliaSora, fontWeight = FontWeight.SemiBold),
        // Justificado (as duas margens alinhadas, como no Word) — pedido pra
        // todo o texto corrido do app. Só afeta parágrafos com mais de uma
        // linha; texto de uma linha só (rótulos, botões) fica igual.
        bodyLarge = bodyLarge.copy(fontFamily = FamiliaManrope, lineHeight = 26.sp, textAlign = TextAlign.Justify),
        bodyMedium = bodyMedium.copy(fontFamily = FamiliaManrope, lineHeight = 22.sp, textAlign = TextAlign.Justify),
        bodySmall = bodySmall.copy(fontFamily = FamiliaManrope, textAlign = TextAlign.Justify),
        labelLarge = labelLarge.copy(fontFamily = FamiliaManrope, fontWeight = FontWeight.SemiBold),
        labelMedium = labelMedium.copy(fontFamily = FamiliaManrope),
        labelSmall = labelSmall.copy(fontFamily = FamiliaManrope),
    )
}

/** Estilo do texto animado da RA — precisa ser legível sobre qualquer fundo. */
val EstiloConviteRa = TextStyle(
    fontFamily = FamiliaSora,
    fontSize = 34.sp,
    lineHeight = 40.sp,
    fontWeight = FontWeight.Black,
    color = Color.White,
)

@Composable
fun TemaEsterSabino(
    // Sempre claro, mesmo se o celular estiver no tema escuro do sistema:
    // todo o visual desta versão (ilustrações, cores, chips) foi desenhado
    // só para o fundo creme. O modo escuro (EsquemaEscuro) fica pronto no
    // código pra quando alguém quiser adaptar o resto das telas pra ele.
    escuro: Boolean = false,
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
