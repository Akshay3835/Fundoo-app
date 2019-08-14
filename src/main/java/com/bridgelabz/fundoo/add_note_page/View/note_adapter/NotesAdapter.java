package com.bridgelabz.fundoo.add_note_page.View.note_adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bridgelabz.fundoo.R;
import com.bridgelabz.fundoo.add_note_page.Model.BaseNoteModel;
import com.bridgelabz.fundoo.add_note_page.Model.NoteResponseModel;
import com.bridgelabz.fundoo.utility.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NoteHolder> implements ItemTouchHelperAdapter, Filterable {
    private static final String TAG = NotesAdapter.class.getName();


    private List<NoteResponseModel> noteModelArrayList;
    private List<NoteResponseModel> noteArrayListFull;
    private ItemTouchHelper itemTouchHelper;
    private OnClickListener listener;


    public NotesAdapter(List<NoteResponseModel> noteModelArrayList, OnClickListener listener) {
        Log.e(TAG, "NotesAdapter: " + noteModelArrayList.toString());

        this.noteModelArrayList = noteModelArrayList;
        this.listener = listener;
        noteArrayListFull = new ArrayList<>(noteModelArrayList);


    }


    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_itemview, parent,
                false);
        final NoteHolder mNoteHolder = new NoteHolder(view, listener);

        return mNoteHolder;
    }

    @Override
    public void onBindViewHolder(NoteHolder noteHolder, int position) {
        NoteResponseModel currentItem = noteModelArrayList.get(position);
        noteHolder.bindNoteToCard(currentItem);
    }


    @Override
    public int getItemCount() {
        return noteModelArrayList.size();
    }

    public NoteResponseModel getNoteAt(int position) {
        return noteModelArrayList.get(position);
    }

    public void onItemMove(int draggedPosition, int targetPosition) {
        NoteResponseModel draggedNote = noteModelArrayList.get(draggedPosition);
        noteModelArrayList.remove(draggedNote);
        noteModelArrayList.add(targetPosition, draggedNote);
        notifyItemMoved(draggedPosition, targetPosition);
    }

    public void onItemSwiped(int position) {
        noteModelArrayList.remove(position);
        notifyItemRemoved(position);
    }

    public void setTouchHelper(ItemTouchHelper touchHelper) {
        this.itemTouchHelper = touchHelper;
    }

    public void setNoteModelArrayList(List<NoteResponseModel> noteModelArrayList) {
        this.noteModelArrayList = noteModelArrayList;

    }


    // Interface for ItemClickListener
    public interface OnClickListener {
        void onItemClick(int position);

    }

    @Override
    public Filter getFilter() {
        return noteFilter;
    }

    public Filter noteFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<BaseNoteModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(noteArrayListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (BaseNoteModel item : noteArrayListFull) {
                    if (item.getDescription().contains(filterPattern)) {
                        filteredList.add(item);
                    }

                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            Log.e("NotesAdapter", "filtering");
            noteModelArrayList.clear();
            noteModelArrayList.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };


}

