package com.example.moveo_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moveo_project.db.AppDatabase;
import com.example.moveo_project.db.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NoteListAdapter noteListAdapter;
    FirebaseAuth mAuth;
    Button btLogOut ;
    Button mapNote;
    FloatingActionButton btnAddNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (getIntent().getExtras() != null)
        {
            if (getIntent().getExtras().getString("type").equals("update")) {
                AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
                
                double lat  =  db.noteDao().getLat(getIntent().getExtras().getString("id"));
                double lon  =  db.noteDao().getLon(getIntent().getExtras().getString("id"));
                
                Note note = new Note();
                
                note.setNoteTitle(getIntent().getExtras().getString("new title"));
                note.setNotebody(getIntent().getExtras().getString("new body"));
                note.setDate(getIntent().getExtras().getString("new date"));
                note.setUid(Integer.parseInt(getIntent().getExtras().getString("id")));
                note.setLat(lat);
                note.setLon(lon);
                updateNote(note);
            }
            else if(getIntent().getExtras().getString("type").equals("delete"))
            {
                deleteNote(getIntent().getExtras().getString("id"));
            }
        }


        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        String mail = mAuth.getCurrentUser().getEmail();
        setContentView(R.layout.activity_main);
        btLogOut = findViewById(R.id.main_log_out_btn);
        mapNote = findViewById(R.id.btnMapNote);

        mapNote.setOnClickListener(view -> {
            AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
            List<Note> noteList = new ArrayList<>();
            noteList =  db.noteDao().getAllNote(mAuth.getCurrentUser().getEmail());

            Intent intent = new Intent(MainActivity.this,MapActivity.class);
            ArrayList<Note> ex = new ArrayList<Note>();
            ex.addAll(noteList);
            intent.putExtra("data",ex);
            startActivity(new Intent(MainActivity.this, MapActivity.class));

        });

        btnAddNote = findViewById(R.id.btnAddNote);
        btLogOut.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });


        btnAddNote.setOnClickListener(view -> startActivityForResult(new Intent(MainActivity.this, AddNewNoteActivity.class), 100));
        initRecyclerView();
        loadNoteList();
    }

    private void initRecyclerView(){

        RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration divierItemDecoration = new DividerItemDecoration(this , DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divierItemDecoration);
        noteListAdapter =new NoteListAdapter(this);
        recyclerView.setAdapter(noteListAdapter);
    }

 private void loadNoteList(){
     AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
     List<Note> noteList = db.noteDao().getAllNote(mAuth.getCurrentUser().getEmail());
     if (noteList.size() == 0)
     {
         Toast.makeText(MainActivity.this, "no notes  Sent", Toast.LENGTH_SHORT).show();
     }
     noteListAdapter.setNoteList(noteList);
 }

    public void updateNote(Note note){
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        db.noteDao().updateNote(Integer.toString(note.getUid()),note.getNotebody(), note.getNoteTitle());
    }

    public void deleteNote(String id){
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        db.noteDao().deleteNote(id);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 100) {
            loadNoteList();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}