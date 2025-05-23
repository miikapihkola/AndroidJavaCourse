package com.example.androidjavacourse.ui.notifications;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidjavacourse.R;
import com.example.androidjavacourse.databinding.FragmentNotificationsBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class NotificationsFragment extends Fragment {

    public static final String TAG ="Timer";
    private FragmentNotificationsBinding binding;
    NumberPicker numPicker;
    CountDownTimer timer;
    MaterialButtonToggleGroup materialButtonToggleGroup;
    Boolean timerOn;
    MaterialButton starBtn;
    TextView textTimer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        numPicker = root.findViewById(R.id.numberpicker_main_picker);
        numPicker.setMinValue(0);
        numPicker.setMaxValue(60);
        String[] dispValues = new String[61];
        for(int i=0; i<=60; i++){
            dispValues[i] = String.valueOf(i) + " s";
        }
        numPicker.setDisplayedValues(dispValues);
        numPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                numberPickerChange();
            }
        });

        timerOn = false;
        starBtn = (MaterialButton) root.findViewById(R.id.btn_start);
        textTimer = (TextView) root.findViewById(R.id.textTimer);
        textTimer.setVisibility(View.INVISIBLE);
        materialButtonToggleGroup = (MaterialButtonToggleGroup) root.findViewById(R.id.toggleBtnGroup);
        materialButtonToggleGroup.addOnButtonCheckedListener(
                new MaterialButtonToggleGroup.OnButtonCheckedListener() {
                    @Override
                    public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                        if (isChecked) {
                            if (checkedId == R.id.btn_start) {
                                startBtnPressed();
                            } else if (checkedId == R.id.btn_pause) {
                                if (timerOn) {
                                    pauseBtnPressed();
                                }
                                else {
                                    materialButtonToggleGroup.clearChecked();
                                }
                            } else if (checkedId == R.id.btn_stop) {
                                stopBtnPressed();
                            }
                        }
                    }
                });
        // Tämä viimeiseksi
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void numberPickerChange(){
        //Log.d(TAG,"value changed");
    }
    public void startBtnPressed(){
        numPicker.setEnabled(false);
        timerOn = true;
        textTimer.setVisibility(View.VISIBLE);
        starBtn.setText(getResources().getString(R.string.timer_start));
        Log.d(TAG,"start btn pressed, time start: " + numPicker.getValue());
        timer = new CountDownTimer(numPicker.getValue() * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTick(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                timerFin();
            }
        }.start();
    }
    public void stopBtnPressed(){
        Log.d(TAG,"stop btn pressed");
        if(timerOn) {
            timer.cancel();
        }
        numPicker.setValue(0);
        numPicker.setEnabled(true);
        timerOn = false;
        starBtn.setText(getResources().getString(R.string.timer_start));
        textTimer.setVisibility(View.INVISIBLE);
        materialButtonToggleGroup.clearChecked();
    }
    public void pauseBtnPressed(){
        Log.d(TAG,"pause btn pressed");
        if(timerOn) {
            timer.cancel();
            starBtn.setText(getResources().getString(R.string.timer_continue));
        }
    }
    public void timerTick(long millisUntilFinished){
        //Log.d(TAG, "Timer tick");
        //numPicker.setValue(numPicker.getValue()-1);
        numPicker.setValue((int)millisUntilFinished/1000);
        textTimer.setText(String.valueOf((int)millisUntilFinished/1000) + " s");
    }
    public void timerFin(){
        Log.d(TAG, "Timer Fin");
        Uri currentAlarmUri = RingtoneManager.getActualDefaultRingtoneUri(getActivity()
                .getApplicationContext(), RingtoneManager.TYPE_ALARM);
        Ringtone currentAlarm = RingtoneManager.getRingtone(getActivity(), currentAlarmUri);
        currentAlarm.play();
        numPicker.setEnabled(true);
        materialButtonToggleGroup.clearChecked();
        timerOn = false;
        Animation animation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.spin_animation);
        textTimer.setText(getResources().getString(R.string.timer_ending));
        textTimer.startAnimation(animation);
        textTimer.postDelayed(new Runnable() {
            @Override
            public void run() {
                textTimer.setVisibility(View.INVISIBLE);
                currentAlarm.stop();
            }
        }, 1500);
    }
}