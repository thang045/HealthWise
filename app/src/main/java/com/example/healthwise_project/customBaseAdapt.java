package com.example.healthwise_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class customBaseAdapt extends BaseAdapter {
    Context ctx;
    String lsTime[];
    String  lsDate[];
    String lsDoctors[];
    LayoutInflater inflater;

    public customBaseAdapt(Context context, String [] time, String [] date, String [] doctors ){
        this.ctx = context;
        this.lsTime = time;
        this.lsDate = date;
        this.lsDoctors = doctors;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return lsDate.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_history_app, null);
        TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
        TextView tvTime = (TextView) view.findViewById(R.id.tvTime);
        TextView tvDoctor = (TextView) view.findViewById(R.id.tvDoctor);

        tvDate.setText(lsDate[i]);
        tvTime.setText(lsTime[i]);
        tvDoctor.setText(lsDoctors[i]);

        return view;
    }
}
