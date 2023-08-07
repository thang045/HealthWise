package com.example.healthwise_project.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.healthwise_project.Model.HealthRecordClass;
import com.example.healthwise_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HealthRecord extends AppCompatActivity {

    ListView listView;
    ArrayList<HealthRecordClass> healthRecordList;

    CustomAdapterHealthRecord customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_record);

        listView = (ListView) findViewById(R.id.lvHealthRecord);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        String userID = firebaseUser.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance(
                        "https://healthwise-project-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Appointments");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                healthRecordList = new ArrayList<HealthRecordClass>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    if(dataSnapshot.child("idUser").getValue().toString().equals(userID))
                    {
                        String datetime = dataSnapshot.child("datetime").getValue().toString();
                        String symptoms = dataSnapshot.child("symptoms").getValue().toString();
                        HealthRecordClass data = new HealthRecordClass(datetime,symptoms);

//                        System.out.println(datetime);
//                        System.out.println(symptoms);
//                        System.out.println(data.getDate());
                        healthRecordList.add(data);

                        customAdapter = new CustomAdapterHealthRecord(
                                HealthRecord.this,
                                R.layout.customlv_health_record,
                                healthRecordList);
                        listView.setAdapter(customAdapter);

                    }
                    for (HealthRecordClass health : healthRecordList)
                    {
                        System.out.println(health.getSymtomps());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}