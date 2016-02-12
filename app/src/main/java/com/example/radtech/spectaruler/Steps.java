package com.example.radtech.spectaruler;

/**
 * Created by Shantanu on 22-03-2015.
 */

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;



/**
 * Created by Shantanu on 20-03-2015.
 */
public class Steps extends Activity implements SensorEventListener {
    SensorManager sManager;
    TextView disp;
    int n;
    float curPit;
    float prePit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stepping);
        try
        {
            Thread.sleep(3000);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        disp=(TextView)findViewById(R.id.tvhtDisp);
        curPit=prePit=0;
        n=0;
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    SensorEventListener sListener;

    //when this Activity starts
    @Override
    protected void onResume()
    {
        super.onResume();
    /*register the sensor listener to listen to the gyroscope sensor, use the
    callbacks defined in this class, and gather the sensor information as quick
    as possible*/

        sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),SensorManager.SENSOR_DELAY_NORMAL);
    }


    float Rot[]=null; //for gravity rotational data
    //don't use R because android uses that for other stuff
    float I[]=null; //for magnetic rotational data
    float accels[]=new float[3];
    float mags[]=new float[3];
    float[] values = new float[3];

    float azimuth;
    float pitch;
    float roll;

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        //below commented code - junk - unreliable is never populated
        //if sensor is unreliable, return void
        //if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
        //{
        //    return;
        //}
        switch (event.sensor.getType())
        {
            case Sensor.TYPE_MAGNETIC_FIELD:
                mags = event.values.clone();
                break;
            case Sensor.TYPE_ACCELEROMETER:
                accels = event.values.clone();
                break;
        }

        if (mags != null && accels != null) {
            Rot = new float[9];
            I= new float[9];
            SensorManager.getRotationMatrix(Rot, I, accels, mags);
            // Correct if screen is in Landscape

            float[] outR = new float[9];
            SensorManager.remapCoordinateSystem(Rot, SensorManager.AXIS_X,SensorManager.AXIS_Z, outR);
            SensorManager.getOrientation(outR, values);

            azimuth = values[0]* 57.2957795f; //looks like we don't need this one
            pitch =values[1] * 57.2957795f;
            roll = values[2] * 57.2957795f;
            curPit=pitch;

            if(curPit*prePit<0)
            {

                n++;
            }
            prePit=pitch;
            //disp.setText("PITCH "+Float.toString(pitch)+"\nAZIMUTH "+Float.toString(azimuth)+"\nRoll "+Float.toString(roll)+"\nNUM "+Integer.toString(n));
            disp.setText("You took "+Integer.toString(n)+" steps");

            mags = null; //retrigger the loop when things are repopulated
            accels = null; ////retrigger the loop when things are repopulated
        }


    }
}
