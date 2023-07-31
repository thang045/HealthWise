package com.example.healthwise_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    Button btnSignUp;
    TextView tvError;
    EditText edtUS2, edtPW2;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        FirebaseAuth auth = FirebaseAuth.getInstance();


        edtUS2 = view.findViewById(R.id.edtUS2);
        edtPW2 = view.findViewById(R.id.edtPW2);
        btnSignUp = (Button) view.findViewById(R.id.btnSignUp);
        tvError = (TextView) view.findViewById(R.id.tvError);


        btnSignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String email, password;
                email = String.valueOf(edtUS2.getText());
                password = String.valueOf(edtPW2.getText());
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful())
                                {
                                    User user = new User(email, "Null","Null");
                                    System.out.println(user.getUserName());

                                    FirebaseUser currentUser = auth.getCurrentUser();

                                    DatabaseReference reference = FirebaseDatabase.getInstance("https://healthwise-project-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Registered Users");

                                    assert currentUser != null;
                                    reference.child(currentUser.getUid()).setValue(user);

                                    Toast.makeText(getActivity(), "Sign up success!", Toast.LENGTH_SHORT).show();
                                    loadFragments(new AccountFragment());
                                }
                                else
                                {
                                    try {
                                        throw task.getException();
                                    }
                                    catch (FirebaseAuthInvalidCredentialsException e)
                                    {
                                        tvError.setText("Your email is invalid or already in use");
                                    }
                                    catch (FirebaseAuthUserCollisionException e)
                                    {
                                        tvError.setText("This email has already in use, use another email");
                                    }
                                    catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }

                                }
                            }
                        });
            }
        });

        return view;
    }
    public void loadFragments(Fragment fragment)
    {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.btmHW_Layout,fragment);
        ft.commit();
    }

}