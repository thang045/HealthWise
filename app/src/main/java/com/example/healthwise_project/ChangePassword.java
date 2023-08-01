package com.example.healthwise_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TotpSecret;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePassword#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePassword extends Fragment {
    Button btnChangePassword, btnAuthenticate;
    EditText edtOldPassword, edtNewPassword, edtComfirmPassword;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChangePassword() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangePassword.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePassword newInstance(String param1, String param2) {
        ChangePassword fragment = new ChangePassword();
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
         View view = inflater.inflate(R.layout.fragment_change_password, container, false);
         btnChangePassword = (Button) view.findViewById(R.id.btnChangePassword);
         btnAuthenticate = (Button) view.findViewById(R.id.btnAuthenticatePassword);
         edtOldPassword = (EditText) view.findViewById(R.id.edtOldPassword);
         edtNewPassword = (EditText) view.findViewById(R.id.edtChangePassword);
         edtComfirmPassword = (EditText) view.findViewById(R.id.edtComfirmPassword);




        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser.equals(""))
        {
            Toast.makeText(getActivity(), "User Details not available", Toast.LENGTH_SHORT).show();
            firebaseUser.reload();

        }else {
            reauthenticateUser(firebaseUser);
        }

         return view;
    }
    public  void reauthenticateUser(FirebaseUser firebaseUser)
    {
        edtNewPassword.setEnabled(false);
        edtComfirmPassword.setEnabled(false);
        btnChangePassword.setEnabled(false);

        btnAuthenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = String.valueOf(edtOldPassword.getText());
                String newPassword = String.valueOf(edtNewPassword.getText());
                String confirmPassword = String.valueOf(edtComfirmPassword.getText());
                System.out.println(firebaseUser.getEmail().toString());
                System.out.println(oldPassword);

                if (TextUtils.isEmpty(oldPassword))
                {
                    Toast.makeText(getActivity(),"Current Password needed to change",Toast.LENGTH_SHORT).show();
                    edtOldPassword.setError("Please enter your current password to change");
                    edtOldPassword.requestFocus();
                }
                else {
                    AuthCredential authCredential = EmailAuthProvider.getCredential(firebaseUser.getEmail(),oldPassword);
                    firebaseUser.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            edtComfirmPassword.setEnabled(true);
                            edtNewPassword.setEnabled(true);
                            btnChangePassword.setEnabled(true);

                            Toast.makeText(getActivity(),"Authenticate success!",Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = String.valueOf(edtOldPassword.getText());
                String newPassword = String.valueOf(edtNewPassword.getText());
                String confirmPassword = String.valueOf(edtComfirmPassword.getText());
                System.out.println(newPassword);
                if (TextUtils.isEmpty(newPassword)) {
                    Toast.makeText(getActivity(), "Password needed", Toast.LENGTH_SHORT).show();
                    edtOldPassword.setError("Please enter your current password to change");
                    edtOldPassword.requestFocus();
                } else if (TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(getActivity(), "Password needed", Toast.LENGTH_SHORT).show();
                    edtNewPassword.setError("Please enter your new password to change");
                    edtNewPassword.requestFocus();
                } else if (!newPassword.matches(confirmPassword)) {
                    Toast.makeText(getActivity(), "Password did not match", Toast.LENGTH_SHORT).show();
                    edtNewPassword.setError("Please enter your new password to change");
                    edtNewPassword.requestFocus();
                }
                else {
                    firebaseUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getActivity(),"Password changed successfully",Toast.LENGTH_SHORT).show();
                                loadFragments(new AccountDetailFragment());
                            }
                            else {
                                try {
                                    throw task.getException();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }

                        }
                    });
                }
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