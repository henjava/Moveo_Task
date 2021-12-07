package com.example.moveo_project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moveo_project.db.Note;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.MYViewHolder>
{

    private Context context;
    private List<Note> noteList;
    public NoteListAdapter(Context context){
       this.context = context;
    }

    public void setNoteList(List<Note> noteList){
        this.noteList = noteList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteListAdapter.MYViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recyciler , parent , false);
        return new MYViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull NoteListAdapter.MYViewHolder holder, int position) {
       holder.tvTitle.setText(this.noteList.get(position).noteTitle);
       holder.tvBody.setText(this.noteList.get(position).notebody);
       holder.tvDate.setText(this.noteList.get(position).date);
       holder.tvidd.setText(Integer.toString(this.noteList.get(position).uid));


       Note notePosition = noteList.get(position);
    }

    @Override
    public int getItemCount() {
        return this.noteList.size();
    }


    public class MYViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvTitle;
        TextView tvBody;
        TextView tvDate;
        TextView tvidd;
        Button editButton;

       public MYViewHolder(View view) {
           super(view);
           view.setOnClickListener(this);
            tvTitle = view.findViewById(R.id.tvnotetitle);
            tvBody = view.findViewById(R.id.tvnotebody);
            tvDate = view.findViewById(R.id.tvnotedate);
            tvidd = view.findViewById(R.id.tvid);
       }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "position = " + getBindingAdapterPosition(), Toast.LENGTH_SHORT).show();
            int pos = getBindingAdapterPosition();
            Intent intent = new Intent(context, EditNoteActivity.class);
            intent.putExtra("dataTitle", tvTitle.getText());
            intent.putExtra("dataBody", tvBody.getText());
            intent.putExtra("dataDate", tvDate.getText());
            intent.putExtra("dataid", tvidd.getText());
            intent.putExtra("position", pos);
            context.startActivity(intent);
        }
        }
    }

