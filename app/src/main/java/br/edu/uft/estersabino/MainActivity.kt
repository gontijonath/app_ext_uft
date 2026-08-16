package br.edu.uft.estersabino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import br.edu.uft.estersabino.data.carregarConteudoRemoto
import br.edu.uft.estersabino.ui.nav.AppEsterSabino
import br.edu.uft.estersabino.ui.theme.TemaEsterSabino
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemaEsterSabino {
                AppEsterSabino()
            }
        }

        // Busca o texto mais atual no Supabase (se configurado) e atualiza as
        // telas por baixo dos panos. Sem internet ou sem Supabase configurado,
        // não faz nada e o app segue com os textos padrão — ver ConteudoRemoto.kt.
        lifecycleScope.launch {
            carregarConteudoRemoto()
        }
    }
}
