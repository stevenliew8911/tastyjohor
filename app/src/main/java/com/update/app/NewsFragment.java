package com.update.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.concurrent.ExecutionException;

/**
 * Created by Windows on 22/2/2016.
 */
public class NewsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.content_main, container, false);
        try {
          ListView  listView = (ListView) view.findViewById(R.id.content_list);
            new GetEventsTask(getActivity(), listView).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //do whatever you want here - like adding a listview or anything

        return view;
    }


    public NewsFragment()
    {

    }
}
