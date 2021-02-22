package com.example.instagramclone.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagramclone.FollowersActivity;
import com.example.instagramclone.FrameAll.ProfileFragment;
import com.example.instagramclone.Model.User;
import com.example.instagramclone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContect;
    private List<User> mUser;
    private FirebaseUser firebaseUser;

    public UserAdapter(Context mContect, List<User> mUser) {
        this.mContect = mContect;
        this.mUser = mUser;
    }

    /*public UserAdapter(FollowersActivity mContect, List<User> userList) {
    }

    public UserAdapter(FollowersActivity followersActivity, List<User> userList) {
    }

    public UserAdapter(FollowersActivity followersActivity, List<User> userList) {
    }*/

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContect).inflate(R.layout.user_item,parent,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        final User user=mUser.get(i);
        holder.btn_follow_item.setVisibility(View.VISIBLE);
        holder.username_item.setText(user.getUsername());
        holder.txt_name_item.setText(user.getAd());
        Glide.with(mContect).load(user.getResimurl()).into(holder.profile_image_item);
        isFollowing(user.getId(),holder.btn_follow_item);

        if (user.getId().equals(firebaseUser.getUid())){

            holder.btn_follow_item.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=mContect.getSharedPreferences("PRESS",Context.MODE_PRIVATE).edit();
                editor.putString("profileid",user.getId());
                editor.apply();


                ((FragmentActivity)mContect).getSupportFragmentManager().beginTransaction().replace(R.id.frameall,
                        new ProfileFragment()).commit();
            }
        });

        holder.btn_follow_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.btn_follow_item.getText().toString().equals("follow"))
                {
                    FirebaseDatabase.getInstance().getReference().child("follow").child(firebaseUser.getUid())
                            .child("following").child(user.getId()).setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("follow").child(firebaseUser.getUid())
                            .child("followers").child(user.getId()).setValue(true);
                }
                else {
                    FirebaseDatabase.getInstance().getReference().child("follow").child(firebaseUser.getUid())
                            .child("following").child(user.getId()).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("follow").child(firebaseUser.getUid())
                            .child("followers").child(user.getId()).removeValue();



                }
            }
        });

    }

    @Override
    public int getItemCount() {

        return mUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView username_item;
        public TextView txt_name_item;
        public Button btn_follow_item;
        public CircleImageView profile_image_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username_item=itemView.findViewById(R.id.username_item);
            txt_name_item=itemView.findViewById(R.id.txt_name_item);
            btn_follow_item=itemView.findViewById(R.id.btn_follow_item);
            profile_image_item=itemView.findViewById(R.id.profile_image_item);

        }
    }
    private void isFollowing(String userid,Button button){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(firebaseUser.getUid()).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(userid).exists()){
                    button.setText("following");
                }else {
                    button.setText("follow");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}
