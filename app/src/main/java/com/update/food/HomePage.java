package com.update.food;

import android.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.AnimationDrawable;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;
import com.update.food.Categories.CafeFoodPage;
import com.update.food.Categories.ChineseFoodPage;
import com.update.food.Categories.JapaneseFoodPage;
import com.update.food.Categories.KoreanFoodPage;
import com.update.food.Categories.LocalFoodPage;
import com.update.food.Categories.VegetarianFoodPage;
import com.update.food.Categories.WesternFoodPage;
import com.update.food.List.ProfileList;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.update.food.Network.*;
import com.update.food.Network.NetworkUtil;

/**
 * Created by Windows on 14/9/2016.
 */



public class HomePage extends AppCompatActivity
{
    private AdView mAdView;
    public static Toolbar toolbarMain;
    public static DrawerLayout drawerLayoutMain;
    public static ListView listview;
    public static ImageButton imgbtn;
    public static TextView role_title;
    public ImageView voicemicrophone;
    private ImageAdapter adapter;
    public Activity activity;
    public static final int REQUEST_LOCATION = 2;
    public static final int REQUEST_CODE_SPEECH_INPUT =1000;
     Integer[] imageIDs = {
            R.drawable.chinese_food,
            R.drawable.western_food,
            R.drawable.cafe,
            R.drawable.japanese_food,
            R.drawable.korean_food,
            R.drawable.local_food,
            R.drawable.vegetarian,
    };
    Integer[] foodIDs={
            R.drawable.chinese_food_banner1,
            R.drawable.western_food_banner1,
            R.drawable.cafe_food_banner1,
            R.drawable.japanese_food_banner1,
            R.drawable.korean_food_banner1,
            R.drawable.local_food_banner1,
            R.drawable.vegetarian_food_banner1
    };
     Integer[] foodTitles={
            R.drawable.chinese_title,
            R.drawable.western_title,
            R.drawable.cafe_title,
            R.drawable.japanese_title,
            R.drawable.korean_title,
            R.drawable.local_title,
            R.drawable.vegetarian_title
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        activity=this;
        overridePendingTransition(R.drawable.animation_left_in, R.drawable.animation_left_out);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-6849509841379714~2311660788");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
              //  .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
               // .addTestDevice("C04B1BFFB0774708339BC273F8A43708")
                .build();
        mAdView.loadAd(adRequest);

        checkpermission();
        imgbtn= (ImageButton)findViewById(R.id.btn_avatar);

        Picasso.with(this)
                .load("https://graph.facebook.com/"+ProfileList.getbitmap+"/picture?type=large")
                .placeholder(R.drawable.placeholder)
                .resize(400,400)
                .into(imgbtn);




        role_title = (TextView)findViewById(R.id.role_title);
        role_title.setText(ProfileList.getprofilename);

        adapter = new ImageAdapter(this);
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);






