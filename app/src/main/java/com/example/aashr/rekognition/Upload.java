package com.example.aashr.rekognition;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Upload extends AppCompatActivity {
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference myRef = database.getReference("Name");
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private ImageView mPhotoCapturedImageView = null;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private Button add;
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        mPhotoCapturedImageView = (ImageView)findViewById(R.id.capturePhotoImageView);
        add = (Button) findViewById(R.id.add);
        name = (EditText) findViewById(R.id.name);




    }

    public static void startupTranferServiceEarlyToAvoidBugs(Context context) {
        final TransferUtility tu = new TransferUtility(
                new AmazonS3Client((AWSCredentials)null),
                context);
        tu.cancel(Integer.MAX_VALUE - 1);
    }

    public void takePhoto(View view){
//        TransferUtility transferUtility = new TransferUtility(s3, getApplicationContext());
//        TransferUtility.builder().s3Client(s3).context(getApplicationContext()).build();
//        transferUtility.upload("guest-images2","AKIAJJQWPYRX55SXFHUA",new File("C:\\Aashrav\\15826065_383387015336159_757500444960290063_n.jpg"));

//
//        TransferUtility transferUtility =
//                TransferUtility.builder()
//                        .context(getApplicationContext())
//                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
//                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
//                        .build();


        Intent callCameraApplicationIntent = new Intent();
        callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(callCameraApplicationIntent, ACTIVITY_START_CAMERA_APP);
    }
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        if(requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK){
            Toast.makeText(this, "Picture Taken", Toast.LENGTH_SHORT).show();
            Bundle extras =data.getExtras();
            Bitmap photoCapturedBitmap = (Bitmap) extras.get("data");
            mPhotoCapturedImageView.setImageBitmap(photoCapturedBitmap);

            File filesDir = getApplicationContext().getFilesDir();
            File imageFile = new File(filesDir,  "Test.jpg");

            OutputStream os;
            try {
                os = new FileOutputStream(imageFile);
                photoCapturedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.flush();
                os.close();
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                Toast.makeText(getBaseContext(), "FK" , Toast.LENGTH_SHORT ).show();
            }

            Toast.makeText(getBaseContext(), "DONE" , Toast.LENGTH_SHORT ).show();
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(Upload.this, name.getText(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(Upload.this, mPhotoCapturedImageView.toString(), Toast.LENGTH_SHORT).show();


                            if(name.getText().equals("")){
                                Toast.makeText(Upload.this, "ENTER NAME", Toast.LENGTH_SHORT).show();
                            }else {
                                mPhotoCapturedImageView.setDrawingCacheEnabled(true);
                                mPhotoCapturedImageView.buildDrawingCache();
                                Bitmap bitmap = ((BitmapDrawable) mPhotoCapturedImageView.getDrawable()).getBitmap();


                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] data = baos.toByteArray();
//                            Toast.makeText(Upload.this, data.toString(), Toast.LENGTH_SHORT).show();

                                UploadTask uploadTask = storage.getReference("newGuest.jpg").putBytes(data);

//                            UploadTask uploadTask = storage.getReference().child("newGuest.jpg").putFile(getImageUri(Upload.this,bitmap),metadata);

                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle unsuccessful uploads
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                        Toast.makeText(Upload.this, "Hi", Toast.LENGTH_SHORT).show();
                                        myRef.setValue(name.getText().toString());
                                        Toast.makeText(Upload.this, "Success", Toast.LENGTH_SHORT).show();

                                    }

                                });
                            }


                        }
                    });


                }
            });

//            TransferUtility transferUtility =
//                    TransferUtility.builder()
//                            .context(getApplicationContext())
//                            .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
//                            .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
//                            .build();

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, null, null);
        return Uri.parse(path);
    }

}
