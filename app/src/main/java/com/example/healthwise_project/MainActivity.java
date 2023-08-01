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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FrameLayout HW_FrameLayout;
    BottomNavigationView HW_BtmNavView;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.btmHW_Layout, new AccountFragment());
        ft.commit();

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

                    return true;
                }

                if (id == R.id.appointmentItem)
                {
                    actionBar.setTitle("Appointment Page");
                    loadFragments(new AppointmentFragment());

                    return true;
                }

                if (id == R.id.historyItem)
                {
                    actionBar.setTitle("Appointment History Page");
                    loadFragments(new HistoryFragment());

                    return true;
                }

                if (id == R.id.accountItem)
                {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseUser user = auth.getCurrentUser();
                    if (user != null)
                    {
                        actionBar.setTitle("Account Page");
                        loadFragments(new AccountDetailFragment());
                        return true;
                    }
                    else {
                        actionBar.setTitle("Account Page");
                        loadFragments(new AccountFragment());

                        return true;
                    }
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