        toolbarMain = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbarMain);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        drawerLayoutMain = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        drawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerLayoutMain.setDrawerListener(new DrawerLayout.DrawerListener()
        {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset)
            {

            }
            @Override
            public void onDrawerOpened(View drawerView)
            {
                drawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
            @Override
            public void onDrawerClosed(View drawerView)
            {
                drawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }
            @Override
            public void onDrawerStateChanged(int newState)
            {

            }
        });
    }




    public void share_button(View view)
    {
        String shareBody = "https://play.google.com/store/apps/details?id=com.update.food&hl=en";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Sharing Tasty Johor");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent,"Share Using"));
    }

    public void imgBtnThreeClicked(View view)
    {
        if (drawerLayoutMain != null)
        {
            drawerLayoutMain.openDrawer(GravityCompat.START);
        }
    }
    public void NavigationListClicked(View view)
    {
        SharedPreferences.Editor editor =getSharedPreferences("logged", 0).edit();
        editor.clear();
        editor.commit();

        Intent lol = new Intent(this,MainActivity.class);
        startActivity(lol);
        finish();
    }

    static class ViewHolder {
        ImageView img1;
        ImageView img2;
        RelativeLayout choose;

    }

    public class ImageAdapter extends BaseAdapter  implements View.OnClickListener
    {
        private Context context;
        public  Boolean isScrolling = true;
        public ImageAdapter(Context c)
        {
            context = c;
        }

        public int getCount() {
            return imageIDs.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }


        public View getView(final int position, View convertView, ViewGroup parent)
        {
            View rowView;
            final ViewHolder viewHolder;
            ImageView imageView;

            if(convertView==null)
            {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView= inflater.inflate(R.layout.home_page_listview_linear, null, false);

                viewHolder= new ViewHolder();
                viewHolder.choose = (RelativeLayout) convertView.findViewById(R.id.choose);
                viewHolder.img1 = (ImageView) convertView.findViewById(R.id.imageview1);
                viewHolder.img2 = (ImageView) convertView.findViewById(R.id.imageviewfront);
                rowView=convertView;

                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder) convertView.getTag();
                rowView=convertView;
            }
            viewHolder.img1.setImageResource(foodIDs[position]);
            viewHolder.img2.setImageResource(foodTitles[position]);
          //  viewHolder.img1.setImageResource(R.drawable.chinese_food_banner);
            viewHolder.choose.setOnClickListener(this);
            viewHolder.choose.setTag(position);


            return rowView;
        }

        @Override
        public void onClick(View view) {
            int position=(Integer) view.getTag();
            System.out.println(position);
            if (position == 0) {
                listview.setItemsCanFocus(false);
                Intent lol = new Intent(getApplicationContext(), ChineseFoodPage.class);
                startActivity(lol);
            } else if (position == 1) {
                Intent lol = new Intent(getApplicationContext(), WesternFoodPage.class);
                startActivity(lol);
            } else if (position == 2) {
                Intent lol = new Intent(getApplicationContext(), CafeFoodPage.class);
                startActivity(lol);
            } else if (position == 3) {
                Intent lol = new Intent(getApplicationContext(), JapaneseFoodPage.class);
                startActivity(lol);
            } else if (position == 4) {
                Intent lol = new Intent(getApplicationContext(), KoreanFoodPage.class);
                startActivity(lol);
            } else if (position == 5) {
                Intent lol = new Intent(getApplicationContext(), LocalFoodPage.class);
                startActivity(lol);
            } else if (position == 6) {
                listview.setItemsCanFocus(false);
                Intent lol = new Intent(getApplicationContext(), VegetarianFoodPage.class);
                startActivity(lol);
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        // optional depending on your needs
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    public void checkpermission()
    {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION )
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now

            if (ActivityCompat.shouldShowRequestPermissionRationale( this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Display UI and wait for user interaction
            System.out.println("1");

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Change Permissions in Settings");
                alertDialogBuilder
                        .setMessage("" +
                                "\nUnable open Tasty Johor. \n"+"Go to SETTINGS > Permission \n"+"" +
                                "\n Allow the Location Permission")
                        .setCancelable(false)
                        .setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, 101);


                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

              //  CheckProvider(activity);
            }
        } else
        {
                CheckProvider(activity);
        }

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (requestCode == REQUEST_LOCATION)
        {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {

                System.out.println("permission granted");
                CheckProvider(activity);
            }
            else
            {
                System.out.println("permission not granted");
               // checkpermission();
               // CheckProvider(activity);
            }
        }
    }


    public void CheckProvider(final Context context)
    {
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}


        if(!NetworkUtil.isNetworkAvailable(context))
        {
            if(!network_enabled)
            {
                new AlertDialog.Builder(context)
                        .setMessage("Network Service Disable , Please enable it")
                        .setPositiveButton("Open Network Setting", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                        context.startActivity(new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS));
                                    }
                                }
                        )
                        .setCancelable(false)
                        .show();
            }
        }

        if(!gps_enabled ) {
            // notify user
            new AlertDialog.Builder(context)
                    .setMessage("Location Service Disable , Please enable it")
                    .setPositiveButton("Open Location Setting", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            }
                    )
                    .setCancelable(false)
                    .show();
        }
    }

    @Override
    public void onPause() {
       // listview.setItemsCanFocus(false);
        System.out.println("PAUSE");
    //    adapter.notifyDataSetChanged();
    //    listview.invalidateViews();
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
       // listview.setItemsCanFocus(false);
        System.out.println("RESUME");
        checkpermission();
      //  adapter.notifyDataSetChanged();
      //  listview.invalidateViews();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}



