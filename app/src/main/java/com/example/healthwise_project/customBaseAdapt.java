package com.example.healthwise_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class customAdapterApp extends ArrayAdapter {
    Context ctx;
//    String lsTime[];
//    String  lsDate[];
//    String lsDoctors[];
    ArrayList<Appointment> arrHistoryApp = new ArrayList<>() ;
    LayoutInflater inflater;
    int layoutItem;

    public customAdapterApp(@NonNull Context ctx, int resource, @NonNull ArrayList<Appointment> arrayList){
       super(ctx, resource, arrayList);
       this.ctx = ctx;
       this.layoutItem = resource;
       this.arrHistoryApp = arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        Appointment appointment = arrHistoryApp.get(position);
        if(convertView == null){
            convertView = LayoutInflater.from(ctx).inflate(layoutItem, null);
        }
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        return  convertView;
    }

}
