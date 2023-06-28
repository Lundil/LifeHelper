package com.example.myapplicationtest.activity.raccoons;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationtest.R;
import com.example.myapplicationtest.activity.MainActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class RaccoonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Masquer la barre de navigation
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        setContentView(R.layout.activity_raccoon);

        // Obtenez une référence au bouton Accueil
        Button homeButton = findViewById(R.id.buttonReturn);
        Button anotherRaccoonButton = findViewById(R.id.anotherRaccoonButton);
        ImageView imageView = findViewById(R.id.raccoonImageView);
        // Ajoutez un écouteur de clic pour le bouton Accueil
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Démarrer l'activité principale (accueil)
                Intent intent = new Intent(RaccoonActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        anotherRaccoonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Démarrer l'activité principale (accueil)
                anotherRaccoonButtonClicked(imageView);
            }
        });

    }
    private void anotherRaccoonButtonClicked(ImageView imageView){
        imageView.setImageDrawable(null);
        new DownloadImageTask(imageView)
                .execute("https://i.natgeofe.com/k/6289c775-a06c-426a-badb-8d181a55237b/raccoon-grass_3x4.jpg");

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
