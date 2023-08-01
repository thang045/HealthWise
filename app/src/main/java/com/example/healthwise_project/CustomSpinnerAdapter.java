package com.example.healthwise_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomSpinnerAdapter extends BaseAdapter {

    private Context context;
    private String[] doctorName;
    private String[] department;

    public CustomSpinnerAdapter(Context context, String[] name, String[] dept) {
        this.context = context;
        this.doctorName = name;
        this.department = dept;
    }

    @Override
    public int getCount() {
        return doctorName.length;
    }

    @Override
    public Object getItem(int position) {
        return doctorName[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            //nếu convertView là null, phương thức sẽ tạo một View mới bằng cách sử dụng LayoutInflater để inflate layout của item từ tệp R.layout.custom_demo_spinner
            view = LayoutInflater.from(context).inflate(R.layout.custom_spinner, parent, false);
        } else {
            //Nếu convertView không null, nghĩa là có một View có sẵn để sử dụng lại, thì phương thức sẽ sử dụng convertView đó
            view = convertView;
        }

        //Tìm thành phần
        ImageView icon = view.findViewById(R.id.img_avatar);
        TextView name = view.findViewById(R.id.tvDoctorName_Spinner);
        TextView dept = view.findViewById(R.id.tvDepartment_Spinner);

        icon.setImageResource(R.drawable.ic_launcher_background);
        name.setText(doctorName[position]);
        dept.setText(department[position] + " Department");
        return view;
    }
}
