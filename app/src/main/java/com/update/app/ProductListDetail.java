package com.update.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Windows on 22/2/2016.
 */
public class ProductListDetail extends Fragment{

    String eventid;
    ListView listView;
    Button redeem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
            {
                final View root = inflater.inflate(R.layout.list_product_detail_redeem, container, false);

                try
       {
            listView = (ListView) root.findViewById(R.id.content_list);
           Button redeem = (Button)root.findViewById(R.id.redeem);
           redeem.setVisibility(View.VISIBLE);
           redeem.bringToFront();

           new GetEventsByIdTask(getActivity(), listView,"1").execute().get();

           redeem.setOnClickListener(new View.OnClickListener()
           {
               @Override
               public void onClick(View v)
              {
                  RedeemDialog alert = new RedeemDialog();
                  alert.showDialog(getActivity(), "wow");
               }
           });

      } catch (InterruptedException e) {
           e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }




        //do whatever you want here - like adding a listview or anything

        return root;
    }


    public ProductListDetail()
    {

    }



}
