package com.example.healthwise_project.Controller;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.healthwise_project.R;
import com.example.healthwise_project.View.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadUserAvatar1 extends AppCompatActivity {

    private Uri uriImage;
    private static  final int PICK_IMAGE_REQUEST = 1;
    Button btnChangeAvatar,btnChooseAvatar;
    ImageView imageView;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_user_avatar1);

        addControl();
        addEvent();


    }

    public  void addControl()
    {
        btnChangeAvatar = (Button) findViewById(R.id.btnChangeAvatar);
        btnChooseAvatar = (Button) findViewById(R.id.btnChooseAvatar);
        imageView = (ImageView) findViewById(R.id.imgAvatar);

    }

    public  void addEvent()
    {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference("Display Avatar");

        assert firebaseUser != null;

        btnChangeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPic();
                Toast.makeText(UploadUserAvatar1.this, "Change sucess", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UploadUserAvatar1.this, MainActivity.class);
                startActivity(intent);
            }
        });


        btnChooseAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
    }
    public void imageChooser()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        //startActivityForResult(i, PICK_IMAGE_REQUEST);
        activityResultLauncher.launch(i);

    }

    public void uploadData()
    {

    }
    public void uploadPic()
    {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference("Display Avatar");
        if (imageView!= null || uriImage != null)
        {
            assert firebaseUser != null;
            StorageReference storageReference = storageRef.child(firebaseUser.getUid()+
                    "." + getFileExtension(uriImage));
            storageReference.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri downloadUri = uri;
                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setPhotoUri(downloadUri).build();
                            firebaseUser.updateProfile(profileChangeRequest);
                        }
                    });
                }
            });
        }

    }
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        uriImage = intent.getData();
                        imageView.setImageURI(uriImage);
                    }
                }
            }
    );

    public String getFileExtension(Uri uri)
    {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}