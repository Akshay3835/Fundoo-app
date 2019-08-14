package com.bridgelabz.fundoo.add_label_page.view.label_adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bridgelabz.fundoo.R;
import com.bridgelabz.fundoo.add_label_page.model.Label;

public class LabelHolder extends RecyclerView.ViewHolder {
    private LabelAdapter.OnItemClickListener listener;
    private TextView mLabel;

    public LabelHolder(@NonNull View itemView, LabelAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        mLabel = itemView.findViewById(R.id.tv_label_item_view);
        this.listener = onItemClickListener;
    }

    public void bindNoteToCard(Label label) {
        mLabel.setText(label.getLabelName());
    }
}
