package ao.co.isptec.aplm.helloandroid

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
        val mensagem = intent.getStringExtra(EXTRA_MESSAGE) ?: ""
        val palavras = mensagem.split(" ").filter { it.isNotBlank() }
        val bundle = PalavraBundle(palavras)
        val resumo = gerarResumo(bundle)
        Log.d(TAG, "$bundle\n$resumo")

        val titulo = mensagem.ifBlank { "Nenhuma mensagem recebida." }

        setContent {
            HelloAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp)
                    ) {
                        Text(text = titulo, fontSize = 24.sp, textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(text = resumo, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}