package com.example.registration;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class UserProfile extends AppCompatActivity {

    TextView markertxt,namee,numbr;
    ImageView userimg, bikepic;
    private StorageReference StorageReference;
    private com.google.firebase.storage.StorageReference bikeStorageReference;
    FirebaseAuth fauth;
    DatabaseReference ref,refn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userimg=findViewById(R.id.userimg);
        bikepic=findViewById(R.id.bikeimg);
        namee=(TextView)findViewById(R.id.txt1);
        numbr=(TextView)findViewById(R.id.txt2);

        fauth = FirebaseAuth.getInstance();
        bikeStorageReference= FirebaseStorage.getInstance().getReference().child("users/"+fauth.getCurrentUser().getUid()+"/BikeLending.jpg");
        StorageReference =FirebaseStorage.getInstance().getReference().child("users/"+fauth.getCurrentUser().getUid()+"/profile.jpg");
        // final StorageReference fileRef = StorageReference.child("profile.jpg");

        try {
            final File localFile = File.createTempFile("profile", "jpg");
            StorageReference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(UserProfile.this, "Working...", Toast.LENGTH_SHORT).show();
                            Bitmap bitmap=BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            userimg.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UserProfile.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            final File localFile = File.createTempFile("profile", "jpg");
            bikeStorageReference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(UserProfile.this, "Working...", Toast.LENGTH_SHORT).show();
                            Bitmap bitmap= BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            bikepic.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UserProfile.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        ref= FirebaseDatabase.getInstance().getReference().child("Users/"+fauth.getCurrentUser().getUid()+"/phoneNo");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String numb=dataSnapshot.getValue().toString();
                numbr.setText(numb);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        refn= FirebaseDatabase.getInstance().getReference().child("Users/"+fauth.getCurrentUser().getUid()+"/name");
        refn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name=dataSnapshot.getValue().toString();
                namee.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
