package com.example.androidjavacourse.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidjavacourse.Calculator;
import com.example.androidjavacourse.Exam;
import com.example.androidjavacourse.Game;
import com.example.androidjavacourse.HelloWorld;
import com.example.androidjavacourse.R;
import com.example.androidjavacourse.YtjSearch;
import com.example.androidjavacourse.databinding.FragmentHomeBinding;
import com.google.android.material.textfield.TextInputEditText;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    public static final String TAG ="MainActivity";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Intents
        Intent hello = new Intent(getActivity(), HelloWorld.class);
        Intent calculator = new Intent(getActivity(), Calculator.class);
        Intent game = new Intent(getActivity(), Game.class);
        Intent ytj = new Intent(getActivity(), YtjSearch.class);
        Intent exam = new Intent(getActivity(), Exam.class);

        // Buttons
        Button startHello = (Button) root.findViewById(R.id.mainHelloBtn);
        startHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "helloBtn clicked");
                startActivity(hello);
            }
        });

        Button startCalc = (Button) root.findViewById(R.id.mainCalcBtn);
        startCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "calcBtn clicked");
                startActivity(calculator);
            }
        });

        Button startGame = (Button) root.findViewById(R.id.mainGameBtn);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "gameBtn clicked");
                startActivity(game);
            }
        });

        Button ytjSearch = (Button) root.findViewById(R.id.mainSearchBtn);
        ytjSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText searchText = (TextInputEditText) root.findViewById(R.id.searchTextBox);
                if(String.valueOf(searchText.getText()).equals("") == false)
                {
                    ytj.putExtra("inputText", String.valueOf(searchText.getText()));
                    startActivity(ytj);
                }
            }
        });

        Button openExam = (Button) root.findViewById(R.id.openExamBtn);
        openExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "examBtn clicked");
                startActivity(exam);
            }
        });
        // Tämä viimetteeksi
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}