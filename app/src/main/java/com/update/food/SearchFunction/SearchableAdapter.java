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
import com.update.food.CafeTempVariable.CafeTempVariable;
import com.update.food.GetFunction.GetCafeShopListTask;
import com.update.food.List.ItemList;
import com.update.food.List.ShopList;
import com.update.food.R;

import java.util.ArrayList;

public class SearchableAdapter extends BaseAdapter implements Filterable {

    Context context;
    ArrayList<ShopList> countrylist;
    ArrayList<ShopList> mStringFilterList;

   // ArrayList<ItemList> itemlist;
    ValueFilter valueFilter;
    Double currentlatitude;
    Double currentlongtitude;
    ShopList dataitem;
    ItemList listdata;
    String result;
    public static int loadmoreitem=1;
    public static ArrayList<String> itemlist = new ArrayList<String>();

    public static boolean below10km;
    public static ArrayList<Double> latitudebelow10km= new ArrayList<Double>();;
    public static ArrayList<Double> longtitudebelow10km= new ArrayList<Double>();;
    public static ArrayList<ShopList> ShopListbelow5km = new ArrayList<>();
    public static ArrayList<ShopList> ShopListbelow10km = new ArrayList<>();
    public static ArrayList<ShopList> ShopListbelow15km = new ArrayList<>();
    public static ArrayList<ShopList> ShopListbelow20km = new ArrayList<>();

    public static ArrayList<String> Locationdistancebelow5km = new ArrayList<String>();
    public static ArrayList<String> Locationdistancebelow10km = new ArrayList<String>();
    public static ArrayList<String> Locationdistancebelow15km = new ArrayList<String>();
    public static ArrayList<String> Locationdistancebelow20km = new ArrayList<String>();

    public static ArrayList<String> Locationtimebelow5km = new ArrayList<String>();
    public static ArrayList<String> Locationtimebelow10km = new ArrayList<String>();
    public static ArrayList<String> Locationtimebelow15km = new ArrayList<String>();
    public static ArrayList<String> Locationtimebelow20km = new ArrayList<String>();

    public static ArrayList<String> Locationdistance = new ArrayList<String>();
    public static ArrayList<String> Locationtime = new ArrayList<String>();

    public static ArrayList<String> Locationdistance10km = new ArrayList<String>();

    public SearchableAdapter(Context context , final ArrayList<ShopList> countrylist,final ArrayList<String> itemlist, final Double currentlatitude, final Double currentlongtitude)
    {
        this.context = context;
        this.countrylist = countrylist;
        this.mStringFilterList = countrylist;
        this.currentlatitude = currentlatitude;
        this.currentlongtitude = currentlongtitude;
        this.itemlist = itemlist;
        loopingcalculation();

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
//            try
//            {
//                itemlist.add(i, new CalculateDistanceTaskCafe(context).execute(String.valueOf(currentlatitude), String.valueOf(currentlongtitude), countrylist.get(i).getShopOwnerLatitude(), countrylist.get(i).getShopOwnerLongtitude()).get());
//            //     System.out.println(result+"wtf");
//            } catch (InterruptedException e) {
//              Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
//            } catch (ExecutionException e) {
//                e.printStackTrace();


            if(!itemlist.isEmpty())
            {
               // System.out.println(SearchableAdapter.Locationdistance.get(i).toString());
                Double temp = Double.parseDouble(SearchableAdapter.Locationdistance.get(i).toString());

                if (temp <=10.0 && temp >5.00)
                {
                    ShopListbelow10km.add(countrylist.get(i));
                    Locationdistancebelow10km.add(SearchableAdapter.Locationdistance.get(i));
                    Locationtimebelow10km.add(SearchableAdapter.Locationtime.get(i));
                  // Toast.makeText(context,"below 10km"+countrylist.get(i).getShopID().toString(),Toast.LENGTH_SHORT).show();
                  //  System.out.println("below 10km"+SearchableAdapter.Locationdistance.get(i));
                    latitudebelow10km.add(currentlatitude);
                    longtitudebelow10km.add(currentlongtitude);
                  //  System.out.println(SearchableAdapter.Locationdistance.get(i));
                }
                 if(temp <= 5.00)
                {
                    ShopListbelow5km.add(countrylist.get(i));
                    Locationdistancebelow5km.add(SearchableAdapter.Locationdistance.get(i));
                    Locationtimebelow5km.add(SearchableAdapter.Locationtime.get(i));
                   // CafeTempVariable.CafeTemp5km.addAll(ShopListbelow5km);
                    System.out.println("below 5km"+SearchableAdapter.Locationdistance.get(i));
                    System.out.println("below 5km"+SearchableAdapter.Locationtime.get(i));
                 //   Toast.makeText(context,"below 5Km"+countrylist.get(i).getShopID().toString(),Toast.LENGTH_SHORT).show();
                }
                if(temp <= 15.00 && temp > 10.00)
                {
                    ShopListbelow15km.add(countrylist.get(i));
                    Locationdistancebelow15km.add(SearchableAdapter.Locationdistance.get(i));
                    Locationtimebelow15km.add(SearchableAdapter.Locationtime.get(i));
                 //   System.out.println("below 15km"+countrylist.get(i).getShopID().toString());
                   // Toast.makeText(context,"below 15km"+countrylist.get(i).getShopID().toString(),Toast.LENGTH_SHORT).show();
                }
                if(temp <= 20.00 && temp > 15.00)
                {
                    ShopListbelow20km.add(countrylist.get(i));
                    Locationdistancebelow20km.add(SearchableAdapter.Locationdistance.get(i));
                    Locationtimebelow20km.add(SearchableAdapter.Locationtime.get(i));
                   // System.out.println("below 20km"+countrylist.get(i).getShopID().toString());
                  //  Toast.makeText(context,"below 20km"+countrylist.get(i).getShopID().toString(),Toast.LENGTH_SHORT).show();
                }
             //   Toast.makeText(context,countrylist.get(i).getShopID().toString(),Toast.LENGTH_SHORT).show();

            }



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
       // System.out.println(""+this.itemlist.get(position));

            viewHolder.text3.setText(" Distance: " + SearchableAdapter.Locationdistance.get(position)+" km" +"\n "+
                    "Duration: "+SearchableAdapter.Locationtime.get(position)+" mins");
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
            System.out.println("SORT");
           // System.out.println(((ArrayList<ShopList>) results.values).size());
//            if(CafeTempVariable.CafeTemp.size()==countrylist.size())
//            {
//                //GetCafeShopListTask.listMockData.addAll(CafeTempVariable.CafeTemp);
//                System.out.println("SORT1");
//                GetCafeShopListTask.listMockData.clear();
//                GetCafeShopListTask.listMockData.addAll(CafeTempVariable.CafeTemp);
//            }
//            else
//            {
//
//                System.out.println("SORT2");
//                GetCafeShopListTask.listMockData.clear();
//                GetCafeShopListTask.listMockData.addAll(countrylist);
//
//                if(countrylist.size()==0)
//                {
//                    GetCafeShopListTask.listMockData.clear();
//                    GetCafeShopListTask.listMockData.addAll(CafeTempVariable.CafeTemp);
//                }
//            }

               // System.out.print(countrylist.get(1).getShopID());

            notifyDataSetChanged();
//            if(below10km==true)
//            {
//                System.out.println("10km");
//            }
        }

    }



}