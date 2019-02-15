package com.swc.onestop.Activities.Complaints;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.swc.onestop.Activities.SessionManager;
import com.swc.onestop.MainActivity_Models.CustomAdapter_fav;
import com.swc.onestop.R;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.MyViewHolder> {
    private List<ComplaintCard> mDataset;
    private Context context;


    private SessionManager sessionManager;
    private HashMap<String, String> userDetails;

    private ComplaintAdapter adapter;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    public  class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        private TextView name,date,sub,body, id,supports,comments;
         private ImageView mySupport,personImage;
        private ImageView z;


        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.nameOfComplainer);
            date = v.findViewById(R.id.dateOfComplaint);
            personImage = v.findViewById(R.id.dp);
            mySupport = v.findViewById(R.id.youSupport);
            sub = v.findViewById(R.id.subTF);
            body = v.findViewById(R.id.msgTF);
            id=v.findViewById(R.id.id);

            z = v.findViewById(R.id.supporters_img);
//            Log.i("shivam","abcde"+id);
            supports = v.findViewById(R.id.supporters);
            comments = v.findViewById(R.id.commentsInTotal);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


//                    final String;
                    Intent i = new Intent(context,SingleComplaintActivity.class);
                    Log.i("FindviewbyIDComplaints",((TextView)view.findViewById(R.id.id)).getText().toString());
                    String id=((TextView)view.findViewById(R.id.id)).getText().toString();
                    String name = ((TextView)view.findViewById(R.id.nameOfComplainer)).getText().toString();
                    String body = ((TextView)view.findViewById(R.id.msgTF)).getText().toString();
                    String sub = ((TextView)view.findViewById(R.id.subTF)).getText().toString();
                    String supports = ((TextView)view.findViewById(R.id.supporters)).getText().toString();
                    String comments = ((TextView)view.findViewById(R.id.commentsInTotal)).getText().toString();
                    String date = ((TextView)view.findViewById(R.id.dateOfComplaint)).getText().toString();
                    ImageView support = (view.findViewById(R.id.youSupport));

                    Drawable spt_img =support.getDrawable();

                    Bitmap bmp = getBitmapFromVectorDrawable(context,spt_img);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();



                    i.putExtra("complaintID",id);
                    i.putExtra("name",name);
                    i.putExtra("body",body);
                    i.putExtra("subject",sub);
                    i.putExtra("date",date);
                    i.putExtra("supports",supports);
                    i.putExtra("commentsCount",comments);
                    i.putExtra("supported", byteArray);



//                    i.putExtra("mySupport",support);
//                    i.putExtra

//                    Log.i("shivam",id);
                    context.startActivity(i);
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ComplaintAdapter(List<ComplaintCard> myDataset , Context context) {
        mDataset = myDataset;
        this.context = context;
        this.adapter= this;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ComplaintAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new vie
        View v =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.complaint_card, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    //   the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.name.setText(mDataset.get(position).getName());
        holder.date.setText(mDataset.get(position).getDate());
        holder.sub.setText(mDataset.get(position).getSubject());
        holder.body.setText(mDataset.get(position).getBody());
        holder.id.setText(mDataset.get(position).getId());
        holder.id.setVisibility(View.INVISIBLE);
        holder.supports.setText(mDataset.get(position).getLikes());
        holder.comments.setText(mDataset.get(position).getComments());
        holder.personImage.setImageDrawable(mDataset.get(position).getImage());


        Drawable current = mDataset.get(position).isSupported();
        holder.mySupport.setImageDrawable(current);


        Drawable supported = ContextCompat.getDrawable(context.getApplicationContext(),R.drawable.ic_favorite_black_24dp);

        Drawable unsupported = ContextCompat.getDrawable(context.getApplicationContext(),R.drawable.ic_favorite_border_black_24dp);
        final Bitmap current_bitmap = getBitmapFromVectorDrawable(context,current);
       final Bitmap supported_bitmap = getBitmapFromVectorDrawable(context,supported);


        if (current_bitmap.sameAs(supported_bitmap)) {
            //remove support
          //  holder.mySupport.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_black_24dp));



        } else {

            //add support
          //  holder.mySupport.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_border_black_24dp));

          //  Log.i("nikhil","Unsupported setted"+mDataset.get(position).getSubject());
        }


        holder.mySupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Drawable current =holder.mySupport.getDrawable();

                Drawable supported = ContextCompat.getDrawable(context.getApplicationContext(),R.drawable.ic_favorite_black_24dp);


                final Bitmap current_bitmap = getBitmapFromVectorDrawable(context,current);
                final Bitmap supported_bitmap = getBitmapFromVectorDrawable(context,supported);

                sessionManager = new SessionManager(context);
                userDetails = sessionManager.getUserDetails();

                if (current_bitmap.sameAs(supported_bitmap)) {
                    //remove support
                    holder.mySupport.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_border_black_24dp));

                    DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Complaint").document(mDataset.get(position).getId());

                    adapter.notifyDataSetChanged();
                    documentReference.update("supports", FieldValue.arrayRemove(userDetails.get("rollNo")));
                    mDataset.get(position).setSupported( context.getDrawable(R.drawable.ic_favorite_border_black_24dp));
                    holder.supports.setText(String.valueOf(Integer.parseInt(mDataset.get(position).getLikes())-1));
                    TextView x;
                    x = holder.supports;
                    x.setText(String.valueOf(Integer.parseInt(mDataset.get(position).getLikes())-1));
                    Log.i("Likes",String.valueOf(Integer.parseInt(mDataset.get(position).getLikes())-1));
                    mDataset.get(position).setLikes(String.valueOf(Integer.parseInt(mDataset.get(position).getLikes())-1));


                } else {

                    //add support
                    holder.mySupport.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_black_24dp));
                    mDataset.get(position).setSupported( context.getDrawable(R.drawable.ic_favorite_black_24dp));
                    DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Complaint").document(mDataset.get(position).getId());

                    documentReference.update("supports", FieldValue.arrayUnion(userDetails.get("rollNo")));
                    adapter.notifyDataSetChanged();
                    holder.supports.setText(String.valueOf(Integer.parseInt(mDataset.get(position).getLikes())+1));
                    TextView x;
                    x = holder.supports;
                    x.setText(String.valueOf(Integer.parseInt(mDataset.get(position).getLikes())+1));
                    Log.i("Likes",String.valueOf(Integer.parseInt(mDataset.get(position).getLikes())+1));
                    mDataset.get(position).setLikes(String.valueOf(Integer.parseInt(mDataset.get(position).getLikes())+1));

                }


            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() { return mDataset.size(); }


    public static Bitmap getBitmapFromVectorDrawable(Context context, Drawable drawableId) {
        Drawable drawable = drawableId;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}

