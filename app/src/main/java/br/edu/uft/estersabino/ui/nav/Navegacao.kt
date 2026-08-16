package br.edu.uft.estersabino.ui.nav

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Biotech
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.edu.uft.estersabino.data.Conteudo
import br.edu.uft.estersabino.ui.ar.ConviteArTela
import br.edu.uft.estersabino.ui.comum.ManchaDecorativa
import br.edu.uft.estersabino.ui.telas.EventoTela
import br.edu.uft.estersabino.ui.telas.InicioTela
import br.edu.uft.estersabino.ui.telas.ProjetosTela
import br.edu.uft.estersabino.ui.telas.SaibaMaisTela
import br.edu.uft.estersabino.ui.theme.CoresUft

/** Destinos da barra inferior. */
enum class Aba(
    val rota: String,
    val rotulo: String,
    val icone: ImageVector,
) {
    INICIO("inicio", "Início", Icons.Filled.Home),
    PROJETOS("projetos", "Projetos", Icons.AutoMirrored.Filled.List),
    EVENTO("evento", "Evento", Icons.Filled.Star),
    SAIBA_MAIS("saiba-mais", "Saiba Mais", Icons.Filled.Info),
}

private const val ROTA_RA = "convite-ra"
private const val ARG_PROJETO = "projetoId"

@Composable
fun AppEsterSabino() {
    val navController = rememberNavController()
    val entradaAtual by navController.currentBackStackEntryAsState()
    val rotaAtual = entradaAtual?.destination

    // A tela de RA é imersiva: sem barra superior nem inferior.
    val emTelaCheia = rotaAtual?.hierarchy?.any { it.route == ROTA_RA } == true

    Scaffold(
        topBar = { if (!emTelaCheia) BarraSuperiorApp() },
        bottomBar = { if (!emTelaCheia) BarraInferior(navController, rotaAtual) },
        containerColor = MaterialTheme.colorScheme.background,
    ) { espacamento ->
        val modificadorConteudo = if (emTelaCheia) {
            Modifier
        } else {
            Modifier.padding(espacamento)
        }

        Box(modificadorConteudo) {
            NavHost(
                navController = navController,
                startDestination = Aba.INICIO.rota,
            ) {
                composable(Aba.INICIO.rota) {
                    InicioTela(
                        aoAbrirEvento = { navController.navegarParaAba(Aba.EVENTO) },
                        aoAbrirProjeto = { id ->
                            navController.navigate("${Aba.PROJETOS.rota}?$ARG_PROJETO=$id") {
                                launchSingleTop = true
                            }
                        },
                    )
                }

                composable(Aba.PROJETOS.rota) {
                    ProjetosTela(projetoSelecionadoId = null)
                }

                composable("${Aba.PROJETOS.rota}?$ARG_PROJETO={$ARG_PROJETO}") { entrada ->
                    ProjetosTela(
                        projetoSelecionadoId = entrada.arguments?.getString(ARG_PROJETO),
                    )
                }

                composable(Aba.EVENTO.rota) {
                    EventoTela(aoAbrirRa = { navController.navigate(ROTA_RA) })
                }

                composable(Aba.SAIBA_MAIS.rota) {
                    SaibaMaisTela()
                }

                composable(ROTA_RA) {
                    ConviteArTela(aoFechar = { navController.popBackStack() })
                }
            }
        }
    }
}

/**
 * Cabeçalho do app: selo em pílula translúcida com o nome do grupo, sobre um
 * gradiente com manchas decorativas — estilo trazido da segunda rodada do
 * Claude Design, nas cores da UFT (turquesa/verde) em vez do azul/âmbar
 * original.
 */
@Composable
private fun BarraSuperiorApp() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .background(Brush.linearGradient(listOf(CoresUft.Turquesa, CoresUft.VerdeEscuro)))
            .statusBarsPadding(),
    ) {
        ManchaDecorativa(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 34.dp, y = (-36).dp),
            tamanho = 150.dp,
        )
        ManchaDecorativa(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = (-18).dp, y = 30.dp),
            tamanho = 90.dp,
            cor = CoresUft.Amarelo.copy(alpha = 0.28f),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(percent = 50))
                    .background(Color.White.copy(alpha = 0.16f))
                    .padding(start = 6.dp, end = 16.dp, top = 6.dp, bottom = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(RoundedCornerShape(percent = 50))
                        .background(CoresUft.Amarelo),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Biotech,
                        contentDescription = null,
                        tint = CoresUft.Grafite,
                        modifier = Modifier.size(15.dp),
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = Conteudo.NOME_GRUPO.uppercase(),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    letterSpacing = 0.4.sp,
                )
            }
        }
    }
}

/**
 * Barra de navegação flutuante: pílula arredondada com folga nas bordas, em
 * vez da NavigationBar padrão do Material colada na tela inteira — outro
 * traço do design importado.
 */
@Composable
private fun BarraInferior(
    navController: NavHostController,
    rotaAtual: androidx.navigation.NavDestination?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 18.dp)
            .shadow(
                elevation = 14.dp,
                shape = RoundedCornerShape(percent = 50),
                ambientColor = Color.Black.copy(alpha = 0.16f),
                spotColor = Color.Black.copy(alpha = 0.16f),
            )
            .clip(RoundedCornerShape(percent = 50))
            .background(MaterialTheme.colorScheme.surface)
            .padding(6.dp),
    ) {
        Aba.entries.forEach { aba ->
            val selecionada = rotaAtual?.hierarchy?.any { destino ->
                destino.route == aba.rota || destino.route?.startsWith("${aba.rota}?") == true
            } == true

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(percent = 50))
                    .background(if (selecionada) CoresUft.TurquesaSuave else Color.Transparent)
                    .clickable { navController.navegarParaAba(aba) }
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = aba.icone,
                    contentDescription = null,
                    tint = if (selecionada) CoresUft.Verde else CoresUft.CinzaClaro,
                    modifier = Modifier.size(20.dp),
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = aba.rotulo,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Black,
                    color = if (selecionada) CoresUft.Verde else CoresUft.CinzaClaro,
                )
            }
        }
    }
}

/**
 * Troca de aba preservando o estado de cada uma e sem empilhar destinos
 * repetidos — o botão "voltar" do Android continua previsível.
 */
private fun NavHostController.navegarParaAba(aba: Aba) {
    navigate(aba.rota) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
