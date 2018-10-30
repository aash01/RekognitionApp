package com.example.aashr.rekognition;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;



public class MainActivity extends AppCompatActivity {

    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference myRef = database.getReference("LockStatus");
    private Button log;
    private Switch lock;
    private Button showImage;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private Button upload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        log = (Button) findViewById(R.id.log);
        lock = (Switch)findViewById(R.id.lock);
        upload = (Button) findViewById(R.id.upload);

        String path = "guest.jpg";
//        upload = (Button) findViewById(R.id.upload);

        Toast.makeText(this, ""+lock.isChecked(), Toast.LENGTH_SHORT).show();

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Log.class);
                i.putExtra("user", getIntent().getStringExtra("user"));
                startActivity(i);
            }
        });

        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lock.isChecked()){
                    Toast.makeText(MainActivity.this, ""+lock.isChecked(), Toast.LENGTH_SHORT).show();
                    myRef.setValue("True");
                }else{
                    Toast.makeText(MainActivity.this, ""+lock.isChecked(), Toast.LENGTH_SHORT).show();
                    myRef.setValue("False");
                }
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Upload.class);
                i.putExtra("user", getIntent().getStringExtra("user"));
                startActivity(i);
            }
        });;


    }
//    public void showPic(){
//        final StorageReference storageRef = storage.getReference();
//
//        storageRef.child("31901843_1667560593340196_1610707049186852864_n.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Toast.makeText(MainActivity.this, "FINISHED", Toast.LENGTH_SHORT).show();
//                storageRef.child("Capture.JPG");
//
////                Uri url = storageRef.child("31901843_1667560593340196_1610707049186852864_n.jpg").getDownloadUrl().
//
//                new DownloadImageTask((ImageView) findViewById(R.id.photo))
//                        .execute(uri.toString());
//                Toast.makeText(MainActivity.this, uri.toString(), Toast.LENGTH_LONG).show();
////                Picasso.with(MainActivity.this).load(url).into(photo);
//
//
//
//
//                // Got the download URL for 'users/me/profile.png'
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                Toast.makeText(MainActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
//
//                // Handle any errors
//            }
//        });
//
//    }



}

