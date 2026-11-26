package ao.co.isptec.aplm.simpleimagedownload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.net.URL;


public class MainActivity extends AppCompatActivity implements Handler.Callback {
    public static final String KEY_HANDLER_MSG = "status";
    private static final String IMAGE_SOURCE = "https://github.com/SW-Wanted.png";

    // i. Handler associado à UI thread
    private final Handler handler = new Handler(Looper.getMainLooper(), this);
    private Button btnDownloadFile;
    private Button btnDownloadFileAsync;
    private TextView statusTextView;
    private ImageView imageView;

    // declare handler
    private Runnable imageDownloader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDownloadFile = (Button) findViewById(R.id.btnDownloadFile);
        btnDownloadFileAsync = (Button) findViewById(R.id.btnDownloadFileAsync);
        imageView = (ImageView) findViewById(R.id.image_view);
        statusTextView = (TextView) findViewById(R.id.status);

        // exercise 1 - step 1
        btnDownloadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(imageDownloader, "Download thread").start();
                statusTextView.setText(getString(R.string.download_started));
            }
        });

        // exercise 1 - step 1
        imageDownloader = new Runnable() {
            public void run() {
                // exercise 1 - step 1
                downloadImage(IMAGE_SOURCE);
            }
        };

        // exercise 1 - step 2 to implement
        btnDownloadFileAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // initialize handler
    }

    // ii. Callback executado na UI thread
    @Override
    public boolean handleMessage(Message msg) {
        String text = msg.getData().getString(KEY_HANDLER_MSG);
        statusTextView.setText(text);
        return true;
    }

    // iii. Chamado pela thread de download
    private void sendMessage(String what) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_HANDLER_MSG, what);
        Message message = handler.obtainMessage();
        message.setData(bundle);
        handler.sendMessage(message);
    }

    private void downloadImage(String urlStr){
        try {
//            URL imageUrl = new URL("https", "android.com", "/images/froyo.png");
            URL imageUrl = new URL(urlStr);
            Bitmap image = BitmapFactory.decodeStream(imageUrl.openStream());
            if (image != null) {
                sendMessage(getString(R.string.download_success));
            } else {
                sendMessage(getString(R.string.download_failed_stream));
            }
        } catch (Exception e) {
            sendMessage(getString(R.string.download_failed));
            e.printStackTrace();
        }
    }
}