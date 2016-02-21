package com.update.app;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity  {
    Toolbar toolbarMain;
    DrawerLayout drawerLayoutMain;
    ImageButton imgBtnSearch;
    ImageView imgViewLogo;
    EditText editTextSeach;
    ListView listView,userlistview;
    Button btn_me,btn_collection,btn_saved,btn_home,btn_nearby,btn_redeem,btn_products,btn_aboutus,btn_terms,btn_pdpa,btn_rate,btn_share;

    private boolean isSearchOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NewsFragment newsFragment = new NewsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_main, newsFragment);
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

                break;
            case R.id.btn_home:
                drawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                NewsFragment newsFragment = new NewsFragment();
                FragmentTransaction transaction3 = getFragmentManager().beginTransaction();
                transaction3.replace(R.id.fragment_main, newsFragment);
                // transaction.addToBackStack(null);
                transaction3.commit();
                break;
            case R.id.btn_nearby:

                break;
            case R.id.btn_redeem:
                RedeemDialog alert = new RedeemDialog();
                alert.showDialog(this, "wow");
                break;
            case R.id.btn_products:

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