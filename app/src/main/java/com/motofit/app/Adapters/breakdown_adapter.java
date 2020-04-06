package com.motofit.app.Adapters;

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
import com.motofit.app.R;

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
        TextView time = listview_Item.findViewById(R.id.time);
        TextView break_type = listview_Item.findViewById(R.id.break_type);
        TextView model = listview_Item.findViewById(R.id.bike_model);
        Breakdown breakdown = breakdownList.get(position);
        time.setText(breakdown.Date_and_Time);
        model.setText(breakdown.Model);
        break_type.setText(breakdown.Dropdown_service);
        return listview_Item;


    }
}
