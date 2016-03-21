package com.update.app;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity  {
    Toolbar toolbarMain;
    DrawerLayout drawerLayoutMain;
    ImageButton imgBtnSearch;
    ImageView imgViewLogo;
    EditText editTextSeach;
    LinearLayout bottomlinear;
    ImageButton btm_camera_button;
    Button redeem;
    ListView listView,userlistview;
    Button btn_me,btn_collection,btn_saved,btn_home,btn_nearby,btn_redeem,btn_products,btn_aboutus,btn_terms,btn_pdpa,btn_rate,btn_share;

    private boolean isSearchOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button redeem = (Button)findViewById(R.id.redeem);
       // redeem.setVisibility(View.INVISIBLE);

        ProductList productList = new ProductList();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_main, productList);
        transaction.commit();


        toolbarMain = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbarMain);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
    //    actionBar.setCustomView(R.layout.search_bar);

        editTextSeach = (EditText) findViewById(R.id.et_search);
        imgViewLogo = (ImageView) findViewById(R.id.img_view_logo);
        imgBtnSearch = (ImageButton) findViewById(R.id.img_btn_search);
        imgViewLogo.setVisibility(View.VISIBLE);

        bottomlinear = (LinearLayout) findViewById(R.id.btm_layout);
        btm_camera_button = (ImageButton)findViewById(R.id.btn_footer_camera);
        drawerLayoutMain = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        drawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerLayoutMain.setDrawerListener(new DrawerLayout.DrawerListener() {
            //  @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                drawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                drawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }

            //     @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbarMain, R.string.app_name, R.string.app_name);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_main);
        //navigationView.setNavigationItemSelectedListener(this);
    }
    public void PopOut()
    {
        new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK)
                .setMessage("Coming Soon")
                .setCancelable(false)
                .setPositiveButton("",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MainActivity.this.finish();
                            }
                        }).setNegativeButton("Close", null).show();
    }
    public void TopButtonClicked(View view)
    {
        switch(view.getId())
        {
            case R.id.btn_header_colors:

                break;
            case R.id.btn_header_cosmetic:

                break;
            case R.id.btn_header_fragrance:

                break;
            case R.id.btn_header_hair:

                break;
            case R.id.btn_header_more:

                break;

            default:
                break;

        }
    }

    public void FooterButtonClicked(View view)
    {
        switch(view.getId())
        {
            case R.id.btn_footer_search:

                break;
            case R.id.btn_footer_saved:

                break;
            case R.id.btn_footer_camera:

                break;
            case R.id.btn_footer_line3:

                break;
            case R.id.btn_footer_user:

                break;

            default:
                break;

        }

    }
    public void NavigationListClicked(View view)
        {
            bottomlinear.setVisibility(View.VISIBLE);
            btm_camera_button.setVisibility(View.VISIBLE);

        switch(view.getId())
        {
            case R.id.btn_me:
                drawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                FragmentUser fragmentuser = new FragmentUser();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_main, fragmentuser);
               // transaction.addToBackStack(null);
                transaction.commit();

                break;
            case R.id.btn_collection:

                break;
            case R.id.btn_saved:
                drawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                FragmentSaved fragmentsaved = new FragmentSaved();
                FragmentTransaction transaction5 = getFragmentManager().beginTransaction();
                transaction5.replace(R.id.fragment_main, fragmentsaved);
                // transaction.addToBackStack(null);
                transaction5.commit();
                break;
            case R.id.btn_home:
                drawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                ProductList productList = new ProductList();
                FragmentTransaction transaction3 = getFragmentManager().beginTransaction();
                transaction3.replace(R.id.fragment_main, productList);
                // transaction.addToBackStack(null);
            //    transaction3.popBackStack("lool", transaction3.POP_BACK_STACK_INCLUSIVE);
                transaction3.commit();
                break;
            case R.id.btn_nearby:

                drawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                FragmentNearby fragmentnearby = new FragmentNearby();
                FragmentTransaction transaction6 = getFragmentManager().beginTransaction();
                transaction6.replace(R.id.fragment_main, fragmentnearby);
                // transaction.addToBackStack(null);
                transaction6.commit();

                break;
            case R.id.btn_redeem:
                drawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                FragmentRedeemProduct fragmentredeemproduct = new FragmentRedeemProduct();
                FragmentTransaction transaction4 = getFragmentManager().beginTransaction();
                transaction4.replace(R.id.fragment_main, fragmentredeemproduct);
                // transaction.addToBackStack(null);
                transaction4.commit();
                break;
            case R.id.btn_products:

                bottomlinear.setVisibility(View.INVISIBLE);
                btm_camera_button.setVisibility(View.INVISIBLE);

                drawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                GridViewListFragment fragmentproduct = new GridViewListFragment();
                FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                transaction2.replace(R.id.fragment_main, fragmentproduct);
                transaction2.commit();

                break;
            case R.id.btn_aboutus:

                break;
            case R.id.btn_terms:

                break;
            case R.id.btn_pdpa:

                break;
            case R.id.btn_rate:

                break;
            case R.id.btn_share:

                break;

            default:
                break;
        }

    }



   // public void imgBtnSearchClicked(View view)
   // {
   //     PopOut();
   // }
    public void imgBtnCartClicked(View view)
    {
        PopOut();
    }

    public void imgBtnThreeClicked(View view) {
        if (drawerLayoutMain != null) {
            drawerLayoutMain.openDrawer(GravityCompat.START);
        }
    }

}