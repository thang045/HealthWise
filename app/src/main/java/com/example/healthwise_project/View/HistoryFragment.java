package com.example.healthwise_project.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.healthwise_project.Controller.CustomAdapterHealthRecord;
import com.example.healthwise_project.Controller.DetailAppointment;
import com.example.healthwise_project.Controller.HealthRecord;
import com.example.healthwise_project.Controller.customAdapterApp;
import com.example.healthwise_project.Model.Appointment;
import com.example.healthwise_project.Model.HealthRecordClass;
import com.example.healthwise_project.Model.HistoryAppointment;
import com.google.common.collect.ArrayListMultimap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.example.healthwise_project.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    customAdapterApp adapter;
    ArrayList<HistoryAppointment> HisAppointmentList = new ArrayList<>();
    ListView lvHistory;


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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        lvHistory = (ListView) view.findViewById(R.id.lvAppLog);


        loadDb();
        lvHistory.setClickable(true);

//        DatabaseReference appointmentRef = FirebaseDatabase.getInstance(
//                        "https://healthwise-project-default-rtdb.asia-southeast1.firebasedatabase.app/")
//                .getReference("Appointments");

        lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), DetailAppointment.class);
//                intent.putExtra("id", appointmentRef.child("id").toString());
                startActivity(intent);
            }
        });

        return view;

    }

    public void loadDb(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //assert firebaseUser != null;
        String userID = firebaseUser.getUid();
        DatabaseReference appointmentRef = FirebaseDatabase.getInstance(
                        "https://healthwise-project-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Appointments");

        appointmentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    if(dataSnapshot.child("idUser").getValue().toString().equals(userID))
                    {
                        String datetime = dataSnapshot.child("datetime").getValue().toString();
                        //int idDoc = Integer.parseInt(dataSnapshot.child("idDoctor").getValue().toString());
                        String nameDoc = dataSnapshot.child("idDoctor").getValue().toString();
                        HistoryAppointment data = new HistoryAppointment(datetime, nameDoc);

                        System.out.println(datetime);
                        System.out.println(nameDoc);
                        System.out.println(data.getDate());
                        HisAppointmentList = new ArrayList<HistoryAppointment>();
                        HisAppointmentList.add(data);
                    }
                }
                adapter = new customAdapterApp(getActivity(),HisAppointmentList);
                lvHistory.setAdapter(adapter);

                lvHistory.setClickable(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}