package com.update.app;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by Windows on 19/2/2016.
 */
public class GridViewListFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.grid_view, container, false);
        GridView gridView;
        gridView = (GridView)view.findViewById(R.id.grid_view_list);
        gridView.setAdapter(new GridViewAdapter(getActivity()));
        //do whatever you want here - like adding a listview or anything

        return view;
    }


        // Instance of ImageAdapter Class


    public GridViewListFragment()
    {

    }


}
