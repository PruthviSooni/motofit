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
        LayoutInflater inflater = context.getLayoutInflater();
        View listviewItem = inflater.inflate(R.layout.service_list_layout,null,true);
        TextView location = listviewItem.findViewById(R.id.usr_location);
        TextView time = listviewItem.findViewById(R.id.time);
        TextView service_type = listviewItem.findViewById(R.id.service_type);
        TextView notes = listviewItem.findViewById(R.id.usr_notes);
        TextView odometer = listviewItem.findViewById(R.id.usr_odometer);

        Services services = serviceList.get(position);
        location.setText(services.location);
        time.setText(services.time);
        service_type.setText(services.type_service);
        notes.setText(services.notes);
        odometer.setText(services.odometer);
        return listviewItem;


    }
}
