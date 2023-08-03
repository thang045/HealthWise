package com.example.healthwise_project.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.healthwise_project.Model.Appointment;
import com.example.healthwise_project.Model.HistoryAppointment;
import com.example.healthwise_project.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class customAdapterApp extends ArrayAdapter<HistoryAppointment>{

//    Context context;
//    ArrayList<HistoryAppointment> arrayList;
//    int layout;
//    public customAdapterApp(@NonNull Context context,@NonNull ArrayList<HistoryAppointment> dataArrayList, int resource) {
//        super(context, resource, dataArrayList);
//        this.context = context;
//        this.layout = resource;
//        this.arrayList = dataArrayList;
//
//    }
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
//        HistoryAppointment data = arrayList.get(position);
//
//        if(view == null){
//            view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_history,parent,false);
//        }
//
//        TextView lsDate = view.findViewById(R.id.tvDate);
//        TextView lsDoctor = view.findViewById(R.id.tvDoctor);
//
//        lsDate.setText(data.getDate());
//        lsDoctor.setText(data.getIdDoctor());
//        return  view;
//
//    }
    public customAdapterApp(@NonNull Context context, ArrayList<HistoryAppointment> dataAppointmentList) {
        super(context, R.layout.lv_hisappointment, dataAppointmentList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        HistoryAppointment HisApp = getItem(position);

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.lv_hisappointment,parent, false);
        }
        TextView tvDate = view.findViewById(R.id.tvDate);
        TextView tvDoc = view.findViewById(R.id.tvDoctor);

        tvDate.setText(HisApp.getDate());
        tvDoc.setText(HisApp.getIdDoctor());

        return  view;
    }
}
