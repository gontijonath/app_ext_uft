package br.edu.uft.estersabino.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Paleta extraída diretamente da logo oficial da UFT (`imagens/logo.png`).
 *
 * Diretriz do projeto: predominância do verde, sem exagero. O verde estrutura a
 * navegação, os fundos ficam claros e neutros, e o amarelo aparece apenas em
 * pontos de ação (botão da RA, selo do evento).
 */
object CoresUft {
    val Verde = Color(0xFF00897C)
    val VerdeEscuro = Color(0xFF00695E)
    val VerdeClaro = Color(0xFFB2DFD9)
    val VerdeSuave = Color(0xFFE6F4F2)

    val Amarelo = Color(0xFFFDB92E)
    val AmareloEscuro = Color(0xFFC98D00)
    val AmareloSuave = Color(0xFFFFF3DA)

    val AzulMarinho = Color(0xFF28146E)
    val AzulInstitucional = Color(0xFF005484)

    val Cinza = Color(0xFF848688)
    val Grafite = Color(0xFF373435)

    val Branco = Color(0xFFFFFFFF)
    val OffWhite = Color(0xFFEFEFEF)

    // Tons do modo escuro
    val FundoEscuro = Color(0xFF12191A)
    val SuperficieEscura = Color(0xFF1B2426)
    val TextoEscuroClaro = Color(0xFFE4EAE9)
}
