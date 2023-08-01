package com.example.healthwise_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountDetailFragment extends Fragment {

    String name, healthRecord, userName;
    TextView tvHealthRecord, tvUN, tvName;
    ImageView imageAva;
    Button btnLogOut, btnChangePassword;
    FirebaseAuth auth;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountDetailFragment newInstance(String param1, String param2) {
        AccountDetailFragment fragment = new AccountDetailFragment();
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
        View view = inflater.inflate(R.layout.fragment_account_detail, container, false);

        tvUN = (TextView) view.findViewById(R.id.tvUN);
        tvHealthRecord = (TextView) view.findViewById(R.id.tvHealthRecord);
        tvName = (TextView) view.findViewById(R.id.tvName);
        btnLogOut = (Button) view.findViewById(R.id.btnLogOut);
        imageAva = (ImageView) view.findViewById(R.id.imageView);

        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        if (firebaseUser != null)
        {
            showUserProfile(firebaseUser);
        }
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                loadFragments(new AccountFragment());
            }
        });

        imageAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),UploadUserAvatar1.class);
                startActivity(intent);
            }
        });

        return view;
    }
    public  void showUserProfile(FirebaseUser firebaseUser)
    {
        String userID = firebaseUser.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance(
                "https://healthwise-project-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Registered Users");
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userDetail = snapshot.getValue(User.class);
                if (userDetail != null)
                {
                    name = firebaseUser.getEmail();
                    healthRecord = userDetail.getHealthRecord();
                    userName = userDetail.getRealName();

                    tvUN.setText(name);
                    tvHealthRecord.setText(healthRecord);
                    tvName.setText(userName);

                    //Display avatar
                    Uri uri = firebaseUser.getPhotoUrl();
                    imageAva.setImageURI(uri);
                    Picasso.get().load(uri).into(imageAva);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void loadFragments(Fragment fragment)
    {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.btmHW_Layout,fragment);
        ft.commit();
    }
}