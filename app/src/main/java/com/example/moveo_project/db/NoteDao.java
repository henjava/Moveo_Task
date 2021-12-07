package com.example.moveo_project.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface NoteDao
{
   @Query("SELECT * FROM Note WHERE email =:email  Order by date DESC ")
   List<Note> getAllNote(String email);

    @Query("SELECT * FROM Note WHERE uid =:id")
    Note getNote(String id);

    @Insert
    void insertNote(Note... notes);

    @Update
    public void updateNote(Note note);

    @Delete
    void delete(Note note);

    @Query("UPDATE Note SET body=:body, title =:title WHERE uid =:uid ")
    int updateNote(String uid, String body , String title);

    @Query("DELETE FROM Note where uid =:uid ")
    int deleteNote(String uid);

    @Query("SELECT lat FROM Note WHERE uid =:id")
    double getLat(String id);

    @Query("SELECT lon FROM Note WHERE uid =:id")
    double  getLon(String id);
}
