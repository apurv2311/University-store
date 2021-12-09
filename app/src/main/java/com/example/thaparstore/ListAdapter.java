package com.example.thaparstore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.LinkedList;

public class ListAdapter extends FirebaseRecyclerAdapter<
        Get_add, ListAdapter.NumberViewHolder> {
    private RecyclerView mRecycle;
    private ProgressBar mBar;

    public ListAdapter( FirebaseRecyclerOptions<Get_add> options) {
        super(options);
    }

    @NonNull
    @Override
    public NumberViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        Log.d("qwerty", "pp");
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Toast.makeText(v.getContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });
        mRecycle=view.findViewById(R.id.recycler_view);
        mBar=view.findViewById(R.id.progress_bar);



        return new ListAdapter.NumberViewHolder(view);
    }

    @Override
    public void onDataChanged() {
        mRecycle.setVisibility(View.VISIBLE);
        mBar.setVisibility(View.GONE);
        super.onDataChanged();
    }

    @Override
    protected void onBindViewHolder(@NonNull NumberViewHolder holder, int position, @NonNull Get_add model)
    {
        holder.mName.setText("Item: "+model.getName());

        //Toast.makeText(ListAdapter.this, "here", Toast.LENGTH_SHORT).show();
        // Add lastname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.mItem.setText("Details: "+model.getDetail());


        // Add age from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.mPrice.setText("Price: "+model.getPrice());

        String url=model.getImage_url();
        if(url.equals(""))
        {
            holder.mPhoto.setImageResource(R.drawable.default_image);
            Bitmap bitmap=((BitmapDrawable)holder.mPhoto.getDrawable()).getBitmap();
            Bitmap new2=Bitmap.createScaledBitmap(bitmap,100,100,true);
            holder.mPhoto.setImageBitmap(new2);
        }
        else
        {
            Glide.with(holder.mPhoto.getContext())
                    .load(url)
                    .apply(new RequestOptions().override(100, 100))
                    .into(holder.mPhoto);
        }


    }
    class NumberViewHolder extends RecyclerView.ViewHolder
    {
        public final TextView mName;
        public final TextView mItem;
        public final TextView mPrice;
        public final ImageView mPhoto;
        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = (TextView)itemView.findViewById(R.id.cust_name);
            mItem = (TextView)itemView.findViewById(R.id.item_name);
            mPrice = (TextView)itemView.findViewById(R.id.item_price);
            mPhoto=(ImageView)itemView.findViewById(R.id.image);
        }
    }
}
