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
import java.util.Date;

public class customAdapterApp extends BaseAdapter{
    private Context context;
    private Date[] date;
    private String[] doctorName;

    public customAdapterApp(Context context, Date[] date, String[] doctorName) {
        this.context = context;
        this.date = date;
        this.doctorName = doctorName;
    }

    @Override
    public int getCount() {
        return date.length;
    }

    @Override
    public Object getItem(int i) {
        return date[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;

        if(convertView ==null){
            view = LayoutInflater.from(context).inflate(R.layout.activity_lv_history_app,parent,false);
        } else{
            view = convertView;
        }

        TextView tvDate = view.findViewById(R.id.tvDate);
        TextView tvTime = view.findViewById(R.id.tvTime);
        TextView tvDoctor = view.findViewById(R.id.tvDoctor);

        tvDate.setText((CharSequence) date[position]);
        tvTime.setText((CharSequence) date[position]);
        tvDoctor.setText(doctorName[position]);
        return  view;
    }
}
