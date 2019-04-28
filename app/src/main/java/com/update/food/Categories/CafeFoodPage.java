package com.update.food.Categories;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;


import com.update.food.CafeFoodPageDetail.CafeFoodDetailPage;
import com.update.food.CafeFoodPageDistance.CafeFoodPageBelow10km;
import com.update.food.CafeFoodPageDistance.CafeFoodPageBelow15km;
import com.update.food.CafeFoodPageDistance.CafeFoodPageBelow20km;
import com.update.food.CafeFoodPageDistance.CafeFoodPageBelow5km;
import com.update.food.CafeTempVariable.CafeTempVariable;
import com.update.food.GetFunction.GetCafeShopListTask;
import com.update.food.List.ShopList;
import com.update.food.Network.CheckRequirement;
import com.update.food.R;
import com.update.food.SearchFunction.SearchableAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Windows on 19/9/2016.
 */
public class CafeFoodPage extends AppCompatActivity {
    Toolbar toolbarMain;
   public static  ListView list;
    SearchView search_btn;
    SwipeRefreshLayout mSwipeRefreshLayout;
    public static ArrayList<ShopList> shopdata;
    public static SearchableAdapter adapter,below10kmadapter;
    public static Double currentlatitude;
    public static Double currentlongtitude;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    boolean flag_loading = false;
    Timer timer;
    LocationManager locationManager;

    public static final int REQUEST_LOCATION = 2;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cafe_food_homepage);
        overridePendingTransition(R.drawable.animation_left_in, R.drawable.animation_left_out);

        list = (ListView) findViewById(R.id.listview);
        initialize();

        mSwipeRefreshLayout= (SwipeRefreshLayout)findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
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
                    adapter.getFilter().filter("");

                } else {
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

        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if(i == 0 && listIsAtTop()){
                    mSwipeRefreshLayout.setEnabled(true);
                }else{
                    mSwipeRefreshLayout.setEnabled(false);
                }
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                currentlatitude = 0.00;
                currentlongtitude = 0.00;
                clearshopdetail();
                shopdata.clear();
                list.invalidateViews();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        //fetchshopdata();
                        initialize();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);



            }
        });

        toolbarMain = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbarMain);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


    }
    private boolean listIsAtTop()   {
        if(list.getChildCount() == 0) return true;
        return list.getChildAt(0).getTop() == 0;
    }




    public void imgLocationClicked(View view) {

        final CharSequence[] items = {"0 ~ 5KM ","5 ~ 10KM ","10 ~ 15KM ","15 ~ 20KM "};
        final boolean selected[] = new boolean[]{true, false, false, false};
        final ArrayList seletedItems=new ArrayList();

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Select The Distance")
                .setMultiChoiceItems(items, selected, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {

                        for (int i = 0; i < selected.length; i++) {
                            if (i == indexSelected) {
                                selected[i]=true;
                                ((AlertDialog) dialog).getListView().setItemChecked(i, true);
                            }
                            else {
                                selected[i]=false;
                                ((AlertDialog) dialog).getListView().setItemChecked(i, false);
                            }
                        }

                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                       for(int i=0; i<selected.length; i++)
                       {
                           if(selected[i]==true)
                           {
                               if(i==0)
                               {
                                   Intent intent = new Intent(getApplicationContext(), CafeFoodPageBelow5km.class);
                                   startActivity(intent);
                               }
                               if(i==1)
                               {
                                   Intent intent = new Intent(getApplicationContext(), CafeFoodPageBelow10km.class);
                                   startActivity(intent);

                               }
                               if(i==2)
                               {
                                   Intent intent = new Intent(getApplicationContext(), CafeFoodPageBelow15km.class);
                                   startActivity(intent);
                               }
                               if(i==3)
                               {
                                   Intent intent = new Intent(getApplicationContext(), CafeFoodPageBelow20km.class);
                                   startActivity(intent);

                               }

                           }
                       }

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).create();
        dialog.show();
    }

    public void imgBtnBackClicked(View view) {
        currentlatitude = 0.00;
        currentlongtitude = 0.00;
        clearshopdetail();
        shopdata.clear();

        finish();
        overridePendingTransition(R.drawable.animation_right_in, R.drawable.animation_right_out);
    }

    @Override
    public void onBackPressed() {
        currentlatitude = 0.00;
        currentlongtitude = 0.00;
        shopdata.clear();
        clearshopdetail();

        finish();
        overridePendingTransition(R.drawable.animation_right_in, R.drawable.animation_right_out);
    }

    public void clearshopdetail()
    {
        SearchableAdapter.itemlist.clear();
        SearchableAdapter.Locationdistance.clear();
        SearchableAdapter.Locationtime.clear();
        GetCafeShopListTask.listMockData.clear();


       SearchableAdapter.ShopListbelow5km.clear();
        SearchableAdapter.ShopListbelow10km.clear();
        SearchableAdapter.ShopListbelow15km.clear();
        SearchableAdapter.ShopListbelow20km.clear();
        SearchableAdapter.Locationdistancebelow5km.clear();
        SearchableAdapter.Locationdistancebelow10km.clear();
        SearchableAdapter.Locationdistancebelow15km.clear();
        SearchableAdapter.Locationdistancebelow20km.clear();
        SearchableAdapter.Locationtimebelow5km.clear();
        SearchableAdapter.Locationtimebelow10km.clear();
        SearchableAdapter.Locationtimebelow15km.clear();
        SearchableAdapter.Locationtimebelow20km.clear();

    }
    public void fetchshopdata() {

        new GetCafeShopListTask(this, list).execute();

    }

    public void initialize() {
         locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);


        gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (gps_enabled && network_enabled )
        {

            System.out.println("GPS && NETwork");
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
                    locationListenerNetwork);


        }
      else if (network_enabled)
        {

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
                        locationListenerNetwork);
             System.out.println("Network");


        }
        else if( gps_enabled)
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                    locationListenerGps);

            System.out.println("GPS");
        }


        timer=new Timer();
        timer.schedule(new GetLastLocation(), 0);

        fetchshopdata();


    }

    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer.cancel();
            currentlatitude=location.getLatitude();
            currentlongtitude=location.getLongitude();
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerNetwork);

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

                }
                else
                {
                    currentlatitude=net_loc.getLatitude();
                    currentlongtitude=net_loc.getLongitude();

                }

            }

            if(gps_loc!=null){
                {
                  currentlatitude= gps_loc.getLatitude();
                  currentlongtitude= gps_loc.getLongitude();

                }

            }
            if(net_loc!=null){
                {
                   currentlatitude= net_loc.getLatitude();
                   currentlongtitude= net_loc.getLongitude();


                }
            }

        }
    }


}
