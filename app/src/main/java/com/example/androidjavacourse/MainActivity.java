package com.example.androidjavacourse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public static final String TAG ="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Intents
        Intent hello = new Intent(this, HelloWorld.class);
        Intent calculator = new Intent(this, Calculator.class);
        Intent game = new Intent(this, Game.class);

        // Buttons
        Button startHello = (Button) findViewById(R.id.mainHelloBtn);
        startHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "helloBtn clicked");
                startActivity(hello);
            }
        });

        Button startCalc = (Button) findViewById(R.id.mainCalcBtn);
        startCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "calcBtn clicked");
                startActivity(calculator);
            }
        });

        Button startGame = (Button) findViewById(R.id.mainGameBtn);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "gameBtn clicked");
                startActivity(game);
            }
        });
    }
}