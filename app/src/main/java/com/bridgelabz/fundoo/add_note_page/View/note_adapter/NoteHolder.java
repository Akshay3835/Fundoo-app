package com.bridgelabz.fundoo.add_note_page.View.note_adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bridgelabz.fundoo.R;
import com.bridgelabz.fundoo.add_note_page.Model.NoteResponseModel;

public class NoteHolder extends RecyclerView.ViewHolder  {

    private static final String TAG = "NoteHolder";
    public TextView mTitle, mDescription;
    public TextView mReminder;
    private CardView noteCard;
    private NotesAdapter.OnClickListener listener;
    private CardView reminderCard;


    public NoteHolder(@NonNull View itemView, NotesAdapter.OnClickListener onItemClickListener) {
        super(itemView);
        this.listener = onItemClickListener;
        mTitle = itemView.findViewById(R.id.tv_title);
        mDescription = itemView.findViewById(R.id.tv_description);
        mReminder = itemView.findViewById(R.id.tv_reminder);
        reminderCard = itemView.findViewById(R.id.reminderItemCard);
        noteCard = itemView.findViewById(R.id.noteItemCard);
        this.listener =onItemClickListener;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if(listener != null)
                {
                    listener.onItemClick(position);
                }
                else
                {
                    Log.e(TAG,"listener is null");
                }
            }
        });
    }

    public void bindNoteToCard(NoteResponseModel note)
    {
        mTitle.setText(note.getTitle());
        mDescription.setText(note.getDescription());

        if (!note.getReminder().isEmpty()){
            reminderCard.setCardBackgroundColor(Color.LTGRAY);

            mReminder.setText(note.getReminder().get(0));
            reminderCard.setVisibility(View.VISIBLE);
        }
        else{
            reminderCard.setVisibility(View.GONE);
        }

        if(note.getColor()!= null && !note.getColor().isEmpty()) {
            noteCard.setCardBackgroundColor(Color.parseColor(note.getColor()));
        }
        else
        {
            noteCard.setCardBackgroundColor(Color.WHITE);
        }
    }


}
