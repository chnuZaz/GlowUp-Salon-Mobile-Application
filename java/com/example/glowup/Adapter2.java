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

public class Adapter2 extends RecyclerView.Adapter<Adapter2.ViewHolder> {

    private final RecycleViewInterface2 recycleViewInterface2;
    Context context;
    ArrayList<Category> categoryArrayList;

    public Adapter2(Context context, ArrayList<Category> categoryArrayList, RecycleViewInterface2 recycleViewInterface2) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
        this.recycleViewInterface2 = recycleViewInterface2;
    }

    @NonNull
    @Override
    public Adapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_cardview, parent, false);
        return new Adapter2.ViewHolder(v, recycleViewInterface2);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter2.ViewHolder holder, int position) {
        Category category = categoryArrayList.get(position);
        holder.Name.setText(category.getName());
        Picasso.get().load(category.getImage()).into(holder.Image);
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView Name;
        ImageView Image;

        public ViewHolder(@NonNull View itemView, RecycleViewInterface2 recycleViewInterface2) {
            super(itemView);
            Image = itemView.findViewById(R.id.imageView);
            Name = itemView.findViewById(R.id.name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recycleViewInterface2 != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recycleViewInterface2.onItemClick2(pos);
                        }
                    }
                }
            });
        }
    }
}
