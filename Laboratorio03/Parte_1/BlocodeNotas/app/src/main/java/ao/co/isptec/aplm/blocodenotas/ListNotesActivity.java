package ao.co.isptec.aplm.blocodenotas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ListNotesActivity extends AppCompatActivity {
    private static final String KEY_NOTES = "notes";
    private ArrayList<Note> notes = new ArrayList<>();
    private ArrayAdapter<Note> adapter;
    private final ActivityResultLauncher<Intent> createNoteLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                Intent data = result.getData();
                if (result.getResultCode() == RESULT_OK && data != null) {
                    notes.add(new Note(
                            data.getStringExtra(CreateNoteActivity.EXTRA_TITLE),
                            data.getStringExtra(CreateNoteActivity.EXTRA_TEXT)));
                    adapter.notifyDataSetChanged();
                }
            });

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_list_notes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (savedInstanceState != null) {
            notes = (ArrayList<Note>) savedInstanceState.getSerializable(KEY_NOTES);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        ListView listNotes = findViewById(R.id.listNotes);
        listNotes.setAdapter(adapter);

        listNotes.setOnItemClickListener((parent, view, position, id) -> {
            Note note = notes.get(position);
            Intent intent = new Intent(this, ReadNoteActivity.class);
            intent.putExtra(ReadNoteActivity.EXTRA_TITLE, note.getTitle());
            intent.putExtra(ReadNoteActivity.EXTRA_TEXT, note.getText());
            startActivity(intent);
        });

        Button buttonNewNote = findViewById(R.id.buttonNewNote);
        buttonNewNote.setOnClickListener(v ->
                createNoteLauncher.launch(new Intent(this, CreateNoteActivity.class)));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_NOTES, notes);
    }
}