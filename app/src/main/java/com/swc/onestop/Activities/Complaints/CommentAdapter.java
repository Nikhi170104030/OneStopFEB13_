package com.swc.onestop.Activities.Complaints;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swc.onestop.R;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    private List<Comment> mDataset;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public  class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView title,subhead;
        public MyViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.commentName);
            subhead = v.findViewById(R.id.commeent);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CommentAdapter(List<Comment> myDataset , Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CommentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new vie
        View v =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.comment_view, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.title.setText(mDataset.get(position).getName());
        holder.subhead.setText(mDataset.get(position).getBody());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}

