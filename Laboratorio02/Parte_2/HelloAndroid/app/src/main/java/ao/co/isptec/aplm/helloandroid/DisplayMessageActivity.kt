package ao.co.isptec.aplm.helloandroid

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ao.co.isptec.aplm.helloandroid.ui.theme.HelloAndroidTheme

private const val TAG = "DisplayMessageActivity"

class DisplayMessageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val mensagem: String? = intent.getStringExtra(EXTRA_MESSAGE)
        val palavras = mensagem?.split(" ")?.filter { it.isNotBlank() } ?: listOf()
        val wordCount = palavras.size

        val sumario = when {
            wordCount == 0 -> "Nenhuma palavra encontrada!"
            wordCount == 1 -> "Apenas uma palavra!"
            else -> "Foram digitadas $wordCount palavras."
        }
        var textoEcra = sumario
        if (mensagem != null && wordCount > 0) {
            textoEcra = "\"$mensagem\"\n\n$sumario"
        }

        Log.d(TAG, "Palavras: $palavras | $sumario")

        setContent {
            HelloAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp)
                    ) {
                        Text(text = textoEcra, fontSize = 24.sp, textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}