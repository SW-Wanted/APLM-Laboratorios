package ao.co.isptec.aplm.exercise_3;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTarefa;
    private ListView listViewTarefas;
    private ArrayList<String> tarefas;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTarefa = findViewById(R.id.editTextTarefa);
        listViewTarefas = findViewById(R.id.listViewTarefas);

        tarefas = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                tarefas
        );

        listViewTarefas.setAdapter(adapter);

        editTextTarefa.setOnKeyListener((view, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER
                    && event.getAction() == KeyEvent.ACTION_DOWN) {
                adicionarTarefa();
                return true;
            }

            return false;
        });

        editTextTarefa.setOnEditorActionListener((view, actionId, event) -> {
            boolean actionConcluir =
                    actionId == EditorInfo.IME_ACTION_DONE;

            boolean teclaEnter =
                    event != null
                            && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                            && event.getAction() == KeyEvent.ACTION_DOWN;

            if (actionConcluir || teclaEnter) {
                adicionarTarefa();
                return true;
            }

            return false;
        });
    }

    private void adicionarTarefa() {
        String tarefa = editTextTarefa.getText()
                .toString()
                .trim();

        if (tarefa.isEmpty()) {
            return;
        }

        tarefas.add(0, tarefa);
        adapter.notifyDataSetChanged();

        editTextTarefa.setText("");
    }
}