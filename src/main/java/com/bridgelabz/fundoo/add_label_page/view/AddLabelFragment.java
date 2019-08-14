package com.bridgelabz.fundoo.add_label_page.view;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bridgelabz.fundoo.add_label_page.model.Label;
import com.bridgelabz.fundoo.add_label_page.view.label_adapter.LabelAdapter;
import com.bridgelabz.fundoo.Dashboard.DashboardActivity;
import com.bridgelabz.fundoo.add_label_page.view_model.LabelViewModel;
import com.bridgelabz.fundoo.R;

import java.util.List;


public class AddLabelFragment extends Fragment
{
    LabelViewModel labelViewModel;
    private EditText mTextLabelName;
    private List<Label> labelList;
    private RecyclerView mRecyclerView;

    ImageButton mButtonAdd;
    ImageButton mButtonnDelete;
     Label labelToEdit;
    private LabelAdapter labelAdapter;
    private boolean isLabelEdit;
    private static   String TAG;


    public AddLabelFragment()
    {

        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findLabels();
        initRecycleView();
        setOnClickAdd();
        setOnClickDeleted();
        mTextLabelName = (EditText) getView().findViewById(R.id.et_label);

    }

    private void setOnClickDeleted()
    {
        mButtonnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                    labelViewModel.deleteAllLabels();
                    labelList.clear();
                    labelAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "All Labels Deleted", Toast.LENGTH_SHORT).show();
                    Log.e(TAG,"items in labelList is" + labelList.size());

                }

            });


    }

    private void setOnClickAdd()
    {
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String labelName = mTextLabelName.getText().toString().trim();


                if (labelName.isEmpty() )
                {
                    Toast.makeText(getContext(), " Enter label name", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Label label = new Label(labelName);
                    addLabelToDB(label);
                    labelList.add(label);
                    labelAdapter.notifyDataSetChanged();
                    mTextLabelName.getText().clear();
//                    updateLabelToDB(labelToEdit);
                }
            }
        });
    }

    private void initRecycleView()
    {
        labelList = labelViewModel.getAllLabel();
        mRecyclerView = getView().findViewById(R.id.frag_recycle);
        for (Label label : labelList) {
            System.out.println(label);
        }

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        labelAdapter = new LabelAdapter(
                labelList, new LabelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int labelPosition) {

            }
        });

        mRecyclerView.setAdapter(labelAdapter);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        labelViewModel = new LabelViewModel(getContext());
        View v=  inflater.inflate(R.layout.fragment_add_label, container, false);
        return v;
    }





    private void addLabelToDB(Label label)
    {
        boolean val = labelViewModel.addLabel(label);
        if (val) {
            Toast.makeText(getContext(), "Label  is successfully Saved",
                    Toast.LENGTH_SHORT).show();
            Log.e(TAG,toString());
        }
    }






    private void updateLabelToDB(Label labelToEdit)
    {
        boolean isLabelEdit = labelViewModel.updateLabel(labelToEdit);
        if (isLabelEdit)
        {
            Toast.makeText(getContext(), " Label is Updated", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), DashboardActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Unable to update Label", Toast.LENGTH_SHORT).show();
        }
    }



    private void findLabels()
    {
        mTextLabelName = getView().findViewById(R.id.et_label);
        mButtonAdd = getView().findViewById(R.id.btn_add);
        mButtonnDelete = getView().findViewById(R.id.btn_delete);
    }


}
