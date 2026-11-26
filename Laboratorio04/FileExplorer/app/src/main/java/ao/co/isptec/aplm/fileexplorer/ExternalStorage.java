package ao.co.isptec.aplm.fileexplorer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class ExternalStorage extends Activity {

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

    private File getDataFile() {
        return new File(getExternalFilesDir(null), "test.txt");
    }

    private void write() {
        if (!FileUtil.isExternalStorageWritable()) {
            Toast.makeText(this, "External storage not writable", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean ok = FileUtil.writeStringAsFile(input.getText().toString(), getDataFile());
        Toast.makeText(this, ok ? "File written" : "Write failed", Toast.LENGTH_SHORT).show();
        if (ok) {
            input.setText("");
            output.setText("");
        }
    }

    private void read() {
        if (!FileUtil.isExternalStorageReadable()) {
            Toast.makeText(this, "External storage not readable", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = getDataFile();
        String content = file.isFile() ? FileUtil.readFileAsString(file) : null;
        if (content != null) {
            output.setText(content);
            Toast.makeText(this, "File read", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Unable to read file: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        }
    }
}

