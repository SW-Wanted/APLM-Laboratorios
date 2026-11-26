package ao.co.isptec.aplm.fileexplorer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private Button internalStorage;
    private Button externalStorage;

    @Override
    public void onCreate(final Bundle icicle) {
        super.onCreate(icicle);
        this.setContentView(R.layout.activity_main);

        this.internalStorage = (Button) findViewById(R.id.main_internal_storage_button);
        this.internalStorage.setOnClickListener(this);

        this.externalStorage = (Button) findViewById(R.id.main_external_storage_button);
        this.externalStorage.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.equals(internalStorage)) {
            startActivity(new Intent(this, InternalStorage.class));
        } else if (v.equals(externalStorage)) {
            startActivity(new Intent(this, ExternalStorage.class));
        }
    }
}