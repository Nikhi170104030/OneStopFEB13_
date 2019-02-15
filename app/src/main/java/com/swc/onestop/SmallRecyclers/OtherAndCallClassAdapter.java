package com.swc.onestop.SmallRecyclers;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.swc.onestop.R;

import java.util.List;

public class OtherAndCallClassAdapter extends RecyclerView.Adapter<OtherAndCallClassAdapter.MyViewHolder> {
    private  List<OtherAndCallClass> mDataset;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public  class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView title,subhead;
        private Button other,call;
        public MyViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.titleOfCon);
            subhead = v.findViewById(R.id.subheadOfCon);
            other = v.findViewById(R.id.other_button);
            call = v.findViewById(R.id.callToCon);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public OtherAndCallClassAdapter(List<OtherAndCallClass> myDataset , Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        // create a new vie
        View v =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.other_and_call, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.title.setText(mDataset.get(position).getTitle());
        holder.subhead.setText(mDataset.get(position).getSubhead());
        //holder.d.setImageDrawable(mDataset.get(position).getD());
        holder.other.setText((mDataset.get(position).getTypeOfData() == 2) ? "Web":"Mail");

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+mDataset.get(position).getPhoneNumber()));

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(context,"Permission Not Granted",Toast.LENGTH_LONG).show();
                }
                else
                    context.startActivity(callIntent);
            }
        });

        if(mDataset.get(position).getTypeOfData() == 1){
            holder.other.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //access website
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("web", holder.other.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(context, "Website Copied To Clipboard.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            holder.other.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //access mail
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("mail", holder.other.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(context, "Mail Copied To Clipboard.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}

