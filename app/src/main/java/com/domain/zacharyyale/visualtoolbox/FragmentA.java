package com.domain.zacharyyale.visualtoolbox;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragmentA extends Fragment implements SensorEventListener {

    public FragmentA() {
    }

    private RelativeLayout relativeLayout;
    private TextView lightModeLabel;
    private TextView lightSensorValueLabel;
    private Handler handler = new Handler();
    String initializeString = "Initializing light sensor...";
    String tactileMode = "Sensor Feedback: Vibration";
    //String auditoryMode = "Sensor Feedback: Auditory";

    //Lux Value
    float x;
    //Default Ambient Value
    long ambientValue = 16;

    long floor_delay = 1000;
    long ceiling_delay = 80;
    long vibrate = 100;
    long vibrate_delay;

    Vibrator sensorVibrator;
    Sensor lightSensor;
    SensorManager sensorManager;
    Context context;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Define sensor manager, and light/vibrator sensors
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        sensorVibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Define UI items
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        lightModeLabel = (TextView) view.findViewById(R.id.lightModeLabel);
        lightSensorValueLabel = (TextView) view.findViewById(R.id.lightSensorValueLabel);

        //Initialize light sensor label
        lightSensorValueLabel.setText(initializeString);
        //Initialize light sensor feedback mode label
        lightModeLabel.setText(tactileMode);

        //Timer Task
        //handler.postDelayed(runnable, 1000);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, container, false);

        context = this.getContext();

        return view;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            /* do what you need to do */
            long luxValue = (long)x;
            //Correction Ratio - Ceiling Delay/Most Likely Max Lux
            long ratio = 950/300;

            if(luxValue < ambientValue){
                vibrate = 100;
                vibrate_delay = floor_delay + (ratio* luxValue);
            }
            else{
                vibrate = 100;
                vibrate_delay = floor_delay - (ratio* luxValue);
            }

            if(vibrate_delay < ceiling_delay){
                long[] pattern = {0, vibrate, ceiling_delay};
                sensorVibrator.vibrate(pattern,1);
            }
            else{
                long[] pattern = {0, vibrate, vibrate_delay};
                sensorVibrator.vibrate(pattern,1);
            }

            Log.i("VisualToolbox MainActv.", "Lux: " + luxValue);
            /* and here comes the "trick" */
            handler.postDelayed(this, vibrate_delay);
        }
    };

    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, lightSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        handler.postDelayed(runnable, vibrate_delay);
    }

    public void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
        sensorVibrator.cancel();
        handler.removeCallbacks(runnable);
    }

    public void onPause() {
        super.onStop();
        sensorManager.unregisterListener(this);
        sensorVibrator.cancel();
        handler.removeCallbacks(runnable);
    }

    public void onDetach() {
        super.onStop();
        sensorManager.unregisterListener(this);
        sensorVibrator.cancel();
        handler.removeCallbacks(runnable);
    }

    public void onSensorChanged(SensorEvent event)
    {
        x = event.values[0];
        Log.i("VisualToolbox MainActv.", "Light value: " + x);
        lightSensorValueLabel.setText((int) x + " Lux");
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

}
