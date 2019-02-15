package com.swc.onestop.MainActivity_Models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.squareup.picasso.Picasso;
import com.swc.onestop.Activities.SessionManager;
import com.swc.onestop.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>  {

    private ArrayList<Data_model> dataSet;
    private Context context;
    static FirebaseFirestore db;
    SessionManager sessionManager;
    CustomAdapter_fav adapter_fav;



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewtitle;
        TextView textViewSubtitle;
        TextView textViewinfo;
        ImageView imageViewdesc;
        CircleImageView imageViewdp;
        ImageView save;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.imageViewdp = (CircleImageView) itemView.findViewById(R.id.dp);
            this.textViewtitle = (TextView) itemView.findViewById(R.id.title_name);
            this.textViewSubtitle = (TextView) itemView.findViewById(R.id.subtitle);
            this.textViewinfo = (TextView) itemView.findViewById(R.id.info);
            this.imageViewdesc = (ImageView)itemView.findViewById(R.id.image_desc);
            this.save = (ImageView)itemView.findViewById(R.id.dp2);
            Log.d("CustomAdapter","FindviewbyId Done");


        }
    }

    public CustomAdapter(ArrayList<Data_model> data, Context context) {
        this.dataSet = data;
        this.context = context;
        Log.d("CustomAdapter",dataSet.get(0).getTitle());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);

        db = FirebaseFirestore.getInstance();
        sessionManager = new SessionManager(parent.getContext());

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {


        TextView textViewtitle = holder.textViewtitle;
        TextView textViewSubtitle = holder.textViewSubtitle;
        TextView textViewinfo = holder.textViewinfo;
        ImageView imageViewdesc = holder.imageViewdesc;
        ImageView imageViewdp= holder.imageViewdp;
        ImageView save = holder.save;

        if(!new SessionManager(context).isLoggedIn())
            save.setVisibility(View.INVISIBLE);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Testing CLick", Toast.LENGTH_SHORT).show();

                    Toast.makeText(holder.save.getContext(),"Added to Favourites",Toast.LENGTH_SHORT).show();
                    db.collection("feed").document(dataSet.get(listPosition).getID()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {

                                    db.collection("users").document(sessionManager.getUserDetails().get("id"))
                                            .collection("fav").document(dataSet.get(listPosition).getID())
                                            .set(document.getData());
                                    Toast.makeText(holder.save.getContext(), "Post Saved", Toast.LENGTH_SHORT).show();
                                } else {
//                                Log.d(TAG, "No such document");
                                }
                            } else {
//                            Log.d(TAG, "get failed with ", task.getException());
                            }

                        }
                    });
                notifyDataSetChanged();

//                else {
//                    //fav.setEnabled(false);
//                    db.collection("feed").document(dataSet.get(listPosition).getID()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                            if (task.isSuccessful()) {
//                                DocumentSnapshot document = task.getResult();
//                                if (document.exists()) {
//
//                                    db.collection("users").document(sessionManager.getUserDetails().get("id"))
//                                            .collection("fav").document(dataSet.get(listPosition).getID()).delete();
//
//                                    //Toast.makeText(holder.likeButton.getContext(), "Added to your Favourites", Toast.LENGTH_SHORT).show();
//                                } else {
////                                Log.d(TAG, "No such document");
//                                }
//                            } else {
////                            Log.d(TAG, "get failed with ", task.getException());
//                            }
//
//                        }
//                    });
//
//                }

            }
        });

        textViewtitle.setText(dataSet.get(listPosition).getTitle());
        textViewSubtitle.setText(dataSet.get(listPosition).getSubtitle());
        textViewinfo.setText(dataSet.get(listPosition).getDesc());

        String dpurl = dataSet.get(listPosition).getdp();
        String imageurl = dataSet.get(listPosition).getImage();

        Log.d("url", String.valueOf(dpurl.isEmpty()));

       if(!dpurl.isEmpty())
       {

           Picasso.get().load(dpurl).into(imageViewdp);
       }
       if(!imageurl.isEmpty())
       {

           Picasso.get().load(imageurl).into(imageViewdesc);
       }


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}