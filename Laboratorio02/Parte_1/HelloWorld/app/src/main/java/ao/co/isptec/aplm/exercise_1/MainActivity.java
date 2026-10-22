package ao.co.isptec.aplm.exercise_1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE =
            "ao.co.isptec.aplm.exercise_1.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {
                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars());

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom);

                    return insets;
                });

        Log.d("MainActivity",
                "Minha primeira mensagem de depuração.");

        EditText editText = findViewById(R.id.edit_message);
        Button sendButton = findViewById(R.id.send_btn);

        sendButton.setOnClickListener(view -> {
            String message = editText.getText().toString();

            Intent intent = new Intent(
                    MainActivity.this,
                    DisplayMessageActivity.class);

            intent.putExtra(EXTRA_MESSAGE, message);

            startActivity(intent);
        });
    }
}