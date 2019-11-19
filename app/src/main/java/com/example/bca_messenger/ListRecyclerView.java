package com.example.bca_messenger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListRecyclerView extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private listAdapter mAdapter;
    private  RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<listModel> mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recycler_view);
        ArrayList<listModel> listModels11 = new ArrayList<>();
        listModels11.add(new listModel(R.drawable.ic_camera_alt_black_24dp, "BCA 1st year"));
        listModels11.add(new listModel(R.drawable.ic_menu_gallery, "BCA 2nd year"));
        listModels11.add(new listModel(R.drawable.ic_person_black_24dp, "BCA 3rd year"));
        listModels11.add(new listModel(R.drawable.ic_menu_send, "BCA Faculty"));
        mRecyclerView=(RecyclerView)findViewById(R.id.mrecyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter= new listAdapter(this, listModels11);
        mRecyclerView.setAdapter(mAdapter);
    }
}
