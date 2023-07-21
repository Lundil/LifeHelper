package com.example.myapplicationtest.activity.gym;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.ViewTarget;
import com.example.myapplicationtest.R;
import com.example.myapplicationtest.entity.gym.Exercice;

import java.lang.annotation.Target;

import pl.droidsonroids.gif.GifImageView;

public class AddExerciceActivity extends AppCompatActivity {

    @SuppressLint("CheckResult")
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
        setContentView(R.layout.popup_add_exercice);

        ImageView exerciceImageView = (ImageView) findViewById(R.id.exerciceImageview);

        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView typeTextView = findViewById(R.id.typeTextView);
        TextView tipsTextView = findViewById(R.id.tipsTextView);

        //Button saveButton = popupView.findViewById(R.id.saveButton);
        Button dismissButton = findViewById(R.id.dismissButton);

        //set
        Exercice exo = null;
        if(getIntent().getSerializableExtra("exo") != null) {
            exo = (Exercice) getIntent().getSerializableExtra("exo");
            if(exo != null){
                nameTextView.setText(exo.getName());
                typeTextView.setText("Groupe : "+exo.getType());
                tipsTextView.setText("Tips : Voir "+exo.getTips());

                String uri = "@drawable/"+exo.getImage();
                int imageResource = getResources().getIdentifier(uri, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                Glide.with(this).load(imageResource).into(exerciceImageView);
            }
        }
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddExerciceActivity.this, ExerciceActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

    }
}
