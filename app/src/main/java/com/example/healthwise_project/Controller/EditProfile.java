package com.example.healthwise_project.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthwise_project.R;
import com.example.healthwise_project.View.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfile extends AppCompatActivity {

    EditText edtChangeName;
    Button btnChangeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        addControl();
        addEvent();
    }

    public void addControl()
    {
        btnChangeName = (Button) findViewById(R.id.btnChangeName);
        edtChangeName = (EditText) findViewById(R.id.edtNewName);
    }
    public void addEvent()
    {
        btnChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String changeName = String.valueOf(edtChangeName.getText());
                System.out.println(changeName);
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference reference = FirebaseDatabase.getInstance(
                                "https://healthwise-project-default-rtdb.asia-southeast1.firebasedatabase.app/")
                        .getReference("Registered Users");
                assert firebaseUser != null;
                String userID = firebaseUser.getUid();
                reference.child(userID).child("realName").setValue(changeName);
                Toast.makeText(EditProfile.this,"Change Name Success!",Toast.LENGTH_LONG);
                Intent intent = new Intent(EditProfile.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}