package com.example.ble_project8;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class SubActivity extends AppCompatActivity {
    ImageView load;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subactivity_main);
        load=(ImageView)findViewById(R.id.iv_profile);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference forestRef = storageReference.child("1.jpg");
        forestRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(SubActivity.this).load(uri).into(load);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        StorageReference islandRef = storageReference.child("1.jpg");
        File localFile = null;
        try {
            localFile = File.createTempFile("images", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        forestRef.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata) {
                // Metadata now contains the metadata for '1.jpg'
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(storageMetadata.getCreationTimeMillis());

                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);

                TextView tv_year = (TextView) findViewById(R.id.tv_year);
                TextView tv_month = (TextView) findViewById(R.id.tv_month);
                TextView tv_day_of_month = (TextView) findViewById(R.id.tv_day_of_month);
                TextView tv_hour = (TextView) findViewById(R.id.tv_hour);
                TextView tv_minute = (TextView) findViewById(R.id.tv_minute);

                tv_year.setText("년도: " + mYear);
                tv_month.setText("월: " + mMonth);
                tv_day_of_month.setText("날짜: " + mDay);
                tv_hour.setText("시: " + hour);
                tv_minute.setText("분: " + min);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Uh - oh, an error occurred!
            }
        });
    }
}
