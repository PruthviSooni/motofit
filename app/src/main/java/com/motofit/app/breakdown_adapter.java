package com.motofit.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.motofit.app.Firebase_Classes.Breakdown;

import java.util.List;

public class breakdown_adapter extends ArrayAdapter<Breakdown> {
    private Activity context;
    private List<Breakdown> breakdownList;

    public breakdown_adapter(Activity context, List<Breakdown> breakdownList) {
        super(context, R.layout.breakdown_list_layout, breakdownList);
        this.context = context;
        this.breakdownList = breakdownList;
    }

    @NonNull
    @Override
    @SuppressLint("ViewHolder")
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("InflateParams") View listview_Item = inflater.inflate(R.layout.breakdown_list_layout, null, true);
        TextView location = listview_Item.findViewById(R.id.usr_location);
        TextView time = listview_Item.findViewById(R.id.time);
        TextView break_type = listview_Item.findViewById(R.id.break_type);
        TextView brand = listview_Item.findViewById(R.id.bike_brand);
        TextView model = listview_Item.findViewById(R.id.bike_model);

        Breakdown breakdown = breakdownList.get(position);
        location.setText(breakdown.Location);
        time.setText(breakdown.Date_and_Time);
        brand.setText(breakdown.Brand);
        model.setText(breakdown.Model);
        break_type.setText(breakdown.Dropdown_service);
        return listview_Item;


    }
}
