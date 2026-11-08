package ao.co.isptec.aplm.listadetarefas

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ao.co.isptec.aplm.listadetarefas.ui.theme.ListaDeTarefasTheme

private const val TAG = "ListaDeTarefas"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListaDeTarefasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TaskScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TaskScreen(modifier: Modifier = Modifier) {

    val tasks = remember { mutableStateListOf<Task>() }
    var text by rememberSaveable { mutableStateOf("") }
    var erro by remember { mutableStateOf<String?>(null) }

    fun adicionarTarefa() {
        val titulo = text.trim()
        erro = when {
            titulo.isEmpty() -> "Escreva uma tarefa."
            tasks.any { it.title.equals(titulo, ignoreCase = true) } -> "Essa tarefa já existe."
            else -> null
        }
        if (erro == null) {
            tasks.add(Task(titulo))
            Log.d(TAG, "Tarefa adicionada: $titulo (total = ${tasks.size})")
            text = ""
        } else {
            Log.w(TAG, "Tarefa rejeitada: $erro")
        }
    }

    // 4.4: conclusão (substitui o elemento por uma cópia) e remoção
    fun alternarConclusao(task: Task) {
        val i = tasks.indexOf(task)
        if (i >= 0) tasks[i] = task.copy(isDone = !task.isDone)
        Log.d(TAG, "Tarefa '${task.title}' concluída = ${!task.isDone}")
    }

    fun apagarTarefa(task: Task) {
        tasks.remove(task)
        Log.d(TAG, "Tarefa apagada: ${task.title}")
    }

    val concluidas = tasks.count { it.isDone }

    Column(modifier = modifier.padding(16.dp)) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                erro = null
            },
            label = { Text("Nova tarefa") },
            isError = erro != null,
            supportingText = { erro?.let { Text(it) } },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { adicionarTarefa() }),   // botão OK do teclado
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { adicionarTarefa() }) {
            Text("Add Task")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "$concluidas de ${tasks.size} tarefas concluídas",
            style = MaterialTheme.typography.labelLarge
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            items(tasks, key = { it.title }) { task ->
                TaskRow(
                    task = task,
                    onToggle = { alternarConclusao(task) },
                    onDelete = { apagarTarefa(task) }
                )
            }
        }
    }
}

@Composable
fun TaskRow(task: Task, onToggle: () -> Unit, onDelete: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
    ) {
        Checkbox(checked = task.isDone, onCheckedChange = { onToggle() })
        Text(
            text = task.title,
            modifier = Modifier.weight(1f),
            textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None,
            color = if (task.isDone) MaterialTheme.colorScheme.outline
            else MaterialTheme.colorScheme.onSurface
        )
        IconButton(onClick = onDelete) {
            Text("✕")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskRowPreview() {
    ListaDeTarefasTheme {
        Column {
            TaskRow(Task("Estudar Kotlin"), onToggle = {}, onDelete = {})
            TaskRow(Task("Entregar o memorando", isDone = true), onToggle = {}, onDelete = {})
        }
    }
}