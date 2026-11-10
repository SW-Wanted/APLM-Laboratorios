package ao.co.isptec.aplm.blocodenotas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateNoteActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE = "note_title";
    public static final String EXTRA_TEXT = "note_text";

    private EditText editTitle;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_create_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        editTitle = findViewById(R.id.editTitle);
        editText = findViewById(R.id.editText);
        Button buttonOk = findViewById(R.id.buttonOk);
        Button buttonCancel = findViewById(R.id.buttonCancel);

        buttonOk.setOnClickListener(v -> acceptNote());
        buttonCancel.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    private void acceptNote() {
        String title = editTitle.getText().toString().trim();
        if (title.isEmpty()) {
            title = "Sem título";
        }
        Intent result = new Intent();
        result.putExtra(EXTRA_TITLE, title);
        result.putExtra(EXTRA_TEXT, editText.getText().toString());
        setResult(RESULT_OK, result);
        finish();
    }
}