package com.example.moveo_project.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class},version =  1)
public abstract class AppDatabase extends RoomDatabase
{
    public  abstract NoteDao noteDao();
    private static  AppDatabase INSTACE;
    public static AppDatabase getDbInstance(Context context)
    {
        if(INSTACE == null)
        {
          INSTACE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class , "DB_NOTE16").allowMainThreadQueries().build();
        }

        return INSTACE;
    }

}
