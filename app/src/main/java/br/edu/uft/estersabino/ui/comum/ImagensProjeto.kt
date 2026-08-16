package br.edu.uft.estersabino.ui.comum

import androidx.compose.ui.graphics.Color
import br.edu.uft.estersabino.R
import br.edu.uft.estersabino.ui.theme.CoresUft

/**
 * Capa real de cada projeto (fotos/logos enviados pela equipe). Mantido fora
 * de `Conteudo.kt` de propósito: aquele arquivo é só texto, sem acoplamento a
 * recursos do Android — imagem é responsabilidade da camada de UI.
 *
 * Quando um projeto ainda não tem capa própria, [capaProjeto] retorna `null`
 * e quem chama cai de volta no placeholder numerado ([ImagemPlaceholder]).
 */
private val capasProjeto = mapOf(
    "projeto-1" to R.drawable.capa_projeto1,
    "projeto-2" to R.drawable.capa_projeto2,
    "projeto-3" to R.drawable.capa_projeto3,
)

fun capaProjeto(projetoId: String): Int? = capasProjeto[projetoId]

/** Uma integrante do grupo, para o carrossel de rosto+papel na Início. */
data class MembroEquipe(
    val nome: String,
    val papel: String,
    val foto: Int,
    val cor: Color,
    val corClara: Color,
)

val equipeGrupo: List<MembroEquipe> = listOf(
    MembroEquipe("Patricia Gontijo", "Facilitadora Digital", R.drawable.avatar_patricia, CoresUft.Turquesa, CoresUft.TurquesaSuave),
    MembroEquipe("Luisa Gabrielly", "Mediadora Virtual", R.drawable.avatar_luisa, CoresUft.Verde, CoresUft.TurquesaSuave),
    MembroEquipe("Ruth Carvalho", "Moderadora", R.drawable.avatar_ruth, CoresUft.Roxo, CoresUft.RoxoSuave),
    MembroEquipe("Vivian Maria", "Pacificadora", R.drawable.avatar_vivian, CoresUft.Coral, CoresUft.CoralSuave),
    MembroEquipe("Isadora Ribeiro", "Secretária", R.drawable.avatar_isadora, CoresUft.AmareloEscuro, CoresUft.AmareloSuave),
)
