package com.example.loginsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.VideoView;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {

    VideoView videoView;
    final static int totalSplashScreenTime = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        //This removed the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        // this is for the main splash screen video
        videoView = (VideoView) findViewById(R.id.splashView);
        String splash_path = "android.resource://" + getPackageName() + "/" + R.raw.splash_view;
        Uri videoSplash = Uri.parse(splash_path);
        videoView.setVideoURI(videoSplash);
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoView.start();
            }
        });

        //calling new activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, loginAndSignUpAsk.class);
                startActivity(intent);
                finish();
            }
        },totalSplashScreenTime );

    }
}