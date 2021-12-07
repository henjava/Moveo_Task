package com.example.moveo_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.moveo_project.db.AppDatabase;
import com.example.moveo_project.db.Note;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class EditNoteActivity extends Activity {
    Button saveEditNote;
    EditText editTitle;
    EditText editBody;
    TextView date;
    FirebaseAuth mAuth;
    Button DeleteNote;
    Button backbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupedit);
        editBody = findViewById(R.id.add_body_edtText);
        editTitle = findViewById(R.id.add_title_edtText);
        date = findViewById(R.id.add_date_tvText);
        saveEditNote = findViewById(R.id.btnSave);
        DeleteNote = findViewById(R.id.btnDelete);
        backbtn = findViewById(R.id.btnBack);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditNoteActivity.this, MapActivity.class);
            startActivity(intent);
            }
        });


        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getString("editid") != null) {
                String id = getIntent().getExtras().getString("editid");
                AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
                Note note = db.noteDao().getNote(id);
                editBody.setText(note.notebody);
                editTitle.setText(note.noteTitle);
                date.setText(note.date);
            }

        else {
                final EditText editTitle = (EditText) findViewById(R.id.add_title_edtText);
                final EditText editBody = (EditText) findViewById(R.id.add_body_edtText);
                final TextView viewDate = (TextView) findViewById(R.id.add_date_tvText);
                final String title = getIntent().getExtras().getString("dataTitle");
                final String body = getIntent().getExtras().getString("dataBody");
                final String date = getIntent().getExtras().getString("dataDate");
                editTitle.setText(title);
                editBody.setText(body);
                viewDate.setText(date);
            }
        }
        final NoteListAdapter list = new NoteListAdapter(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        DeleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditNoteActivity.this, MainActivity.class);
                intent.putExtra("id", getIntent().getExtras().getString("dataid"));
                intent.putExtra("type", "delete");
                startActivity(intent);
            }
        });
        saveEditNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int  id;
                String body = editBody.getText().toString();
                String title = editTitle.getText().toString();
                if(getIntent().getExtras().getString("dataid") == null)
                {
                      id = Integer.parseInt(getIntent().getExtras().getString("editid"));
                }
               else{
                      id = Integer.parseInt(getIntent().getExtras().getString("dataid"));
                }
                String date1 = date.getText().toString();

                Note note = new Note();
                note.setUid(id);
                note.setNoteTitle(title);
                note.setNotebody(body);
                note.setDate(date1);

                final String position = getIntent().getExtras().getString("position");
                Intent intent = new Intent(EditNoteActivity.this, MainActivity.class);
                intent.putExtra("new title", editTitle.getText().toString());
                intent.putExtra("new body", editBody.getText().toString());
                intent.putExtra("new date", date.getText().toString());
                intent.putExtra("id", String.valueOf(id));
                intent.putExtra("type", "update");

                startActivity(intent);


            }
        });



    }

}
