package com.example.androidjavacourse;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Game extends AppCompatActivity {

    public static final String TAG ="GuessGame";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Game buttons
        ImageButton btn1 = (ImageButton) findViewById(R.id.gameBtn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameBtnPressed(1);
            }
        });
        ImageButton btn2 = (ImageButton) findViewById(R.id.gameBtn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameBtnPressed(2);
            }
        });
        ImageButton btn3 = (ImageButton) findViewById(R.id.gameBtn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameBtnPressed(3);
            }
        });
        ImageButton btn4 = (ImageButton) findViewById(R.id.gameBtn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameBtnPressed(4);
            }
        });
        FloatingActionButton refBtn = (FloatingActionButton) findViewById(R.id.refreshBtn);
        refBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshGame();
            }
        });
    }

    public void gameBtnPressed(int id){
        Log.d(TAG, "Button " + id + " clicked");
    }
    public void refreshGame(){
        Log.d(TAG, "RefreshBtn clicked");
    }
}