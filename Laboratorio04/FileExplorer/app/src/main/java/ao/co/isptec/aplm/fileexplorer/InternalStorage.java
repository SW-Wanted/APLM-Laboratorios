package ao.co.isptec.aplm.fileexplorer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InternalStorage extends Activity {

    private static final String LINE_SEP = System.getProperty("line.separator");
    private static final String FILE_NAME = "test.txt";
    private final ExecutorService io = Executors.newSingleThreadExecutor();
    private EditText input;
    private TextView output;
    private Button write;
    private Button read;

    @Override
    public void onCreate(final Bundle icicle) {
        super.onCreate(icicle);
        this.setContentView(R.layout.internal_storage);

        this.input = (EditText) findViewById(R.id.internal_storage_input);
        this.output = (TextView) findViewById(R.id.internal_storage_output);

        this.write = (Button) findViewById(R.id.internal_storage_write_button);
        this.write.setOnClickListener(new OnClickListener() {
            public void onClick(final View v) {
                write();
            }
        });

        this.read = (Button) findViewById(R.id.internal_storage_read_button);
        this.read.setOnClickListener(new OnClickListener() {
            public void onClick(final View v) {
                read();
            }
        });
    }

    private void write() {
        final String text = input.getText().toString();      // UI thread
        io.execute(() -> {                                    // worker
            Log.d(Constants.LOG_TAG, "internal write [" + Thread.currentThread().getName() + "]");
            boolean ok = false;
            try (FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
                fos.write(text.getBytes(StandardCharsets.UTF_8));
                ok = true;
            } catch (IOException e) {
                Log.e(Constants.LOG_TAG, "IO problem", e);
            }
            final boolean success = ok;
            runOnUiThread(() -> {                             // UI thread
                Toast.makeText(this, success ? "File written" : "Write failed",
                        Toast.LENGTH_SHORT).show();
                if (success) {
                    input.setText("");
                    output.setText("");
                }
            });
        });
    }

    private void read() {
        io.execute(() -> {
            Log.d(Constants.LOG_TAG, "internal read [" + Thread.currentThread().getName() + "]");
            StringBuilder sb = new StringBuilder();
            boolean ok = false;
            try (FileInputStream fis = openFileInput(FILE_NAME);
                 Scanner scanner = new Scanner(fis)) {
                while (scanner.hasNextLine()) {
                    sb.append(scanner.nextLine()).append(LINE_SEP);
                }
                ok = true;
            } catch (IOException e) {
                Log.e(Constants.LOG_TAG, "File not found", e);
            }
            final boolean success = ok;
            final String content = sb.toString();
            runOnUiThread(() -> {
                output.setText(content);
                Toast.makeText(this, success ? "File read" : "Read failed",
                        Toast.LENGTH_SHORT).show();
            });
        });
    }

    @Override
    protected void onDestroy() {
        io.shutdown();
        super.onDestroy();
    }
}
