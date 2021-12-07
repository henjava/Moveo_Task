package com.example.moveo_project;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.moveo_project.db.AppDatabase;
import com.example.moveo_project.db.Note;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

GoogleMap map;
FirebaseAuth mAuth;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        List<Note> list =  db.noteDao().getAllNote(mAuth.getCurrentUser().getEmail());
        markMap(list);

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                String mark = marker.getTitle();
                Intent i = new Intent(MapActivity.this, EditNoteActivity.class);
                i.putExtra("editid",mark);
                startActivity(i);
                return false;
            }
        });
    }

    public void markMap(List <Note> li) {
        for (Note var : li) {
            MarkerOptions marker = new MarkerOptions().position(new LatLng(var.getLat(), var.getLon())).title(String.valueOf(var.uid));
            map.addMarker(marker);
            map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(var.getLat(), var.getLon())));
        }

    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}