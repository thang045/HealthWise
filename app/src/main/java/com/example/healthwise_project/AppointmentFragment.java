package com.example.healthwise_project;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppointmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppointmentFragment extends Fragment {

    ArrayList<String> doctorNameArrayList = new ArrayList<>(), departmentArrayList = new ArrayList<>();
    Calendar date;
    Button btnDateTimePicker;
    Spinner spnDropdown_Appointment;
    TextView tvDateTime;

    CustomSpinnerAdapter adapter;

    ArrayList<Doctor> doctorArrayList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AppointmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppointmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AppointmentFragment newInstance(String param1, String param2) {
        AppointmentFragment fragment = new AppointmentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_appointment, container, false);

        btnDateTimePicker = (Button) view.findViewById(R.id.btnDateTimePicker);
        tvDateTime = (TextView) view.findViewById(R.id.tvDateTime);
        spnDropdown_Appointment = (Spinner) view.findViewById(R.id.spnDropdown_Appointment);

        loadDataFromFirebase();

        btnDateTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });

        return view;
    }

    //Date and Time picker function
    public void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        Context context = getContext();
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        Log.v("Date Time Picker","The chosen one " + date.getTime());
                        String month = "", dayOfWeek = "", AM_PM = "";

                        //Day of week
                        switch (date.get(Calendar.DAY_OF_WEEK)){
                            case 2:
                                dayOfWeek = "Monday";
                                break;
                            case 3:
                                dayOfWeek = "Tuesday";
                                break;
                            case 4:
                                dayOfWeek = "Wednesday";
                                break;
                            case 5:
                                dayOfWeek = "Thursday";
                                break;
                            case 6:
                                dayOfWeek = "Friday";
                                break;
                            case 7:
                                dayOfWeek = "Saturday";
                                break;
                            case 8:
                                dayOfWeek = "Sunday";
                                break;
                            default:
                                dayOfWeek = "x";
                                System.out.println("Invalid day of week.");
                                break;
                        }

                        //Month
                        switch (date.get(Calendar.DAY_OF_MONTH)){
                            case 1:
                                month = "January";
                                break;
                            case 2:
                                month = "February";
                                break;
                            case 3:
                                month = "March";
                                break;
                            case 4:
                                month = "April";
                                break;
                            case 5:
                                month = "May";
                                break;
                            case 6:
                                month = "June";
                                break;
                            case 7:
                                month = "July";
                                break;
                            case 8:
                                month = "August";
                                break;
                            case 9:
                                month = "September";
                                break;
                            case 10:
                                month = "October";
                                break;
                            case 11:
                                month = "November";
                                break;
                            case 12:
                                month = "December";
                                break;
                            default:
                                month = "xxx";
                                System.out.println("Invalid month.");
                                break;
                        }

                        // AM and PM for time
                        switch (date.get(Calendar.AM_PM)){
                            case 0:
                                AM_PM = "am";
                                break;
                            case 1:
                                AM_PM = "pm";
                                break;
                            default:
                                AM_PM = "Error";
                                break;
                        }

                        tvDateTime.setText(dayOfWeek + ", " + month +
                                " " + date.get(Calendar.DAY_OF_MONTH) +
                                "\n" + date.get(Calendar.HOUR) + ":" + date.get(Calendar.MINUTE) + " " + AM_PM);
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }
    public void loadDataFromFirebase(){

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://healthwise-project" +
                        "-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("Doctors");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Doctor doctor = dataSnapshot.getValue(Doctor.class);
                    doctorArrayList.add(doctor);
                }
                for (Doctor d : doctorArrayList){
                    doctorNameArrayList.add(d.getName());
                    departmentArrayList.add(d.getDepartment());
                }

                Log.d("Doctors", doctorNameArrayList.get(0));
                Log.d("Departments", departmentArrayList.get(0));

                String[] doctorName = new String[doctorNameArrayList.size()],
                        department = new String[departmentArrayList.size()];

                doctorName = doctorNameArrayList.toArray(doctorName);
                department = departmentArrayList.toArray(department);

                adapter = new CustomSpinnerAdapter(getContext(),  doctorName,
                        department);
                spnDropdown_Appointment.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}