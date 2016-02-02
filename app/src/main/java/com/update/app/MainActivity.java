package com.update.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbarMain;
    DrawerLayout drawerLayoutMain;
    ImageButton imgBtnSearch;
    ImageView imgViewLogo;
    EditText editTextSeach;

    private boolean isSearchOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        imgBtnSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        toolbarMain = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbarMain);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(R.layout.search_bar);

        editTextSeach = (EditText) findViewById(R.id.et_search);
        imgViewLogo = (ImageView) findViewById(R.id.img_view_logo);
        imgBtnSearch = (ImageButton) findViewById(R.id.img_btn_search);
        imgViewLogo.setVisibility(View.VISIBLE);

        drawerLayoutMain = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        drawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerLayoutMain.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
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

            @Override
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

    public void imgBtnSearchClicked(View view) {
        Log.i("onClick", "onClick: " + isSearchOpened);

        if (isSearchOpened) { //test if the search is open

            editTextSeach.setVisibility(View.INVISIBLE);
            imgViewLogo.setVisibility(View.VISIBLE);

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editTextSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            imgBtnSearch.setImageResource(R.drawable.ic_action_search);

            isSearchOpened = false;
        } else {
            imgViewLogo.setVisibility(View.INVISIBLE);
            editTextSeach.setVisibility(View.VISIBLE);

            editTextSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editTextSeach, InputMethodManager.SHOW_IMPLICIT);

            //add the close icon
            imgBtnSearch.setImageResource(R.drawable.ic_action_cart);

            isSearchOpened = true;
        }
    }

    public void imgBtnThreeClicked(View view) {
        if (drawerLayoutMain != null) {
            drawerLayoutMain.openDrawer(GravityCompat.START);
        }
    }

    class CustomListAdapter extends ArrayAdapter<String> {

        Context context;
        String[] nameList;
        int[] imageList;

        public CustomListAdapter(Context context, String[] nameList, int[] imageList) {
            super(context, R.layout.list_view_nav_item, nameList);

            this.context = context;
            this.nameList = nameList;
            this.imageList = imageList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_nav_item, parent, false);
            }

            ImageView imgView = (ImageView) convertView.findViewById(R.id.list_view_nav_img_view);
            TextView txtView = (TextView) convertView.findViewById(R.id.list_view_nav_text_view);

            imgView.setImageResource(imageList[position]);
            txtView.setText(nameList[position]);

            return convertView;
        }
    }
}