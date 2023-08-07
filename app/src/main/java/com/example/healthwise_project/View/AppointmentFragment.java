package com.example.healthwise_project.View;

import android.annotation.SuppressLint;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.healthwise_project.Controller.CustomSpinnerAdapter;
import com.example.healthwise_project.Model.Doctor;
import com.example.healthwise_project.Model.Appointment;
import com.example.healthwise_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppointmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppointmentFragment extends Fragment {

    ArrayList<String> doctorNameArrayList = new ArrayList<>(), departmentArrayList = new ArrayList<>();
    Calendar date;
    String selectedDate = "", selectedTime = "";
    Button btnDateTimePicker, btnAddAppointment;
    Spinner spnDropdown_Appointment;
    TextView tvDateTime;
    EditText edtName_Appointment, edtPhone_Appointment, edtSymptoms_Appointment;

    CustomSpinnerAdapter adapter;
    FirebaseAuth firebaseAuth;

    ArrayList<Doctor> doctorArrayList = new ArrayList<Doctor>();
    ArrayList<Appointment> appointmentArrayList = new ArrayList<Appointment>();

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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_appointment, container, false);

        btnDateTimePicker = (Button) view.findViewById(R.id.btnDateTimePicker);
        btnAddAppointment = (Button) view.findViewById(R.id.btnAddAppointment);
        tvDateTime = (TextView) view.findViewById(R.id.tvDateTime);
        spnDropdown_Appointment = (Spinner) view.findViewById(R.id.spnDropdown_Appointment);
        edtName_Appointment = (EditText) view.findViewById(R.id.edtName_Appointment);
        edtPhone_Appointment = (EditText) view.findViewById(R.id.edtPhone_Appointment);
        edtSymptoms_Appointment = (EditText) view.findViewById(R.id.edtSymptoms_Appointment);

        loadDataFromFirebase();

        btnDateTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });
        btnAddAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://healthwise-project" +
                        "-default-rtdb.asia-southeast1.firebasedatabase.app");
                DatabaseReference appointmentsRef = database.getReference("Appointments");


                String name = edtName_Appointment.getText().toString();

                String phone = "";
                Pattern p = Pattern.compile("0[0-9]{9}");
                Matcher m = p.matcher(edtPhone_Appointment.getText());
                if (m.matches()){
                    phone = edtPhone_Appointment.getText().toString();
                }else {
                    Toast.makeText(getContext(), "Số điện thoại không hợp lệ!",
                            Toast.LENGTH_SHORT).show();
                }
                String symptoms = edtSymptoms_Appointment.getText().toString();
                Doctor selectedDoctor =
                        getDoctor(spnDropdown_Appointment.getSelectedItemPosition());
                int idDoctor = selectedDoctor.getId();
                int id = appointmentArrayList.size() + 1;

                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                String idUser = firebaseUser.getUid();

                if (name.isEmpty() || phone.isEmpty() || symptoms.isEmpty() || selectedDate.isEmpty() || selectedTime.isEmpty()){
                    Toast.makeText(getContext(), "Please don't leave any credentials empty!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Appointment appointment = new Appointment(id, name, phone, symptoms,
                                selectedTime + " " + selectedDate,
                                idDoctor, idUser);
                        appointmentsRef.child("Appointment_" + (appointmentArrayList.size() + 1)).setValue(appointment);
                        Toast.makeText(getContext(),"Appointment added!", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                    }
            }
        });
        return view;
    }
    public Doctor getDoctor(int position){
        return doctorArrayList.get(position);
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
                        switch (date.get(Calendar.MONTH)){
                            case 0:
                                month = "January";
                                break;
                            case 1:
                                month = "February";
                                break;
                            case 2:
                                month = "March";
                                break;
                            case 3:
                                month = "April";
                                break;
                            case 4:
                                month = "May";
                                break;
                            case 5:
                                month = "June";
                                break;
                            case 6:
                                month = "July";
                                break;
                            case 7:
                                month = "August";
                                break;
                            case 8:
                                month = "September";
                                break;
                            case 9:
                                month = "October";
                                break;
                            case 10:
                                month = "November";
                                break;
                            case 11:
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

                        Log.d("sYear", "" + date.get(Calendar.YEAR));
                        Log.d("cYear", "" + currentDate.get(Calendar.YEAR));

                        Log.d("sMonth", "" + date.get(Calendar.MONTH));
                        Log.d("cMonth", "" + currentDate.get(Calendar.MONTH));

                        Log.d("sDay", "" + date.get(Calendar.DAY_OF_MONTH));
                        Log.d("cDay", "" + currentDate.get(Calendar.DAY_OF_MONTH));

                        if(date.get(Calendar.YEAR) >= currentDate.get(Calendar.YEAR)){

                            if(date.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH)){

                                if(date.get(Calendar.DAY_OF_MONTH) - currentDate.get(Calendar.DAY_OF_MONTH) >= 2){

                                    if(date.get(Calendar.AM_PM) == 0){

                                        if(date.get(Calendar.HOUR) >= 7 && date.get(Calendar.HOUR) <= 11){

                                                selectedDate = dayOfWeek + ", " + month +
                                                        " " + date.get(Calendar.DAY_OF_MONTH) + " " + date.get(Calendar.YEAR);
                                                selectedTime =
                                                        convertTime(date.get(Calendar.HOUR)) + ":" + convertTime(date.get(Calendar.MINUTE)) + " " + AM_PM;
                                                tvDateTime.setText(selectedDate + "\n" + selectedTime);

                                        }else{
                                            Toast.makeText(getContext(), "Vui lòng chọn trong " +
                                                    "khung giờ 7-11am và 1-5pm!", Toast.LENGTH_LONG);
                                        }
                                    } else if (date.get(Calendar.AM_PM) == 1) {
                                        if(date.get(Calendar.HOUR) >= 1 && date.get(Calendar.HOUR) <= 5){
                                                selectedDate = dayOfWeek + ", " + month +
                                                        " " + date.get(Calendar.DAY_OF_MONTH) + " " + date.get(Calendar.YEAR);
                                                selectedTime =
                                                        convertTime(date.get(Calendar.HOUR)) + ":" + convertTime(date.get(Calendar.MINUTE)) + " " + AM_PM;
                                                tvDateTime.setText(selectedDate + "\n" + selectedTime);
                                        }else{
                                            Toast.makeText(getContext(), "Vui lòng chọn trong " +
                                                    "khung giờ 7-11am và 1-5pm!", Toast.LENGTH_LONG);
                                        }
                                    }
                                }else {
                                    Toast.makeText(getContext(), "Ngày đã chọn không phù hợp",
                                            Toast.LENGTH_LONG).show();
                                    Toast.makeText(getContext(), "Vui lòng chọn lịch cách 2 ngày " +
                                                    "so với ngày hiện tại!",
                                            Toast.LENGTH_LONG).show();
                                }

                            }else if(monthOfYear > currentDate.get(Calendar.MONTH)){
                                if(date.get(Calendar.AM_PM) == 0){
                                    if(date.get(Calendar.HOUR) >= 7 && date.get(Calendar.HOUR) <= 11){
                                            selectedDate = dayOfWeek + ", " + month +
                                                    " " + date.get(Calendar.DAY_OF_MONTH) + " " + date.get(Calendar.YEAR);
                                            selectedTime =
                                                    date.get(Calendar.HOUR) + ":" + date.get(Calendar.MINUTE) + " " + AM_PM;
                                            tvDateTime.setText(selectedDate + "\n" + selectedTime);
                                    }else{
                                        Toast.makeText(getContext(), "Vui lòng chọn trong " +
                                                "khung giờ 7-11am và 1-5pm!", Toast.LENGTH_LONG);
                                    }
                                } else if (date.get(Calendar.AM_PM) == 1) {
                                    if(date.get(Calendar.HOUR) >= 1 && date.get(Calendar.HOUR) <= 5){
                                            selectedDate = dayOfWeek + ", " + month +
                                                    " " + date.get(Calendar.DAY_OF_MONTH) + " " + date.get(Calendar.YEAR);
                                            selectedTime =
                                                    convertTime(date.get(Calendar.HOUR)) + ":" + convertTime(date.get(Calendar.MINUTE)) + " " + AM_PM;
                                            tvDateTime.setText(selectedDate + "\n" + selectedTime);
                                    }else{
                                        Toast.makeText(getContext(), "Vui lòng chọn trong " +
                                                "khung giờ 7-11am và 1-5pm!", Toast.LENGTH_LONG);
                                    }
                                }
                            }else{
                                Toast.makeText(getContext(), "Tháng đã chọn không phù hợp",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getContext(), "Năm đã chọn không phù hợp",
                                    Toast.LENGTH_LONG).show();
                        }


                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }
    //Add "0" before value of hour and minute if < 10
    public String convertTime(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
        }
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
//        DatabaseReference doctorsRef = database.getReference("Doctors");
//        doctorsRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    Doctor doctor = dataSnapshot.getValue(Doctor.class);
//                    doctorArrayList.add(doctor);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        DatabaseReference appointmentsRef = database.getReference("Appointments");

        appointmentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Appointment appointment = dataSnapshot.getValue((Appointment.class));
                    appointmentArrayList.add(appointment);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}