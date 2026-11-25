package ao.co.isptec.aplm.basicthreading;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.activity.EdgeToEdge;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "BasicThreading";
    private static final long TICK_MS = 1000;

    private Thread worker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button start = findViewById(R.id.buttonStart);
        Button stop = findViewById(R.id.buttonStop);
        start.setOnClickListener(v -> startCounting());
        stop.setOnClickListener(v -> stopCounting());
    }

    /**
     * @brief Inicia uma nova thread de contagem periódica ("TickWorker").
     *
     * Garante que não existam duas threads ativas em simultâneo chamando
     * {@link #stopCounting()} antes de criar e iniciar a nova instância. O contador
     * começa em {@code 0}, regista um log a cada {@link #TICK_MS} milissegundos e
     * encerra de forma limpa quando {@link InterruptedException} é lançada.
     */
    private void startCounting() {
        stopCounting();
        worker = new Thread(() -> {
            int counter = 0;
            while (true) {
                Log.d(TAG, "tick " + counter + " [" + Thread.currentThread().getName() + "]");
                counter++;
                try {
                    Thread.sleep(TICK_MS);
                } catch (InterruptedException e) {
                    Log.d(TAG, "worker interrompida em " + counter);
                    return;
                }
            }
        }, "TickWorker");
        worker.start();
        Log.d(TAG, "Start pressionado [" + Thread.currentThread().getName() + "]");
    }

    private void stopCounting() {
        if (worker != null) {
            worker.interrupt();
            worker = null;
        }
    }

    @Override
    protected void onDestroy() {
        stopCounting();
        super.onDestroy();
    }
}