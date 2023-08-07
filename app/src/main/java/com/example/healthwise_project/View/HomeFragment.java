package com.example.healthwise_project.View;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.healthwise_project.Model.Appointment;
import com.example.healthwise_project.Model.HealthRecordClass;
import com.example.healthwise_project.Model.User;
import com.example.healthwise_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    String userName, dateTime, sympton;
    TextView tvRetrieveName, tvRetrieveTime, tvRetrieveSympton;
    TextView tvUpcomingAppointment, tvDoctor, tvTime;
    FirebaseAuth auth;
    DatabaseReference myRef;
    Button btnViewAppointment;
    ArrayList<HealthRecordClass> healthRecordClassArrayList;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        // Inflate the layout for this fragment"
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        //-------------
        tvRetrieveName = (TextView) view.findViewById(R.id.tvRetrieveName);
        tvUpcomingAppointment = (TextView) view.findViewById(R.id.tvUpcomingAppointment);
        tvTime = (TextView) view.findViewById(R.id.tvDateTime);
        tvDoctor = (TextView) view.findViewById(R.id.tvDoctor);
        btnViewAppointment = (Button) view.findViewById(R.id.btnViewAppointment);
        tvRetrieveTime = (TextView) view.findViewById(R.id.tvRetrieveTime);
        tvRetrieveSympton = (TextView) view.findViewById(R.id.tvRetrieveSympton);
        //-------------

        //get Current User form Firebase
        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser1 = auth.getCurrentUser();

        if (firebaseUser1 != null)
        {
            showUserProfile(firebaseUser1);
            getAppointmentQuantity(firebaseUser1);
            getTheNextAppointment(firebaseUser1);
        }

        addEvents();
        return view;

    }
    private void addEvents()
    {
        btnViewAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.btmHW_Layout, new HistoryFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    public  void showUserProfile(FirebaseUser firebaseUser)
    {
        //get Current User ID from Registered Users table
        String userID = firebaseUser.getUid();
        myRef = FirebaseDatabase.getInstance(
                        "https://healthwise-project-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Registered Users");
        myRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userDetail = snapshot.getValue(User.class);
                if (userDetail != null)
                {
                    userName = firebaseUser.getEmail();
                    tvRetrieveName.setText(userName);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getAppointmentQuantity(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();
        myRef = FirebaseDatabase.getInstance("https://healthwise-project-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Appointments");

        Query query = myRef.orderByChild("idUser").equalTo(userID);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int appointmentCount = (int) dataSnapshot.getChildrenCount();
                tvUpcomingAppointment.setText("Quantity of appointments: " + appointmentCount);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });
    }

    public void getTheNextAppointment(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();
        myRef = FirebaseDatabase.getInstance("https://healthwise-project-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Appointments");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                healthRecordClassArrayList = new ArrayList<HealthRecordClass>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    if(dataSnapshot.child("idUser").getValue().toString().equals(userID));
                    {
                        String datetime = dataSnapshot.child("datetime").getValue().toString();
                        String symptoms = dataSnapshot.child("symptoms").getValue().toString();
                        HealthRecordClass newestData = new HealthRecordClass(datetime,symptoms);
                        healthRecordClassArrayList.add(newestData);

                        tvRetrieveTime.setText(datetime);
                        tvRetrieveSympton.setText(symptoms);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}