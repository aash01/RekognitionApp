package com.example.aashr.rekognition;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class GuestPicture extends AppCompatActivity {
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private String time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_picture);
        time = getIntent().getStringExtra("time");
        showPic();
//        Toast.makeText(GuestPicture.this, time, Toast.LENGTH_SHORT).show();

    }
    public void showPic(){
        final StorageReference storageRef = storage.getReference();

        storageRef.child(time).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
//                Toast.makeText(MainActivity.this, "FINISHED", Toast.LENGTH_SHORT).show();
//                storageRef.child("Capture.JPG");

//                Uri url = storageRef.child("31901843_1667560593340196_1610707049186852864_n.jpg").getDownloadUrl().

                new DownloadImageTask((ImageView) findViewById(R.id.guestImage))
                        .execute(uri.toString());
//                Toast.makeText(L.this, uri.toString(), Toast.LENGTH_LONG).show();
//                Picasso.with(MainActivity.this).load(url).into(photo);




                // Got the download URL for 'users/me/profile.png'
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
//                Toast.makeText(MainActivity.this, "FAILED", Toast.LENGTH_SHORT).show();

                // Handle any errors
            }
        });

    }
}
