package com.example.radtech.spectaruler;

/**
 * Created by Shantanu on 22-03-2015.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by Shantanu on 20-03-2015.
 */

public class Calculation extends Activity implements View.OnClickListener {

    TextView htDisp,distDisp;
    int c;
    Button htconf;
    EditText htInp;
    float read[];
    float h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculation);
        Bundle gotBasket=getIntent().getExtras();
        read=gotBasket.getFloatArray("key");
        htDisp=(TextView)findViewById(R.id.tvhtDisp);
        //bdDisp=(TextView)findViewById(R.id.tvbdDisp);
        distDisp=(TextView)findViewById(R.id.tvDistance);
        //areaDisp=(TextView)findViewById(R.id.tvArea);
        htInp=(EditText)findViewById(R.id.etHTINP);
        htconf=(Button)findViewById(R.id.bHTCONF);
        htconf.setOnClickListener(this);
        read[1]=-read[1];
        c=0;

    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.bHTCONF :
                if(c==0) {
                    h = Float.parseFloat(htInp.getText().toString());
                    htconf.setText("Display object height");
                    c++;
                }
                else if(c==1)
                {
                    float T2=(float)Math.tan(read[1]);
                    float T1=(float)Math.tan(read[0]);

                    float H=h-(h/7.5f);
                    float ans=H*(1+T2/T1);
                    htDisp.setText("The height is"+Float.toString(ans));
                    float dist=H/T1;
                   distDisp.setText("And the distance is"+Float.toString(dist));
                    htconf.setVisibility(View.INVISIBLE);
                   /* float breadth=dist*(1/tl+1/tr);
                    //bdDisp.setText("breadth"+Float.toString(breadth));
                    float area=ans*breadth;
                    areaDisp.setText("area"+Float.toString(area));*/
                }
                break;

        }
    }
}

