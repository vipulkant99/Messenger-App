package com.example.bca_messenger;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class listAdapter extends RecyclerView.Adapter<listAdapter.viewHolder> {
    private Context context;
    private ArrayList<listModel> list;
    public listAdapter(Context context, ArrayList list){
        this.context=context;
        this.list=list;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listadapter,viewGroup,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull listAdapter.viewHolder viewHolder, int i) {
        listModel current =list.get(i);
        viewHolder.image.setImageResource(current.getImage());
        viewHolder.text.setText(current.getText());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView text;
        public ImageView image;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            text=(TextView)itemView.findViewById(R.id.text22);
            image=(ImageView)itemView.findViewById(R.id.circleImageView);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listModel item = list.get(pos);
            //Toast.makeText(context,"The postion is: "+pos,Toast.LENGTH_LONG).show();
            if (pos == 0){
                Intent intent = new Intent(context,testActivity.class);
                context.startActivity(intent);
            }
            else if (pos == 1){
                Intent intent = new Intent(context,testActivity2.class);
                context.startActivity(intent);
            }
            else if (pos == 2){
                Intent intent = new Intent(context,testActivity3.class);
                context.startActivity(intent);
            }
            else if (pos == 3){
                Intent intent = new Intent(context,testActivity4.class);
                context.startActivity(intent);
            }

        }
    }
}
