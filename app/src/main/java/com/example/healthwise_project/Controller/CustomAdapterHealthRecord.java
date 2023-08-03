package com.example.healthwise_project.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.healthwise_project.Model.HealthRecordClass;
import com.example.healthwise_project.R;

import java.util.ArrayList;

public class CustomAdapterHealthRecord extends ArrayAdapter {
    Context context;
    ArrayList<HealthRecordClass> arrayList;
    int layout;
    public CustomAdapterHealthRecord(@NonNull Context context, int resource, @NonNull ArrayList<HealthRecordClass> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layout = resource;
        this.arrayList = objects;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HealthRecordClass data = arrayList.get(position);

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,null);
        }
        TextView txtDate = convertView.findViewById(R.id.tvDateHealthRecord);
        txtDate.setText(data.getDate());

        TextView txt = convertView.findViewById(R.id.tvSymptonHealthRecord);
        txt.setText(data.getSymtomps());
        return convertView;
    }
}
