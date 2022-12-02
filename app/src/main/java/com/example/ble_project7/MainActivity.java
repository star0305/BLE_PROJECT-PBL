package com.example.ble_project7;

import static java.sql.DriverManager.println;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;



public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseStorage storage = FirebaseStorage.getInstance(); //Cloud Storage를 사용할 준비가 되었다.
        ImageView load;
        //create a sotrage reference from our app
        StorageReference storageReference = storage.getReference();

        //Get reference to the file
        StorageReference forestRef = storageReference.child("1.jpg");
        load = (ImageView)findViewById(R.id.iv_profile);
        forestRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(MainActivity.this).load(uri).into(load);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

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