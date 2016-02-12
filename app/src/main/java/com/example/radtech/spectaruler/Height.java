package com.example.radtech.spectaruler;

/**
 * Created by Shantanu on 22-03-2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Shantanu on 20-03-2015.
 */
public class Height extends Activity implements SensorEventListener,View.OnClickListener {
    SensorManager sManager;
    //SensorEventListener sListener;
    int flag;
    float read[];
    private Camera mCamera;
    private CameraPreview mPreview;
    Button butCap;
    TextView pitc;
    ImageView iv;
    float Rot[]=null;
    float I[]=null;
    float accels[]=new float[3];
    float mags[]=new float[3];
    float[] values=new float[3];
    float azimuth,pitch,roll;
    SensorEventListener sListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        butCap=(Button)findViewById(R.id.button_capture);
        iv=(ImageView)findViewById(R.id.imageView2);
        sManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        flag=0;
        read=new float[2];
        //pitc=(TextView)findViewById(R.id.tvPITCH);
        butCap.setOnClickListener(this);
        azimuth=pitch=roll=0;


        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        preview.removeView(butCap);
        preview.addView(butCap);
        preview.removeView(iv);
        preview.addView(iv);
        /*preview.removeView(pitc);
        preview.addView(pitc);*/
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button_capture:
                if(flag==0)
                {
                /*sManager.registerListener(this,sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
                sManager.registerListener(this,sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),SensorManager.SENSOR_DELAY_NORMAL);*/
                    read[flag++]=pitch;
                    butCap.setText("TOP");
                    butCap.setBackgroundColor(Color.GREEN);
                    //flag++;
                }
                else if(flag==1)
                {
                    read[flag]=pitch;
                    butCap.setText("DONE");
                    butCap.setBackgroundColor(Color.GRAY);
                    flag++;
                }
                else if(flag==2)
                {
                         try

                    {
                        //butCap.setText("ONE");
                        Class ourClass = Class.forName("com.example.radtech.spectaruler."+"Calculation");
                        Bundle basket=new Bundle();
                        basket.putFloatArray("key",read);
                        Intent i = new Intent(Height.this, ourClass);
                        i.putExtras(basket);
                        startActivity(i);
                    }
                    catch (Exception e){}
                }
                break;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType())
        {
            case Sensor.TYPE_MAGNETIC_FIELD:
                mags=event.values.clone();
                break;
            case Sensor.TYPE_ACCELEROMETER:
                accels=event.values.clone();
                break;
        }
        if(mags!=null && accels!=null)
        {
            Rot=new  float[9];
            I=new float[9];
            SensorManager.getRotationMatrix(Rot,I,accels,mags);
            float[] outR=new float[9];
            SensorManager.remapCoordinateSystem(Rot,SensorManager.AXIS_X,SensorManager.AXIS_Z,outR);
            SensorManager.getOrientation(outR,values);
            azimuth=values[0];
            pitch=values[1];
            roll=values[2];
            // pitc.setText("PITCH "+Float.toString(pitch)+"\nAZIMUTH "+Float.toString(azimuth)+"\nRoll "+Float.toString(roll));
            mags=null;
            accels=null;
        }

    }


    public static Camera getCameraInstance()
    {
        Camera c=null;
        try {
            c = Camera.open();
        }
        catch (Exception e)
        {

        }
        return  c;
    }

    @Override
    protected void onResume() {
        super.onResume();
        sManager.registerListener(this,sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        sManager.registerListener(this,sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sManager.unregisterListener(this);
    }
}
