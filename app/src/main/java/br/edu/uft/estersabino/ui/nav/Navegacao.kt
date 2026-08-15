package br.edu.uft.estersabino.ui.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.edu.uft.estersabino.data.Conteudo
import br.edu.uft.estersabino.ui.ar.ConviteArTela
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
    EVENTO("evento", "EVENTO", Icons.Filled.Star),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BarraSuperiorApp() {
    TopAppBar(
        title = {
            Text(
                text = Conteudo.NOME_PLATAFORMA,
                fontWeight = FontWeight.Bold,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = CoresUft.Verde,
            titleContentColor = Color.White,
        ),
    )
}

@Composable
private fun BarraInferior(
    navController: NavHostController,
    rotaAtual: androidx.navigation.NavDestination?,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Aba.entries.forEach { aba ->
            val selecionada = rotaAtual?.hierarchy?.any { destino ->
                destino.route == aba.rota || destino.route?.startsWith("${aba.rota}?") == true
            } == true

            NavigationBarItem(
                selected = selecionada,
                onClick = { navController.navegarParaAba(aba) },
                icon = { Icon(aba.icone, contentDescription = null) },
                label = {
                    Text(
                        text = aba.rotulo,
                        fontWeight = if (aba == Aba.EVENTO) FontWeight.Bold else FontWeight.Normal,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                ),
            )
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
