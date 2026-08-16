package br.edu.uft.estersabino.ui.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import br.edu.uft.estersabino.R

/**
 * Sora (títulos) e Manrope (corpo) — o par tipográfico do design importado do
 * Claude Design (projeto "Aplicativo de extensão universitária"), buscado em
 * runtime via Google Play Services (Downloadable Fonts). Sem arquivo de fonte
 * embutido no app: se o aparelho não tiver Play Services, o Compose cai de
 * volta na fonte padrão do sistema sozinho — degradação segura.
 */
private val provedorGoogleFonts = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs,
)

private val nomeSora = GoogleFont("Sora")
private val nomeManrope = GoogleFont("Manrope")

val FamiliaSora = FontFamily(
    Font(googleFont = nomeSora, fontProvider = provedorGoogleFonts, weight = FontWeight.SemiBold),
    Font(googleFont = nomeSora, fontProvider = provedorGoogleFonts, weight = FontWeight.Bold),
    Font(googleFont = nomeSora, fontProvider = provedorGoogleFonts, weight = FontWeight.ExtraBold),
)

val FamiliaManrope = FontFamily(
    Font(googleFont = nomeManrope, fontProvider = provedorGoogleFonts, weight = FontWeight.Normal),
    Font(googleFont = nomeManrope, fontProvider = provedorGoogleFonts, weight = FontWeight.Medium),
    Font(googleFont = nomeManrope, fontProvider = provedorGoogleFonts, weight = FontWeight.SemiBold),
    Font(googleFont = nomeManrope, fontProvider = provedorGoogleFonts, weight = FontWeight.Bold),
    Font(googleFont = nomeManrope, fontProvider = provedorGoogleFonts, weight = FontWeight.ExtraBold),
)
