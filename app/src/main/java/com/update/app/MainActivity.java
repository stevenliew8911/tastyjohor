package com.update.app;

import android.app.LauncherActivity;
import android.content.Context;
import android.os.Bundle;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbarMain;
    DrawerLayout drawerLayoutMain;
    ImageButton imgBtnSearch;
    ImageView imgViewLogo;
    EditText editTextSeach;
    Button btnme;

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
        navlistitem();
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbarMain, R.string.app_name, R.string.app_name);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_main);
        //navigationView.setNavigationItemSelectedListener(this);

        ArrayList<ListItem> listData = getListData();

        final ListView listView = (ListView) findViewById(R.id.content_list);
        listView.setAdapter(new ProductListAdapter(this, listData));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                ListItem newsData = (ListItem) listView.getItemAtPosition(position);

            }
        });

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

            // isSearchOpened = false;
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



    class ProductListAdapter extends BaseAdapter {
        private ArrayList<ListItem> listData;
        private LayoutInflater layoutInflater;
        TextView TitleView;
        TextView SubTitleView ;
        TextView DateView ;
        ImageView ImageUrlView,IconUrlView;

        public ProductListAdapter(Context context, ArrayList listData) {
            this.listData = listData;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Object getItem(int position) {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.list_view_content_item, null);

                TitleView = (TextView) convertView.findViewById(R.id.title);
                SubTitleView = (TextView) convertView.findViewById(R.id.subtitle);
                DateView = (TextView) convertView.findViewById(R.id.date);
                ImageUrlView = (ImageView) convertView.findViewById(R.id.bgImage);
                IconUrlView = (ImageView) convertView.findViewById(R.id.logoImage);

            }
            ListItem newsItem =  listData.get(position);
            TitleView.setText(newsItem.getTitle());
            TitleView.bringToFront();
            SubTitleView.setText(newsItem.getSubTitle());
            SubTitleView.bringToFront();
            DateView.setText(newsItem.getDate());
            DateView.bringToFront();
            ImageUrlView.setBackgroundResource(R.drawable.taeyeon);
            IconUrlView.setBackgroundResource(R.drawable.logolist);
           // imageView.setImageResource();
            return convertView;
        }
    }

    private ArrayList<ListItem> getListData() {
        ArrayList<ListItem> listMockData = new ArrayList<ListItem>();

        ListItem newsData = new ListItem();
        newsData.setImageUrl("ImageURL");
        newsData.setIconUrl("setIconURL");
        newsData.setTitle("hahaha");
        newsData.setSubTitle("WOWOW");
        newsData.setDate("1990");
        listMockData.add(newsData);

        ListItem newsData2 = new ListItem();
        newsData2.setImageUrl("ImageURL");
        newsData2.setIconUrl("setIconURL");
        newsData2.setTitle("hahaha");
        newsData2.setSubTitle("WOWOW");
        newsData2.setDate("1990");
        listMockData.add(newsData2);
        return listMockData;
    }







    public void navlistitem()
    {
        btnme = (Button) findViewById(R.id.btn_me);
        btnme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }
        });
    }


}