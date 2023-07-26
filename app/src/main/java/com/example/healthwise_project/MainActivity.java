package com.example.healthwise_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    FrameLayout HW_FrameLayout;
    BottomNavigationView HW_BtmNavView;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        //-----------
        actionBar = getSupportActionBar();
        //-----------
        addEvents();
    }

    private void addControls()
    {
        HW_FrameLayout = (FrameLayout) findViewById(R.id.btmHW_Layout);
        HW_BtmNavView = (BottomNavigationView) findViewById(R.id.btmHW_Nav);
    }

    private void addEvents()
    {
        HW_BtmNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.homeItem)
                {
                    actionBar.setTitle("Home Page");
                    loadFragments(new HomeFragment());
                    Toast.makeText(MainActivity.this, "Switching Home Page!", Toast.LENGTH_SHORT).show();

                    return true;
                }

                if (id == R.id.appointmentItem)
                {
                    actionBar.setTitle("Appointment Page");
                    loadFragments(new AppointmentFragment());
                    Toast.makeText(MainActivity.this, "Switching Appointment Page!", Toast.LENGTH_SHORT).show();

                    return true;
                }

                if (id == R.id.healthRecordItem)
                {
                    actionBar.setTitle("Health Record Page");
                    loadFragments(new HealthRecordFragment());
                    Toast.makeText(MainActivity.this, "Switching Health Record Page!", Toast.LENGTH_SHORT).show();

                    return true;
                }

                if (id == R.id.accountItem)
                {
                    actionBar.setTitle("Account Page");
                    loadFragments(new AccountFragment());
                    Toast.makeText(MainActivity.this, "Switching Account Page!", Toast.LENGTH_SHORT).show();

                    return true;
                }

                return false;
            }
        });
    }

    public void loadFragments(Fragment fragment)
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.btmHW_Layout,fragment);
        ft.commit();
    }
}