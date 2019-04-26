package com.update.food.SearchFunction;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.update.food.List.ItemList;
import com.update.food.List.ShopList;
import com.update.food.R;

import java.util.ArrayList;

public class SearchableDistanceAdapter extends BaseAdapter implements Filterable {

    Context context;
    ArrayList<ShopList> countrylist;
    ArrayList<ShopList> mStringFilterList;
    ArrayList<String> distance = new ArrayList<String>();;
    ArrayList<String> duration = new ArrayList<String>();;

   // ArrayList<ItemList> itemlist;
    ValueFilter valueFilter;
    Double currentlatitude;
    Double currentlongtitude;
    ShopList dataitem;
    ItemList listdata;
    String result;
    ArrayList<String> itemlist = new ArrayList<String>();

    public static boolean below10km;
    public static ArrayList<Double> latitudebelow10km= new ArrayList<Double>();;
    public static ArrayList<Double> longtitudebelow10km= new ArrayList<Double>();;
    public static ArrayList<String> Locationdistance = new ArrayList<String>();
    public static ArrayList<String> Locationtime = new ArrayList<String>();

    public static ArrayList<String> Locationdistance10km = new ArrayList<String>();

    public SearchableDistanceAdapter(Context context , final ArrayList<ShopList> countrylist,final ArrayList<String> distance,final ArrayList<String> duration, final Double currentlatitude, final Double currentlongtitude)
    {
        this.context = context;
        this.countrylist = countrylist;
        this.mStringFilterList = countrylist;
        this.currentlatitude = currentlatitude;
        this.currentlongtitude = currentlongtitude;
        this.distance = distance;
        this.duration = duration;
    //    loopingcalculation();

    }
    static class ViewHolder {
        ImageView image;
        TextView text1;
        TextView text2;
        TextView text3;
        RatingBar rating;

    }

    public void loopingcalculation()
    {
        for(int i=0; i<countrylist.size(); i++)
        {

//            if(!itemlist.isEmpty())
//            {
//               // System.out.println(SearchableAdapter.Locationdistance.get(i).toString());
//                Double temp = Double.parseDouble(SearchableDistanceAdapter.Locationdistance.get(i).toString());
//
//                if (temp <=10.0)
//                {
//                    latitudebelow10km.add(currentlatitude);
//                    longtitudebelow10km.add(currentlongtitude);
//                  //  System.out.println(SearchableAdapter.Locationdistance.get(i));
//                }
//
//            }



        }

    }


    @Override
    public int getCount() {
        return countrylist.size();
    }

    @Override
    public Object getItem(int position) {
        return countrylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return countrylist.indexOf(getItem(position));
    }

    @Override
    public View getView(int position , View convertView , ViewGroup parent )
    {

        View rowView=convertView;
        final ViewHolder viewHolder;
        if(convertView==null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            rowView= inflater.inflate(R.layout.chinese_food_listview_layout, null, false);

            viewHolder= new ViewHolder();
            viewHolder.image = (ImageView) rowView.findViewById(R.id.img1);
            viewHolder.text1 = (TextView) rowView.findViewById(R.id.text1);
            viewHolder.text2 = (TextView) rowView.findViewById(R.id.text2);
            viewHolder.text3 = (TextView) rowView.findViewById(R.id.text3);
            viewHolder.rating = (RatingBar)rowView.findViewById(R.id.rating);
            rowView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        dataitem = countrylist.get(position);
        viewHolder.text1.setText(" " + dataitem.getShopName());
        viewHolder.text1.setTextSize(15);
        viewHolder.text1.setTypeface(viewHolder.text1.getTypeface(), Typeface.BOLD);

        viewHolder.text2.setText(" " + dataitem.getShopOwnerFAddress());
        viewHolder.text2.setTextSize(12);
        viewHolder.text2.setTypeface(viewHolder.text2.getTypeface(), Typeface.BOLD_ITALIC);

        viewHolder.text3.setText("Distance: " + distance.get(position)+" km" +
                '\n' +"Duration: "+duration.get(position)+" mins");

        viewHolder.text3.setTextSize(12);
        viewHolder.text3.setTypeface(viewHolder.text3.getTypeface(), Typeface.BOLD_ITALIC);
        int temp=Integer.parseInt(dataitem.getShopRatingHits());
        int temp2 =Integer.parseInt(dataitem.getShopRatingMarks());
        if(temp== 0)
        {
            viewHolder.rating.setRating(0);
        }
        else
        {
            int result =temp2/temp;
            viewHolder.rating.setRating(result);
        }
     //   System.out.println(""+itemlist.get(position));



       // if(itemlist.get(position).contains(""))
     //   {

     //   }
       // viewHolder.text3.setText(" " + dataitem);
    //    System.out.println(itemlist.get(position));
     //   if(itemlist.get(position).)
     //   {

    //   }
    //    else
     //   {
     //       viewHolder.text3.setText(" " + itemlist.get(position).toString());
     //       viewHolder.text3.setTextSize(15);
      //  }

        Picasso.with(context)
                .load("" + dataitem.getShopLogo())
                .placeholder(R.drawable.placeholder)   // optional
                .resize(400, 400)                        // optional
                .into(viewHolder.image);


        return rowView;
    }
    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<ShopList> filterList = new ArrayList<ShopList>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ( (mStringFilterList.get(i).getShopOwnerFAddress().toUpperCase() )
                            .contains(constraint.toString().toUpperCase())||(mStringFilterList.get(i).getShopName().toUpperCase() )
                            .contains(constraint.toString().toUpperCase())) {
                  

                        filterList.add(mStringFilterList.get(i));
                    }

                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;

            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            countrylist = (ArrayList<ShopList>) results.values;


            notifyDataSetChanged();
//            if(below10km==true)
//            {
//                System.out.println("10km");
//            }
        }

    }



}