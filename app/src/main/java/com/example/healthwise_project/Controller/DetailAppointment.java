package com.example.healthwise_project.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthwise_project.Model.Appointment;
import com.example.healthwise_project.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

public class DetailAppointment extends AppCompatActivity {
    TextView tvname, tvphone, tvsymptom, tvtime, tvdoctor;
    ArrayList<Appointment> ListAppointments;
    String name, phone, symptoms, time, doctor;
    Button btnCancelAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_appointment);

        addControls();

        Intent intent = this.getIntent();
        //int idApp1 = intent.getIntExtra("id", 0);
        String idApp1 = String.valueOf(intent.getIntExtra("id", 0));
        if(intent!=null){
            //int id = Integer.parseInt(intent.getStringExtra("id"));

            DatabaseReference appointmentRef = FirebaseDatabase.getInstance(
                            "https://healthwise-project-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference("Appointments");

            appointmentRef.addValueEventListener(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                        System.out.println(dataSnapshot.child("id").getValue()+" testtttttttttttttt");
                        if(dataSnapshot.child("id").getValue().toString().equals(idApp1))
                        {
                            System.out.println("Vo IF roiiiiiiiiiiiiii");

                            name = dataSnapshot.child("name").getValue().toString();
                            phone =  dataSnapshot.child("phone").getValue().toString();
                            symptoms =  dataSnapshot.child("symptoms").getValue().toString();
                            time =  dataSnapshot.child("datetime").getValue().toString();
                            doctor =  dataSnapshot.child("idDoctor").getValue().toString();

                            //System.out.println(name +" "+phone+" "+ symptoms+" "+time+" "+doctor );

                            tvname.setText("Name: " + name);
                            tvphone.setText("Phone: "+phone);
                            tvsymptom.setText("Symptoms: "+symptoms);
                            tvtime.setText("Time: "+time);
                            tvdoctor.setText("Doctor: "+doctor);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error)
                {

                }
            });
            btnCancelAppointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailAppointment.this);
                    builder.setTitle("Cancel appointment?");
                    builder.setMessage("Do you want to delete this appointment?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Query query = appointmentRef.orderByChild("id").equalTo(Integer.parseInt(idApp1));

                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren())
                                    {
                                        if(dataSnapshot.child("id").getValue().toString().equals(idApp1))
                                        {
                                            dataSnapshot.getRef().removeValue()
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused)
                                                {
                                                    Toast.makeText(DetailAppointment.this, "Appointment deleted", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                                    .addOnFailureListener(new OnFailureListener()
                                                    {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e)
                                                        {
                                                            Toast.makeText(DetailAppointment.this, "Failed to delete appointment", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            });
        }
    }
    public void addControls(){
        tvname = (TextView) findViewById(R.id.tvname);
        tvphone = (TextView) findViewById(R.id.tvphone);
        tvsymptom = (TextView) findViewById(R.id.tvsymptons);
        tvtime = (TextView) findViewById(R.id.tvTime);
        tvdoctor = (TextView) findViewById(R.id.tvdoc);
        btnCancelAppointment = (Button) findViewById(R.id.btnCancel);
    }
}