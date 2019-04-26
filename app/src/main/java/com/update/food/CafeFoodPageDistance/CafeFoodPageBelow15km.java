package com.update.food.CafeFoodPageDistance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.update.food.CafeFoodPageDetail.CafeFoodDetailPage;
import com.update.food.GetFunction.GetCafeShopListTask;
import com.update.food.List.ShopList;
import com.update.food.R;
import com.update.food.RandomCafeFoodDialog.RandomCafeFood15kmDialog;
import com.update.food.RandomCafeFoodDialog.RandomCafeFood5kmDialog;
import com.update.food.SearchFunction.SearchableAdapter;
import com.update.food.SearchFunction.SearchableDistanceAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

/**
 * Created by Windows on 19/9/2016.
 */
public class CafeFoodPageBelow15km extends AppCompatActivity {
    Toolbar toolbarMain;
    ListView list;
    SearchView search_btn;
    ImageView random_btn;


    ArrayList<ShopList> shopdata;
    SearchableDistanceAdapter adapter,below10kmadapter;
    Double currentlatitude;
    Double currentlongtitude;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    Timer timer;
    LocationManager locationManager;
    public static final int REQUEST_LOCATION = 2;
    public static Activity activity_15km;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cafe_food_distance);
        overridePendingTransition(R.drawable.animation_left_in, R.drawable.animation_left_out);
        //Fetch data first
        activity_15km=this;
     //   initialize();

        adapter = new SearchableDistanceAdapter(this, SearchableAdapter.ShopListbelow15km,SearchableAdapter.Locationdistancebelow15km,SearchableAdapter.Locationtimebelow15km, currentlatitude, currentlongtitude);


        list = (ListView) findViewById(R.id.listview);
        list.setAdapter(adapter);

        random_btn = (ImageView)findViewById(R.id.img_btn_random);

        search_btn = (SearchView) findViewById(R.id.insert_data);
        search_btn.clearFocus();
        search_btn.setIconifiedByDefault(false);
        search_btn.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")) {
                    //  listView.setVisibility(View.INVISIBLE);
                    //  relativelayout.setVisibility(View.VISIBLE);
                    adapter.getFilter().filter("");

                } else {
                    //  relativelayout.setVisibility(View.GONE);
                    //  listView.setVisibility(View.VISIBLE);
                    adapter.getFilter().filter(newText);
                }
                return false;
            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                ShopList shoplist = (ShopList) adapter.getItem(position);

                Intent intent = new Intent(getApplicationContext(), CafeFoodDetailPage.class);
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
                intent.putExtra("getShopRatingHits", shoplist.getShopRatingHits());
                intent.putExtra("getShopRatingMarks", shoplist.getShopRatingMarks());
                startActivity(intent);

            }
        });

        toolbarMain = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbarMain);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


    }


    public void imgBtnRandomClicked(View view)
    {
        if(SearchableAdapter.ShopListbelow15km.size()==0)
        {
            Toast.makeText(this,"No Ramdom Data",Toast.LENGTH_SHORT).show();
        }
        else
        {
            RandomCafeFood15kmDialog RCFD=new RandomCafeFood15kmDialog(this);
            RCFD.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            RCFD.setCanceledOnTouchOutside(false);
            RCFD.show();
        }
    }



    public void imgBtnBackClicked(View view) {
        currentlatitude = 0.00;
        currentlongtitude = 0.00;
        shopdata = null;
        list.invalidateViews();
        finish();
        overridePendingTransition(R.drawable.animation_right_in, R.drawable.animation_right_out);
    }

    @Override
    public void onBackPressed() {
        currentlatitude = 0.00;
        currentlongtitude = 0.00;
        shopdata = null;
        list.invalidateViews();
        finish();
        overridePendingTransition(R.drawable.animation_right_in, R.drawable.animation_right_out);
    }

    public void fetchshopdata() {
        try {
            shopdata = new GetCafeShopListTask(this, list).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
         locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);


        gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (gps_enabled)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                    locationListenerGps);
        if (network_enabled)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
                    locationListenerNetwork);
        timer=new Timer();
        timer.schedule(new GetLastLocation(), 0);
        //System.out.println(String.valueOf(currentlatitude));
      //  fetchshopdata();

    }

    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer.cancel();
            currentlatitude=location.getLatitude();
            currentlongtitude=location.getLongitude();
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerNetwork);
            Context context = getApplicationContext();

            Toast toast = Toast.makeText(context, "gps enabled "+location.getLatitude() + "\n" + location.getLongitude(),Toast.LENGTH_SHORT);
            toast.show();
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer.cancel();
            currentlatitude = location.getLatitude();
            currentlongtitude = location.getLongitude();
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerGps);

            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, "network enabled"+location.getLatitude() + "\n" + location.getLongitude(), duration);
            toast.show();
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    class GetLastLocation extends TimerTask {
        @Override
        public void run() {
            locationManager.removeUpdates(locationListenerGps);
            locationManager.removeUpdates(locationListenerNetwork);

            Location net_loc=null, gps_loc=null;
            if(gps_enabled)
                gps_loc=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(network_enabled)
                net_loc=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            //if there are both values use the latest one
            if(gps_loc!=null && net_loc!=null){
                if(gps_loc.getTime()>net_loc.getTime())
                {
                     currentlatitude=gps_loc.getLatitude();
                     currentlongtitude=gps_loc.getLongitude();
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "gps lastknown "+gps_loc.getLatitude() + "\n" + gps_loc.getLongitude(), duration);
                    toast.show();
                }
                else
                {
                    currentlatitude=net_loc.getLatitude();
                    currentlongtitude=net_loc.getLongitude();
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "network lastknown "+net_loc.getLatitude() + "\n" +  net_loc.getLongitude(), duration);
                    toast.show();

                }

            }

            if(gps_loc!=null){
                {
                  currentlatitude= gps_loc.getLatitude();
                  currentlongtitude= gps_loc.getLongitude();
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "gps lastknown "+gps_loc.getLatitude()+ "\n" + gps_loc.getLongitude(), duration);
                    toast.show();
                }

            }
            if(net_loc!=null){
                {
                   currentlatitude= net_loc.getLatitude();
                   currentlongtitude= net_loc.getLongitude();
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "network lastknown "+net_loc.getLatitude()+ "\n" + net_loc.getLongitude(), duration);
                    toast.show();

                }
            }
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, "no last know avilable", duration);
            toast.show();

        }
    }


}
