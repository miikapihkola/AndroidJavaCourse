package com.example.androidjavacourse;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends AppCompatActivity {

    public static final String TAG ="GuessGame";
    public Integer rightChoice = 0;
    public ArrayList<ImageButton> btnList = new ArrayList<ImageButton>();
    public boolean canClick = true;
    public Integer[] stats = {0,0,0,0};
    public Integer[] currentgame = {0,0};
    MMKV kv;
    Drawable btnBg;

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
                gameBtnPressed(0);
            }
        });
        ImageButton btn2 = (ImageButton) findViewById(R.id.gameBtn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameBtnPressed(1);
            }
        });
        ImageButton btn3 = (ImageButton) findViewById(R.id.gameBtn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameBtnPressed(2);
            }
        });
        ImageButton btn4 = (ImageButton) findViewById(R.id.gameBtn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameBtnPressed(3);
            }
        });
        FloatingActionButton refBtn = (FloatingActionButton) findViewById(R.id.refreshBtn);
        refBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshGame();

            }

        });

        // MMKV Datastore
        // https://github.com/Tencent/MMKV/wiki/android_tutorial
        String rootDir = MMKV.initialize(this);
        System.out.println("mmkv root: " + rootDir);
        kv = MMKV.defaultMMKV();

        try {
            String[] stats2 = kv.decodeString("stats").replace(" ", "").replace("[", "").replace("]","").split(",");
            Log.d(TAG, "Pituus: " + String.valueOf(stats2.length) + " // Eka solu " + String.valueOf(stats2[0]));
            if (stats2.length > 0) {
                for (int i = 0; i < stats2.length; i++) {
                    stats[i] = Integer.parseInt(stats2[i]);
                }
                Log.d(TAG, "Stats loaded");
            }
            else {
                Log.d(TAG, "No stats found");
            }
        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.gameToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        btnList.add(btn1);
        btnList.add(btn2);
        btnList.add(btn3);
        btnList.add(btn4);

        btnBg = btn1.getBackground();


        System.out.println();
        newGame();
    }


    // Buttons
    public void gameBtnPressed(int id){
        Log.d(TAG, "Button " + id + " clicked");
        if (id == rightChoice && canClick){
            success(id);
        }
        else if(canClick) {
            failed(id);
        }
    }
    public void refreshGame(){
        Log.d(TAG, "RefreshBtn clicked");
        stats[2] = 0;
        stats[3] = 0;
        kv.encode("stats", Arrays.toString(stats));
        newGame();
    }

    // Set New Game Round
    public void newGame(){
        Log.d(TAG,"New game round started");
        for(int i=0; i<currentgame.length; i++){
                currentgame[i] = 0;
        }
        TextView score = (TextView) findViewById(R.id.scoreboard_counter);
        TextView scorePercent = (TextView) findViewById(R.id.scoreboard_percent);
        Double percent = stats[2].doubleValue() / stats[3].doubleValue() * 100;
        rightChoice = getRng();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (ImageButton btn: btnList) {
                    btn.setVisibility(View.VISIBLE);
                    btn.setImageResource(android.R.drawable.star_big_on);
                    btn.setBackground(btnBg);
                }
                score.setText(stats[0] + " / " + stats[1]);
                scorePercent.setText(percent.intValue() + " %");
            }
        });
        Log.d(TAG,"New RNG is: " + rightChoice);
        canClick = true;
    }

    // Failed
    public void failed(int id){
        Log.d(TAG,"Failed guess");
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_animation);
        btnList.get(id).startAnimation(animation);
        btnList.get(id).setVisibility(View.INVISIBLE);

        // Game logic
        stats[3]++;
        currentgame[1]++;
    }

    // Success
    public void success(int id){
        Log.d(TAG,"Successful guess");
        canClick = false;
        Toast.makeText(this, getResources().getString(R.string.game_toast), Toast.LENGTH_SHORT).show();

        //Game logic
        stats[3]++;
        stats[2]++;
        currentgame[1]++;
        currentgame[0]++;
        stats[1] = currentgame[1];
        stats[0] = currentgame[0];
        kv.encode("stats", Arrays.toString(stats));

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.spin_animation);
        btnList.get(id).startAnimation(animation);
        btnList.get(id).postDelayed(new Runnable() {
            @Override
            public void run() {
                btnList.get(id).setBackground(null);
                btnList.get(id).setImageResource(android.R.drawable.sym_action_email);
            }
        }, animation.getDuration() + 150);
        //btnList.get(id).setVisibility(View.INVISIBLE);

        // Delay for new game
        Timer timer = new Timer("Timer");
        TimerTask tasknew = new TimerTask() {
            @Override
            public void run() {
                newGame();
            }
        };
        Log.d(TAG,"Timer started...");
        timer.schedule(tasknew, 2000L);
    }

    // Get Rng
    public int getRng(){
        Random rand = new Random();
        return rand.nextInt(4);
    }
}