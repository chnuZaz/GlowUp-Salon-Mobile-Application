package com.example.glowup;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class ReviewsFragment extends Fragment {

    FloatingActionButton btn;
    RecyclerView recyclerViewReviews;
    Review_Adapter reviewAdapter;
    ArrayList<Review_Model> reviewList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);

        btn = view.findViewById(R.id.fab);
        recyclerViewReviews = view.findViewById(R.id.recyclerViewReviews);

        // Initialize Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Reviews");

        // Set up RecyclerView
        reviewList = new ArrayList<>();
        reviewAdapter = new Review_Adapter(getActivity(), reviewList);
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewReviews.setAdapter(reviewAdapter);

        // Fetch data from Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reviewList.clear(); // Clear existing data to avoid duplication
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Review_Model review_model = snapshot.getValue(Review_Model.class);
                    reviewList.add(review_model);
                }
                reviewAdapter.notifyDataSetChanged(); // Notify the adapter that data has changed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddReviews.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
