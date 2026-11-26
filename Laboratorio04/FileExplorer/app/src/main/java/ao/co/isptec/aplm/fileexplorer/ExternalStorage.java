package ao.co.isptec.aplm.fileexplorer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExternalStorage extends Activity {

    private final ExecutorService io = Executors.newSingleThreadExecutor();
    // wrap some operations that are likely to be needed in more than one place in FileUtil

    private EditText input;
    private TextView output;
    private Button write;
    private Button read;

    @Override
    public void onCreate(final Bundle icicle) {
        super.onCreate(icicle);
        this.setContentView(R.layout.external_storage);

        this.input = (EditText) findViewById(R.id.external_storage_input);
        this.output = (TextView) findViewById(R.id.external_storage_output);

        this.write = (Button) findViewById(R.id.external_storage_write_button);
        this.write.setOnClickListener(new OnClickListener() {
            public void onClick(final View v) {
                write();
            }
        });

        this.read = (Button) findViewById(R.id.external_storage_read_button);
        this.read.setOnClickListener(new OnClickListener() {
            public void onClick(final View v) {
                read();
            }
        });
    }

    private void write() {
        final String text = input.getText().toString();
        io.execute(() -> {
            Log.d(Constants.LOG_TAG, "external write [" + Thread.currentThread().getName() + "]");
            boolean ok = FileUtil.isExternalStorageWritable()
                    && FileUtil.writeStringAsFile(text, new File(getExternalFilesDir(null), "test.txt"));
            runOnUiThread(() -> {
                Toast.makeText(this, ok ? "File written" : "Write failed", Toast.LENGTH_SHORT).show();
                if (ok) {
                    input.setText("");
                    output.setText("");
                }
            });
        });
    }

    private void read() {
        io.execute(() -> {
            Log.d(Constants.LOG_TAG, "external read [" + Thread.currentThread().getName() + "]");
            File file = new File(getExternalFilesDir(null), "test.txt");
            String content = FileUtil.isExternalStorageReadable() && file.canRead()
                    ? FileUtil.readFileAsString(file) : null;
            runOnUiThread(() -> {
                if (content != null) {
                    output.setText(content);
                    Toast.makeText(this, "File read", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Unable to read file: " + file.getAbsolutePath(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        io.shutdown();
        super.onDestroy();
    }
}

