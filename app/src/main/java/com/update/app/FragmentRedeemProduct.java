package com.update.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
/**
 * Created by Windows on 2/3/2016.
 */
public class FragmentRedeemProduct extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.redeem_list_nav, container, false);

        //do whatever you want here - like adding a listview or anything

        return view;
    }


}
