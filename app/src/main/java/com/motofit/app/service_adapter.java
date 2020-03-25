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

import com.motofit.app.Firebase_Classes.Services;

import java.util.List;

public class service_adapter extends ArrayAdapter<Services> {
    private Activity context;
    private List<Services> serviceList;

    public service_adapter(Activity context, List<Services> servicesList) {
        super(context, R.layout.service_list_layout, servicesList);
        this.context = context;
        this.serviceList = servicesList;
    }

    @NonNull
    @Override
    @SuppressLint("ViewHolder")
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View list_view_Item = inflater.inflate(R.layout.service_list_layout, null, true);
        TextView location = list_view_Item.findViewById(R.id.usr_location);
        TextView time = list_view_Item.findViewById(R.id.time);
        TextView service_type = list_view_Item.findViewById(R.id.service_type);
        TextView notes = list_view_Item.findViewById(R.id.usr_notes);
        TextView odometer = list_view_Item.findViewById(R.id.usr_odometer);

        Services services = serviceList.get(position);
        location.setText(services.location);
        time.setText(services.time);
        service_type.setText(services.type_service);
        notes.setText(services.notes);
        odometer.setText(services.odometer);
        return list_view_Item;


    }
}
