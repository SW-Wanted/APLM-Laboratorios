package ao.co.isptec.aplm.helloandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ao.co.isptec.aplm.helloandroid.ui.theme.HelloAndroidTheme

const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
private const val TAG = "MainActivity"
fun dividir(a: Int, b: Int): Int = a / b

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Aplicação iniciada com sucesso.")
        Log.i(TAG, "onCreate (savedInstanceState = $savedInstanceState)")
        enableEdgeToEdge()
        setContent {
            HelloAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MessageScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    override fun onStart() { super.onStart(); Log.i(TAG, "onStart") }
    override fun onResume() { super.onResume(); Log.i(TAG, "onResume") }
    override fun onPause() { super.onPause(); Log.i(TAG, "onPause") }
    override fun onStop() { super.onStop(); Log.i(TAG, "onStop") }
    override fun onDestroy() { super.onDestroy(); Log.i(TAG, "onDestroy") }
}

@Composable
fun MessageScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var text by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Escreva a sua mensagem") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                Toast.makeText(context, "Botão clicado!", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "Utilizador digitou a mensagem: $text")
                if (text.isBlank()) {
                    Log.w(TAG, "Foi enviada uma mensagem vazia.")
                }
                val intent = Intent(context, DisplayMessageActivity::class.java).apply {
                    putExtra(EXTRA_MESSAGE, text)
                }
                context.startActivity(intent)
            }
        ) {
            Text("Enviar")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = {
                if (text.trim().equals("crash", ignoreCase = true)) {
                    Log.w(TAG, "A provocar uma excepção não tratada...")
                    dividir(10, 0)
                } else {
                    try {
                        val resultado = dividir(10, 0)
                        Log.d(TAG, "Resultado: $resultado")
                    } catch (e: ArithmeticException) {
                        Log.e(TAG, "Erro intencional: divisão por zero", e)
                        Toast.makeText(context, "Erro registado no Logcat", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        ) {
            Text("Provocar erro (÷ 0)")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessageScreenPreview() {
    HelloAndroidTheme {
        MessageScreen()
    }
}