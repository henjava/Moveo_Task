package com.example.moveo_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moveo_project.db.AppDatabase;
import com.example.moveo_project.db.Note;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;

public class AddNewNoteActivity extends AppCompatActivity {
    EditText noteDate;
    EditText noteTitle;
    EditText noteBody;
    CheckBox secureNote;
    Button editButton;
    Button camera;
    FloatingActionButton deleteBtn;
    FirebaseAuth mAuth;
    ImageView img;
    FusedLocationProviderClient fusedLocationProviderClient;

public static final int CAMERA_REQUEST = 9999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth.getInstance();
        setContentView(R.layout.activity_add_new_note);
        final EditText noteTitleInput = findViewById(R.id.add_title_edtText);
        final EditText noteBodyInput = findViewById(R.id.add_body_edtText);
        final TextView notedate = findViewById(R.id.add_date_tvText);
        img = (ImageView) findViewById(R.id.add_image);
        camera = findViewById(R.id.camera);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        notedate.setText(DateFormat.getDateTimeInstance().format(System.currentTimeMillis()));
        Button saveButton = findViewById(R.id.btnSave);
        saveButton.setOnClickListener(view -> saveNewNote(noteTitleInput.getText().toString(), noteBodyInput.getText().toString(), mAuth.getInstance().getCurrentUser().getEmail()));
        Button backButton = findViewById(R.id.btnBack);
        backButton.setOnClickListener(view -> {
            startActivity(new Intent(AddNewNoteActivity.this, MainActivity.class));
        });
            if(ContextCompat.checkSelfPermission(AddNewNoteActivity.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(AddNewNoteActivity.this,
                        new String[]{
                                Manifest.permission.CAMERA
                        },
                        100);

            }
            camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                  startActivityForResult(intent , 100);
                }
            });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100)
        {
        Bitmap bit = (Bitmap) data.getExtras().get("data");
        img.setImageBitmap(bit);
    }

}


private void saveNewNote(String noteTitle, String noteBody, String email) {
    Note note = new Note();
    AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
    if (ActivityCompat.checkSelfPermission(AddNewNoteActivity.this
            , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    note.lat = (location.getLatitude());
                    note.lon = (location.getLongitude());
                    note.noteTitle = noteTitle;
                    note.notebody = noteBody;
                    note.email = email;
                    note.date = DateFormat.getDateTimeInstance().format(System.currentTimeMillis());
                    db.noteDao().insertNote(note);
                }

            }

        });
    }
    startActivity(new Intent(AddNewNoteActivity.this, MainActivity.class));
}

}


