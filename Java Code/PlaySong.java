package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;


public class PlaySong extends AppCompatActivity {
    TextView textView;
    ImageView play, previous, next;
    ArrayList<File> songs;
    MediaPlayer mediaPlayer;
    String textContent;
    int position;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        textView = findViewById(R.id.textView);
        play = findViewById(R.id.play);
        next=findViewById(R.id.next);
        previous=findViewById(R.id.previous);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        songs =(ArrayList) bundle.getParcelableArrayList("songList");
        textContent=intent.getStringExtra("currentSong");
        textView.setText(textContent);
        position = intent.getIntExtra("position",0);
        Uri uri = Uri.parse(songs.get(position).toString());
        mediaPlayer = MediaPlayer.create(this,uri);
        mediaPlayer.start();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    play.setImageResource(R.drawable.play);
                    mediaPlayer.pause();
                }
                else{
                    play.setImageResource(R.drawable.pause);
                    mediaPlayer.start();
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                if(position!=0){
                    position = position-1;
                }
                else{
                    position = songs.size()-1;
                }
                Uri uri = Uri.parse(songs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                mediaPlayer.start();
                play.setImageResource(R.drawable.pause);
                textContent = songs.get(position).getName().toString();
                textView.setText(textContent);
            }

        });



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                if (position!= songs.size()-1){
                    position = position+1;
                }
                else{
                    position =0;
                }
                Uri uri = Uri.parse(songs.get(position).toString());
                mediaPlayer = mediaPlayer.create(getApplicationContext(),uri);
                mediaPlayer.start();
                play.setImageResource(R.drawable.pause);
                textContent = songs.get(position).getName().toString();
                textView.setText(textContent);
            }
        });


    }
}