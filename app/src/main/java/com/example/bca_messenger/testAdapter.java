package com.example.bca_messenger;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class testAdapter extends RecyclerView.Adapter<testAdapter.ViewHolder> {
    private List<testModel> chatList;

    public testAdapter(List<testModel> chatList) {
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public testAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull testAdapter.ViewHolder viewHolder, int position) {
        String username=chatList.get(position).getUsername();
        String usermail=chatList.get(position).getUsermail();
        String usermessage=chatList.get(position).getMessage();
        String currdate=chatList.get(position).getDate();
        String currtime=chatList.get(position).getTime();
        int resource=chatList.get(position).getImageResource();

        viewHolder.setName(username);
        viewHolder.setEmail(usermail);
        viewHolder.setMessage(usermessage);
        viewHolder.setTime(currtime);
        viewHolder.setDate(currdate);
        viewHolder.setUserPic(resource);

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView usr_name,usr_mail,usr_msg,curr_time,curr_date;
        private CircleImageView usr_pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usr_name=itemView.findViewById(R.id.textView_name);
            usr_mail=itemView.findViewById(R.id.textView_email);
            usr_msg=itemView.findViewById(R.id.textView_message);
            curr_time=itemView.findViewById(R.id.textView_time);
            curr_date=itemView.findViewById(R.id.textView_date);
           usr_pic=itemView.findViewById(R.id.circleImageView);
        }
        private void setName(String name){
            usr_name.setText(name);
        }
        private void setEmail(String email){
            usr_mail.setText(email);
        }
        private void setMessage(String message){
            usr_msg.setText(message);
        }
        private void setTime(String time){
            curr_time.setText(time);
        }
        private void setDate(String date){
            curr_date.setText(date);
        }
        private void setUserPic(int resource){
            usr_pic.setImageResource(resource);
        }
    }
}
