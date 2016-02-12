package com.example.radtech.spectaruler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;


import android.view.View;
import android.widget.ImageButton;


public class Menu extends Activity implements View.OnClickListener {

    ImageButton ruler,height,steps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ruler=(ImageButton)findViewById(R.id.ibRuler);
        height=(ImageButton)findViewById(R.id.ibHeight);
        steps=(ImageButton)findViewById(R.id.ibSteps);
        ruler.setBackgroundColor(Color.TRANSPARENT);
        height.setBackgroundColor(Color.TRANSPARENT);
        steps.setBackgroundColor(Color.TRANSPARENT);
        ruler.setOnClickListener(this);
        height.setOnClickListener(this);
        steps.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Class ourClass=null;
        String cheese=null;
        switch (view.getId())
        {
            case R.id.ibRuler:
                cheese="Ruler";
                break;
            case R.id.ibHeight:
                cheese="Height";
                break;
            case R.id.ibSteps:
                cheese="Steps";
                break;
        }
        try
        {
            ourClass=Class.forName("com.example.radtech.spectaruler."+cheese);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Intent i=new Intent(Menu.this,ourClass);
        startActivity(i);
    }
}
