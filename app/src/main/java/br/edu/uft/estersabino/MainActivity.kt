package br.edu.uft.estersabino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.edu.uft.estersabino.ui.nav.AppEsterSabino
import br.edu.uft.estersabino.ui.theme.TemaEsterSabino

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemaEsterSabino {
                AppEsterSabino()
            }
        }
    }
}
