package com.example.healthwise_project.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.healthwise_project.Model.Appointment;
import com.example.healthwise_project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DetailAppointment extends AppCompatActivity {
    TextView tvname, tvphone, tvsympton, tvtime, tvdoctor;
    ArrayList<Appointment> ListAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_appointment);

        addControls();

        Intent intent = this.getIntent();
        if(intent!=null){
            int id = Integer.parseInt(intent.getStringExtra("id"));

            DatabaseReference appointmentRef = FirebaseDatabase.getInstance(
                            "https://healthwise-project-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference("Appointments");

            appointmentRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    System.out.println(snapshot.getChildren());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }



    }

    public void addControls(){
        tvname = (TextView) findViewById(R.id.tvname);
        tvphone = (TextView) findViewById(R.id.tvphone);
        tvsympton = (TextView) findViewById(R.id.tvsymptons);
        tvtime = (TextView) findViewById(R.id.tvTime);
        tvdoctor = (TextView) findViewById(R.id.tvdoc);

    }
}