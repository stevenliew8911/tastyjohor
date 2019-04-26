package com.update.food.TimeDialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.update.food.R;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Windows on 14/7/2018.
 */

public class TimeDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    public String shopdescription;
    public String dayOfTheWeek;
    TextView monday;
    TextView tuesday;
    TextView wednesday;
    TextView thursday;
    TextView friday;
    TextView saturday;
    TextView sunday;

    public TimeDialog(Activity a,String shopdescription,String dayOfTheWeek) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.shopdescription=shopdescription;
        this.dayOfTheWeek=dayOfTheWeek;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.time_dialog);
        monday=(TextView)findViewById(R.id.monday);
        tuesday=(TextView)findViewById(R.id.tuesday);
        wednesday=(TextView)findViewById(R.id.wednesday);
        thursday=(TextView)findViewById(R.id.thursday);
        friday=(TextView)findViewById(R.id.friday);
        saturday=(TextView)findViewById(R.id.saturday);
        sunday=(TextView)findViewById(R.id.sunday);




        timing();




    }
    public void timing()
    {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String str = sdf.format(new Date());
        System.out.println(str);



        String currentDateandTime = sdf.format(new Date());
        String []temp=shopdescription.split("\n");
        String []temp2=null;

        SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");

        SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
        // one=String.valueOf(date24Format.format(date12Format.parse(temp[i])));
        for (int i = 0; i < temp.length; i++) {

            if(temp[i].contains(dayOfTheWeek))
            {
                if(i==0)
                {
                    monday.setTypeface(monday.getTypeface(), Typeface.BOLD);
                    monday.setText(temp[i]);
                }
                else if (i==1)
                {
                    tuesday.setTypeface(tuesday.getTypeface(), Typeface.BOLD);
                    tuesday.setText(temp[i]);
                }
                else if(i==2)
                {
                    wednesday.setTypeface(wednesday.getTypeface(), Typeface.BOLD);
                    wednesday.setText(temp[i]);
                }
                else if(i==3)
                {
                    thursday.setTypeface(thursday.getTypeface(), Typeface.BOLD);
                    thursday.setText(temp[i]);
                }
                else if(i==4)
                {
                    friday.setTypeface(friday.getTypeface(), Typeface.BOLD);
                    friday.setText(temp[i]);
                }
                else if(i==5)
                {
                    saturday.setTypeface(saturday.getTypeface(),Typeface.BOLD);
                    saturday.setText(temp[i]);
                }
                else if(i==6)
                {
                    sunday.setTypeface(sunday.getTypeface(),Typeface.BOLD);
                    sunday.setText(temp[i]);
                }

            }
            else
            {
                if(i==0)
                {
                   // monday.setTypeface(monday.getTypeface(), Typeface.BOLD);
                    monday.setText(temp[i]);
                }
                else if (i==1)
                {
                  //  tuesday.setTypeface(tuesday.getTypeface(), Typeface.BOLD);
                    tuesday.setText(temp[i]);
                }
                else if(i==2)
                {
                   // wednesday.setTypeface(wednesday.getTypeface(), Typeface.BOLD);
                    wednesday.setText(temp[i]);
                }
                else if(i==3)
                {
                   // thursday.setTypeface(thursday.getTypeface(), Typeface.BOLD);
                    thursday.setText(temp[i]);
                }
                else if(i==4)
                {
                  //  friday.setTypeface(friday.getTypeface(), Typeface.BOLD);
                    friday.setText(temp[i]);
                }
                else if(i==5)
                {
                  //  saturday.setTypeface(saturday.getTypeface(),Typeface.BOLD);
                    saturday.setText(temp[i]);
                }
                else if(i==6)
                {
                  //  sunday.setTypeface(sunday.getTypeface(),Typeface.BOLD);
                    sunday.setText(temp[i]);
                }

            }

        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }
        dismiss();
    }
}