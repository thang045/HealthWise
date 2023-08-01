package com.example.healthwise_project;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.common.collect.ArrayListMultimap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {
    ArrayList<String> doctorNameArrayList = new ArrayList<>();
    ArrayList<Date>dateArrList = new ArrayList<>();
    ListView lvHistory;
    ArrayList<Appointment> arrayListAppointments = new ArrayList<Appointment>();
    ArrayList<Date> listDate = new ArrayList<>();
    ArrayList<String> listDocName = new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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

    public void getDataFromDB(){
        FirebaseDatabase database = FirebaseDatabase.getInstance("");
        DatabaseReference myRef = database.getReference("Appointment");
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        lvHistory = (ListView) view.findViewById(R.id.lvHistory);


        String[] lsname = new String[]{"Kien", "Thang", "Thinh"};
        Date lsDate = new Date();
        String[] lsDoc = new String[] {"bs1","bs2","bs3"};


        //adapter = new customAdapterApp(HistoryFragment.this.getActivity(), R.layout.activity_lv_history_app, arrayAppList);
        lvHistory.setAdapter(adapter);
        return view;
    }

    public void loadDataFromFB(){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://console.firebase.google.com/project/healthwise-project/database/healthwise-project-default-rtdb/data/~2F");
        DatabaseReference myRef = database.getReference("Appointments");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Appointment appDetail = snapshot.getValue(Appointment.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}