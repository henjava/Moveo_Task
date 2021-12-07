package com.example.moveo_project.db;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.ColorInt;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
@SuppressWarnings("serial")
public class Note implements Serializable {


    @PrimaryKey(autoGenerate = true)
   public  int uid;

    @ColumnInfo(name = "title")
   public  String noteTitle;

   @ColumnInfo(name = "body")
   public String notebody;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "email")
    public String email;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    @ColumnInfo(name = "lat")
    public Double lat;

    @ColumnInfo(name = "lon")
    public Double lon;




    public Note(String title, String body, String date , String email) {
        this.noteTitle = title;
        this.notebody = body;
        this.email = email;
        this.date = date;
    }

    public Note(Parcel in) {
        noteTitle = in.readString();
        notebody = in.readString();
        email = in.readString();
        date = in.readString();
        lon = in.readDouble();
        lat = in.readDouble();
    }

    public Note() {
    }
    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public void setNotebody(String notebody) {
        this.notebody = notebody;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public int getUid() {
        return uid;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNotebody() {
        return notebody;
    }

    public String getDate() {
        return date;
    }

    public String getEmail() {
        return email;
    }

}