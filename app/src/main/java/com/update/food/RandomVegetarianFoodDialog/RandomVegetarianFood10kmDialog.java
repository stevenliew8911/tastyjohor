package com.update.food.RandomVegetarianFoodDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.update.food.List.ShopList;
import com.update.food.R;
import com.update.food.SearchFunction.SearchableAdapter;
import com.update.food.VegetarianFoodPageDetail.VegetarianFoodDetailPage;
import com.update.food.VegetarianFoodPageDistance.VegetarianFoodPageBelow10km;
import com.update.food.WesternFoodPageDetail.WesternFoodDetailPage;
import com.update.food.WesternFoodPageDistance.WesternFoodPageBelow10km;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Windows on 16/6/2018.
 */

public class RandomVegetarianFood10kmDialog extends Dialog {

    private String message;
    private String title;
    private String btYesText;
    private String btNoText;
    private int icon=0;
    Context context;
    ImageView spinwheel;
    RelativeLayout press_btn;
    RotateAnimation anim;
    Timer mTimer = new Timer();
    boolean press_indicator;

    ImageView img1;
    TextView text1,text2,text3;
    RelativeLayout relativewhole,relative1;

    public int randomVegetarianFoodID;



    public RandomVegetarianFood10kmDialog(Context context) {
        super(context);
        this.context = context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cafe_food_random);

        relativewhole=(RelativeLayout)findViewById(R.id.relativewhole);
        relativewhole.setVisibility(View.INVISIBLE);
        relative1=(RelativeLayout)findViewById(R.id.relative1);

        spinwheel=(ImageView)findViewById(R.id.spinwheel);
        img1=(ImageView)findViewById(R.id.img1);
        text1=(TextView)findViewById(R.id.text1);
        text2=(TextView)findViewById(R.id.text2);
        text3=(TextView)findViewById(R.id.text3);




        anim = new RotateAnimation(0.0f, 360.0f ,
                Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(1000);

        press_btn = (RelativeLayout)findViewById(R.id.press_btn);
        press_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(press_indicator==false)
                {

                    relativewhole.setVisibility(View.INVISIBLE);
                    spinwheel.startAnimation(anim);
                    setTimerTask();
                    randomShopID();

                    press_indicator=true;

                }
                else
                {

                }

            }
        });

        relative1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopList shoplist = SearchableAdapter.ShopListbelow10km.get(randomVegetarianFoodID);

                Intent intent = new Intent(context, VegetarianFoodDetailPage.class);
                intent.putExtra("getShopID", shoplist.getShopID());
                intent.putExtra("getShopName", shoplist.getShopName());
                intent.putExtra("getShopOwnerName", shoplist.getShopOwnerName());
                intent.putExtra("getShopOwnerIc", shoplist.getShopOwnerIc());
                intent.putExtra("getShopOwnerHp", shoplist.getShopOwnerHp());
                intent.putExtra("getShopOwnerFAddress", shoplist.getShopOwnerFAddress());
                intent.putExtra("getShopOwnerAddress", shoplist.getShopOwnerAddress());
                intent.putExtra("getShopOwnerDescription", shoplist.getShopOwnerDescription());
                intent.putExtra("getShopOwnerLatitude", shoplist.getShopOwnerLatitude());
                intent.putExtra("getShopOwnerLongtitude", shoplist.getShopOwnerLongtitude());
                intent.putExtra("getShopOwnerBannerPic", shoplist.getShopOwnerBannerPic());
                intent.putExtra("getShopOwnerPic1", shoplist.getShopOwnerPic1());
                intent.putExtra("getShopOwnerPic2", shoplist.getShopOwnerPic2());
                intent.putExtra("getShopOwnerPic3", shoplist.getShopOwnerPic3());
                intent.putExtra("getShopOwnerPic4", shoplist.getShopOwnerPic4());
                intent.putExtra("getShopOwnerPic5", shoplist.getShopOwnerPic5());
                context.startActivity(intent);
            }
        });


    }
    private void setTimerTask() {
        mTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                VegetarianFoodPageBelow10km.activity_10km.runOnUiThread(setImageRunnable);
            }



        }, 2000/* 表示1000毫秒之後，每隔1000毫秒執行一次 */);
    }

    final Runnable setImageRunnable = new Runnable() {
        public void run() {
            anim.cancel();
            press_indicator=false;
            relativewhole.setVisibility(View.VISIBLE);
        }
    };

    public void randomShopID()
    {
        Random rand = new Random();
        String random = SearchableAdapter.ShopListbelow10km.get(rand.nextInt(SearchableAdapter.ShopListbelow10km.size())).getShopID();
        for(int i=0; i<SearchableAdapter.ShopListbelow10km.size(); i++)
        {
            if(SearchableAdapter.ShopListbelow10km.get(i).getShopID()==random)
            {
                randomVegetarianFoodID=i;
                ShopList shoplist = SearchableAdapter.ShopListbelow10km.get(i);
                Picasso.with(context)
                        .load("" + shoplist.getShopLogo())
                        .placeholder(R.drawable.placeholder)   // optional
                        .resize(400, 400)                        // optional
                        .into(img1);
                text1.setText(shoplist.getShopName());
                text1.setTextSize(15);
                text1.setTypeface(text1.getTypeface(), Typeface.BOLD);
                text2.setText(shoplist.getShopOwnerFAddress());
                text2.setTextSize(12);
                text2.setTypeface(text2.getTypeface(), Typeface.BOLD_ITALIC);
                text3.setText(" Distance: " + SearchableAdapter.Locationdistancebelow10km.get(i)+" km" +"\n "+
                        "Duration: "+SearchableAdapter.Locationtimebelow10km.get(i)+ " mins");
                text3.setTextSize(12);
                text3.setTypeface(text3.getTypeface(), Typeface.BOLD_ITALIC);

            }

        }


    }

    public void imgBtnRandomSpin(View view)
    {
       System.out.println("TEST");
    }




}
