package com.example.glowup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Review_Adapter extends RecyclerView.Adapter<Review_Adapter.MyViewHolder> {

    Context context;
    ArrayList<Review_Model> reviewList;

    public Review_Adapter(Context context, ArrayList<Review_Model> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public Review_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_cardview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Review_Adapter.MyViewHolder holder, int position) {
        Review_Model review_model = reviewList.get(position);
        holder.name.setText(review_model.getUserName());
        holder.review.setText(review_model.getReview());

        // Load images using Picasso
        Picasso.get().load(review_model.getBefore()).into(holder.before);
        Picasso.get().load(review_model.getAfter()).into(holder.after);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, review;
        ImageView before, after;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.entername);
            review = itemView.findViewById(R.id.enterreview);
            before = itemView.findViewById(R.id.imageView4);
            after = itemView.findViewById(R.id.imageView5);
        }
    }
}
