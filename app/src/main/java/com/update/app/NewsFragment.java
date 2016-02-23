package com.update.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.concurrent.ExecutionException;

/**
 * Created by Windows on 22/2/2016.
 */
public class NewsFragment extends Fragment {
    ListView  listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.content_main, container, false);
        try {
            listView = (ListView) view.findViewById(R.id.content_list);
            new GetEventsTask(getActivity(), listView).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                ListItem newsItem = GetEventsTask.listMockData.get(position);
                //listView.removeAllViews();
                //  listItems.clear();
                //  NewsDetailFragment news= new NewsDetailFragment();
             //  System.out.println( newsItem.getId());
                try {

                    new GetEventsByIdTask(getActivity(), listView,newsItem.getId()).execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }



            }
        });
        //do whatever you want here - like adding a listview or anything

        return view;
    }


    public NewsFragment()
    {

    }
}
