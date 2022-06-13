package com.example.andriodnotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.nvHolder> {
    private List<JsonNote> noteL;
    private MainActivity mainActivity;

    public NotesAdapter(List<JsonNote> notesList, MainActivity mainActivity) {
        this.noteL = notesList;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public nvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_view, parent, false);
        itemView.setOnClickListener(mainActivity);
        itemView.setOnLongClickListener(mainActivity);

        return new nvHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull nvHolder holder, int position) {
        JsonNote note = noteL.get(position);
        holder.text.setText(note.getDisText());
        holder.title.setText(note.getNtTitle());
        holder.ntTime.setText(note.getNtTime());
    }

    @Override
    public int getItemCount() {
        return noteL.size();
    }

    public class nvHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView text;
        TextView ntTime;

        public nvHolder(View view) {
            super(view);

            this.title = view.findViewById(R.id.listTitle);
            this.text = view.findViewById(R.id.listText);
            this.ntTime = view.findViewById(R.id.listUpdatedTime);
        }
    }
}
