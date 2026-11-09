package ao.co.isptec.aplm.simpleactivity;

import android.os.Bundle;
import android.view.View;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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
        Log.i(TAG, "onCreate");
    }

    private static final String TAG = "MainActivity";

    @Override protected void onStart()   { super.onStart();   Log.i(TAG, "onStart"); }
    @Override protected void onResume()  { super.onResume();  Log.i(TAG, "onResume"); }
    @Override protected void onPause()   { super.onPause();   Log.i(TAG, "onPause"); }
    @Override protected void onStop()    { super.onStop();    Log.i(TAG, "onStop"); }
    @Override protected void onRestart() { super.onRestart(); Log.i(TAG, "onRestart"); }
    @Override protected void onDestroy() { super.onDestroy(); Log.i(TAG, "onDestroy"); }
    public void finishButtonPressed(View view) {
        finish();
    }
}