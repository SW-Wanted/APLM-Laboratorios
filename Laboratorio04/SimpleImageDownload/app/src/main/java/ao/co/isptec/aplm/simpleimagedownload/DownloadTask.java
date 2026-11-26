package ao.co.isptec.aplm.simpleimagedownload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Void, Bitmap> {
    private static final String TAG = "DownloadTask";
    private final ImageView imageView;
    private final TextView statusText;

    public DownloadTask(ImageView imageView, TextView statusText) {
        this.imageView = imageView;
        this.statusText = statusText;
    }

    @Override
    protected void onPreExecute() {            // UI thread
        Log.d(TAG, "onPreExecute [" + Thread.currentThread().getName() + "]");
        imageView.setImageDrawable(null);
        statusText.setText(R.string.download_started);
    }

    @Override
    protected Bitmap doInBackground(String... inputUrls) {   // worker thread
        Log.d(TAG, "doInBackground [" + Thread.currentThread().getName() + "]");
        try (InputStream in = new URL(inputUrls[0]).openStream()) {
            return BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            Log.e(TAG, "Download falhou", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap result) {   // UI thread
        Log.d(TAG, "onPostExecute [" + Thread.currentThread().getName() + "]");
        if (result != null) {
            imageView.setImageBitmap(result);
            statusText.setText(R.string.download_success);
        } else {
            statusText.setText(R.string.download_failed);
        }
    }
}