package com.wipro.architectureexample.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wipro.architectureexample.R;
import com.wipro.architectureexample.model.Note;

import java.util.List;

public class NoteAdaptor extends RecyclerView.Adapter <NoteAdaptor.NoteHolder> {

    private List<Note> notes ;

    public NoteAdaptor(List<Note> notes) {
        this.notes = notes;
    }


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.note_item,parent,false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = notes.get(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getDescription());
        Picasso.get().load(currentNote.getImageHref()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NoteHolder extends  RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewDescription;
        private ImageView imageView;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_desc);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}
