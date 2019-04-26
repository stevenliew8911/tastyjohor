package com.update.food.WesternFoodPageDetail;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.update.food.Manifest;
import com.update.food.PostFunction.PostRatingHitsTask;
import com.update.food.R;
import com.update.food.VegetarianFoodPageDetail.VegetarianFoodDetailPage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Created by Windows on 22/9/2016.
 */
public class WesternFoodDetailPage extends AppCompatActivity implements OnMapReadyCallback {
    ImageView image, image1, image2, image3, image4, image5;
    MapFragment fm;
    TextView shopname,address,description;
    String ShopID;
    LatLng point;
    String ShopOwnerLatitude;
    String ShopOwnerLongtitude;
    String ShopOwnerAddress;
    String ShopOwnerDescription;
    Button navigation;
    String ShopName;
    String dayOfTheWeek;
    String ShopRatingHits;
    String ShopRatingMarks;
    LinearLayout line2;
    FloatingActionButton fab;
    RatingBar rating;
    public static final int REQUEST_LOCATION = 2;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.western_food_page_detail);

        line2 = (LinearLayout)findViewById(R.id.line2);
        image = (ImageView) findViewById(R.id.shop_image_banner);
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image4 = (ImageView) findViewById(R.id.image4);
        image5 = (ImageView) findViewById(R.id.image5);
        shopname = (TextView)findViewById(R.id.shop_title);
        address = (TextView) findViewById(R.id.address_western);
        description = (TextView) findViewById(R.id.description_western);
        navigation= (Button) findViewById(R.id.navigation);

        fab=(FloatingActionButton)findViewById(R.id.fab) ;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent searchAddress = new  Intent(Intent.ACTION_VIEW,Uri.parse("geo:0,0?q="+ShopOwnerAddress));
                startActivity(searchAddress);

            }
        });
        rating=(RatingBar)findViewById(R.id.rating);
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(final RatingBar ratingBar, float v, boolean b) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WesternFoodDetailPage.this);
                builder.setMessage(
                        "Confirm Your Rating Rate")
                        .setCancelable(false)
                        .setPositiveButton("ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        //to perform on ok
                                        UpdateRatingHits(ratingBar.getRating());
                                        //new PostRatingHitsTask(CafeFoodDetailPage.this).execute(ShopID);

                                    }
                                })
                        .setNegativeButton("cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {

                                        //dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();

                // Setting Dialog Title

            }
        });

        image.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View view)
            {
                enlargeimage(image);
            }});

        image1.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View view)
            {
                enlargeimage(image1);
            }});

        image2.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View view)
            {
                enlargeimage(image2);
            }});

        image3.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View view)
            {
                enlargeimage(image3);
            }});
        image4.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View view)
            {
                enlargeimage(image4);
            }});
        image5.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View view)
            {
                enlargeimage(image5);
            }});


        Intent intent = getIntent();
        ShopID = intent.getStringExtra("getShopID");
        ShopName = intent.getStringExtra("getShopName");
        String ShopOwnerName = intent.getStringExtra("getShopOwnerName");
        String ShopOwnerIc = intent.getStringExtra("getShopOwnerIc");
        String ShopOwnerHp = intent.getStringExtra("getShopOwnerHp");
        ShopOwnerAddress= intent.getStringExtra("getShopOwnerAddress");
        String ShopOwnerFAddress = intent.getStringExtra("getShopOwnerFAddress");
        ShopOwnerDescription= intent.getStringExtra("getShopOwnerDescription");
        ShopOwnerLatitude = intent.getStringExtra("getShopOwnerLatitude");
        ShopOwnerLongtitude = intent.getStringExtra("getShopOwnerLongtitude");
        String ShopOwnerBannerPic = intent.getStringExtra("getShopOwnerBannerPic");
        String ShopOwnerPic1 = intent.getStringExtra("getShopOwnerPic1");
        String ShopOwnerPic2 = intent.getStringExtra("getShopOwnerPic2");
        String ShopOwnerPic3 = intent.getStringExtra("getShopOwnerPic3");
        String ShopOwnerPic4 = intent.getStringExtra("getShopOwnerPic4");
        String ShopOwnerPic5 = intent.getStringExtra("getShopOwnerPic5");
        ShopRatingHits = intent.getStringExtra("getShopRatingHits");
        ShopRatingMarks = intent.getStringExtra("getShopRatingMarks");


        point= new LatLng(Double.parseDouble(ShopOwnerLatitude),Double.parseDouble(ShopOwnerLongtitude));

        setupMap();



        final ScrollView mainScrollView = (ScrollView)findViewById(R.id.main_scrollview);
        ImageView transparentImageView = (ImageView) findViewById(R.id.transparent_image);
        transparentImageView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        mainScrollView.requestDisallowInterceptTouchEvent(true);
                        return false;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        mainScrollView.requestDisallowInterceptTouchEvent(false);
                        return true;

                    case MotionEvent.ACTION_MOVE:

                        mainScrollView.requestDisallowInterceptTouchEvent(true);
                        return false;

                    default:
                        return true;
                }
            }
        });


        Picasso.with(this)
                .load("" + ShopOwnerBannerPic)
                .placeholder(R.drawable.placeholder)   // optional
                        // .error(R.drawable.error)      // optional
                .resize(800,400)                        // optional
                .into(image);

        Picasso.with(this)
                .load("" + ShopOwnerPic1)
                .placeholder(R.drawable.placeholder)   // optional
                        // .error(R.drawable.error)      // optional
                .resize(300,300)                        // optional
                .into(image1);

        Picasso.with(this)
                .load("" + ShopOwnerPic2)
                .placeholder(R.drawable.placeholder)   // optional
                        // .error(R.drawable.error)
                        // optional
                .resize(300, 300)
                        //.resize(400,400)                        // optional
                .into(image2);

        Picasso.with(this)
                .load("" + ShopOwnerPic3)
                .placeholder(R.drawable.placeholder)   // optional
                        // .error(R.drawable.error)      // optional
                        //.resize(400,400)
                        // optional
                .resize(300, 300)
                .into(image3);
        Picasso.with(this)
                .load("" + ShopOwnerPic4)
                .placeholder(R.drawable.placeholder)   // optional
                        // .error(R.drawable.error)      // optional
                        //.resize(400,400)                        // optional
                .resize(300, 300)
                .into(image4);

        Picasso.with(this)
                .load("" + ShopOwnerPic5)
                .placeholder(R.drawable.placeholder)   // optional
                        // .error(R.drawable.error)      // optional
                        //.resize(400,400)                        // optional
                .resize(300, 300)
                .into(image5);

        address.setText(ShopOwnerAddress);
        address.setTextSize(14);
        address.setTypeface(address.getTypeface(), Typeface.BOLD_ITALIC);

        timing();

        shopname.setText(ShopName);


        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Double.parseDouble(ShopOwnerLatitude), Double.parseDouble(ShopOwnerLongtitude));
                Intent searchAddress = new  Intent(Intent.ACTION_VIEW,Uri.parse("geo:0,0?q="+ShopOwnerAddress));
                startActivity(searchAddress);
                // Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                //startActivity(intent);
            }
        });


    }
    public void UpdateRatingHits(float marks)
    {
        int temp=Integer.parseInt(ShopRatingHits);
        temp++;
        int totalmarks=Integer.parseInt(ShopRatingMarks);
        int tempmarks=(int)(Math.round(marks));
        totalmarks=totalmarks+tempmarks;
        int result=totalmarks/temp;
        System.out.println(result);

        PostRatingHitsTask la= new PostRatingHitsTask(this);
        la.execute(ShopID,String.valueOf(temp),String.valueOf(totalmarks),"cafeshop");


    }
    public void timing()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String str = sdf.format(new Date());
        //  System.out.println(str);

        SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE");
        Date d = new Date();
        dayOfTheWeek = sdf2.format(d);
        //   System.out.println(dayOfTheWeek);

        String currentDateandTime = sdf.format(new Date());
        String []temp=ShopOwnerDescription.split("\n");
        String []temp2=null;
        String []temp3=null;

        SimpleDateFormat date12Format = new SimpleDateFormat("hh:mma");

        SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
        // one=String.valueOf(date24Format.format(date12Format.parse(temp[i])));
        for (int i = 0; i < temp.length; i++) {

            if(temp[i].contains(dayOfTheWeek))
            {
                temp2= temp[i].split("-");
                String endtime=temp2[1];
                temp3=temp2[0].split("\\s+");
                String starttime=temp3[1];
                // System.out.println("test"+temp3[1]);

                try {
                    if (date24Format.format(date12Format.parse(starttime)).compareTo(str) < 0)
                    {
//                      System.out.println("BOTH");
//                      description.setTextColor(Color.parseColor("#008000"));
//                      description.setText("OPEN NOW");
//                      System.out.println("getTestTime more than getCurrentTime ");
                        // Do your staff
                        if(date24Format.format(date12Format.parse(endtime)).compareTo(str) < 0)
                        {
                            if(date24Format.format(date12Format.parse(endtime)).equals("00:00"))
                            {
                                description.setTextColor(Color.parseColor("#008000"));
                                description.setText("OPEN NOW");

                            }
                            else
                            {
                                description.setTextColor(Color.parseColor("#FF0000"));
                                description.setText("CLOSED NOW");
                            }

//
                        }
                        else
                        {
                            description.setTextColor(Color.parseColor("#008000"));
                            description.setText("OPEN NOW");
                        }

                    }
                    else
                    {
                        if(date24Format.format(date12Format.parse(endtime)).compareTo(str) < 0)
                        {
                            description.setTextColor(Color.parseColor("#FF0000"));
                            description.setText("CLOSED NOW");
                        }
                        else
                        {
                            description.setTextColor(Color.parseColor("#FF0000"));
                            description.setText("CLOSED NOW");
//
                        }
//                      if(date24Format.format(date12Format.parse(endtime)).equals("00:00"))
//                      {
//                          description.setTextColor(Color.parseColor("#008000"));
//                          description.setText("OPEN NOW");
//                          System.out.println("getTestTime more than getCurrentTime ");
//                      }
//                      else
//                      {
//                          description.setTextColor(Color.parseColor("#FF0000"));
//                          description.setText("CLOSED NOW");
//
//                          System.out.println("getTestTime less than getCurrentTime ");
//                      }

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }

        }
//        SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
//
//        SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
//
//        try {
//            System.out.println(date24Format.format(date12Format.parse(temp[0])));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

    }

    public void enlargeimage(ImageView image)
    {
        LayoutInflater inflater = LayoutInflater.from(this);
        View imgEntryView = inflater.inflate(R.layout.fullscreen_dialog, null);
        final Dialog dialog=new Dialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen); //default fullscreen titlebar
        ImageView img = (ImageView) imgEntryView.findViewById(R.id.usericon_large);
        img.setMaxHeight(200);
        img.setMaxWidth(200);
        img.setImageDrawable(image.getDrawable());

        dialog.setContentView(imgEntryView);
        dialog.show();

        imgEntryView.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramView)
            {
                dialog.cancel();
            }
        });
    }
    public void setupMap()
    {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            // Check Permissions Now
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION))
            {
                // Display UI and wait for user interaction
            }
            else
            {
                ActivityCompat.requestPermissions(
                        this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            }
        }
        else
        {
            fm = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
            fm.getMapAsync(this);
            // Getting Current Location
            //   Location location = locationManager.getLastKnownLocation(provider);
        }


    }

    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == REQUEST_LOCATION)
        {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                fm = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
                fm.getMapAsync(this);
            }
            else
            {

            }
        }
    }

    private void moveToCurrentLocation(LatLng currentLocation, GoogleMap googleMap)
    {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);


    }


    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        googleMap.setMyLocationEnabled(true);
        // Double.parseDouble(ShopOwnerLatitude);
        googleMap.addMarker(new MarkerOptions().position(point));
        moveToCurrentLocation(point,googleMap);
        LocationManager locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();
        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);
    }
}
