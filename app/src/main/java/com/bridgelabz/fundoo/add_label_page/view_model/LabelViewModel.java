package com.bridgelabz.fundoo.add_label_page.view_model;

import android.content.Context;

import com.bridgelabz.fundoo.add_label_page.model.Label;
import com.bridgelabz.fundoo.add_label_page.model.LabelDatabaseManager;

import java.util.List;

public class LabelViewModel
{
    private LabelDatabaseManager labelDatabaseManager;

    public LabelViewModel(Context context)
    {
        labelDatabaseManager = new LabelDatabaseManager(context);
    }

    public boolean addLabel(Label label) {
        return labelDatabaseManager.addLabel(label);
    }

    public List<Label> getAllLabel() {
        return labelDatabaseManager.getLabelData();
    }

    public boolean updateLabel(Label labelToEdit)
    {
        return labelDatabaseManager.updateLabels(labelToEdit);
    }

    public boolean deleteLabel(Label label)
    {
        return labelDatabaseManager.deleteLabel(label);
    }

    public void deleteAllLabels()
    {
        labelDatabaseManager.deleteAllLabels();
    }


}
