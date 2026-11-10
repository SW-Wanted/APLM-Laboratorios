package ao.co.isptec.aplm.blocodenotas;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class ReadNoteActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE = "note_title";
    public static final String EXTRA_TEXT = "note_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_read_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView textTitle = findViewById(R.id.textTitle);
        TextView textContent = findViewById(R.id.textContent);
        textTitle.setText(getIntent().getStringExtra(EXTRA_TITLE));
        textContent.setText(getIntent().getStringExtra(EXTRA_TEXT));
    }
}