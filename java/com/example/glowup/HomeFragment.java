package com.example.glowup;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment implements RecycleViewInterface,RecycleViewInterface2,RecycleViewInterface3,RecycleViewInterface4 {

    RecyclerView recyclerViewHair, recyclerViewFacial, recyclerViewMakeup, recyclerViewNail;

    ProgressBar progressBar1,progressBar2,progressBar3,progressBar4;


    DatabaseReference database1, database2, database3, database4;
    Adapter adapter1;
    Adapter2 adapter2;
    Adapter3 adapter3;
    Adapter4 adapter4;
    ArrayList<Category> listHair, listFacial, listMakeup, listNail;
    TextView greetingTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerViewHair = rootView.findViewById(R.id.recycleview1);
        recyclerViewFacial = rootView.findViewById(R.id.recycleview2);
        recyclerViewMakeup = rootView.findViewById(R.id.recycleview3);
        recyclerViewNail = rootView.findViewById(R.id.recycleview4);
        progressBar1=rootView.findViewById(R.id.PBHair);
        progressBar2=rootView.findViewById(R.id.PBFacial);
        progressBar3=rootView.findViewById(R.id.PBNail);
        progressBar4=rootView.findViewById(R.id.PBMakeup);
        greetingTextView=rootView.findViewById(R.id.greetingTextView);

        updateGreeting();

        database1 = FirebaseDatabase.getInstance().getReference().child("Hair care");
        database2 = FirebaseDatabase.getInstance().getReference().child("Facial Treatments");
        database3 = FirebaseDatabase.getInstance().getReference().child("Makeup Services");
        database4 = FirebaseDatabase.getInstance().getReference().child("Nail Care");
        recyclerViewHair.setHasFixedSize(true);
        recyclerViewFacial.setHasFixedSize(true);
        recyclerViewMakeup.setHasFixedSize(true);
        recyclerViewNail.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listHair = new ArrayList<>();
        adapter1 = new Adapter(getActivity(), listHair, this);
        recyclerViewHair.setAdapter(adapter1);

        listFacial = new ArrayList<>();
        adapter2 = new Adapter2(getActivity(), listFacial, this);
        recyclerViewFacial.setAdapter(adapter2);

        listMakeup = new ArrayList<>();
        adapter3 = new Adapter3(getActivity(), listMakeup, this);
        recyclerViewMakeup.setAdapter(adapter3);

        listNail = new ArrayList<>();
        adapter4 = new Adapter4(getActivity(), listNail, this);
        recyclerViewNail.setAdapter(adapter4);

        database1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                progressBar1.setVisibility(View.GONE);
                recyclerViewHair.setVisibility(View.VISIBLE);


                listHair.clear(); // Clear the list before adding new data to avoid duplicates

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Category category = dataSnapshot.getValue(Category.class);
                    listHair.add(category);
                }

                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error, e.g., show an error message
            }
        });

        database2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listFacial.clear(); // Clear the list before adding new data to avoid duplicates
                progressBar2.setVisibility(View.GONE);
                recyclerViewFacial.setVisibility(View.VISIBLE);

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Category category = dataSnapshot.getValue(Category.class);
                    listFacial.add(category);
                }

                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error, e.g., show an error message
            }
        });

        database3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listMakeup.clear(); // Clear the list before adding new data to avoid duplicates
                progressBar3.setVisibility(View.GONE);
                recyclerViewNail.setVisibility(View.VISIBLE);

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Category category = dataSnapshot.getValue(Category.class);
                    listMakeup.add(category);
                }

                adapter3.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error, e.g., show an error message
            }
        });

        database4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listNail.clear(); // Clear the list before adding new data to avoid duplicates
                progressBar4.setVisibility(View.GONE);
                recyclerViewMakeup.setVisibility(View.VISIBLE);

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Category category = dataSnapshot.getValue(Category.class);
                    listNail.add(category);
                }

                adapter4.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error, e.g., show an error message
            }
        });

        return rootView;

    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), Details.class);
        intent.putExtra("NAME", listHair.get(position).getName());
        String reference= "Hair care";
        intent.putExtra("Refe",reference);
        startActivity(intent);

    }

    @Override
    public void onItemClick2(int position) {
        Intent intent = new Intent(getActivity(), Details.class);
        intent.putExtra("NAME", listFacial.get(position).getName());
        String reference= "Facial Treatments";
        intent.putExtra("Refe",reference);
        startActivity(intent);

    }

    @Override
    public void onItemClick3(int position) {
        Intent intent = new Intent(getActivity(), Details.class);
        intent.putExtra("NAME", listMakeup.get(position).getName());
        String reference= "Makeup Services";
        intent.putExtra("Refe",reference);
        startActivity(intent);

    }

    @Override
    public void onItemClick4(int position) {
        Intent intent = new Intent(getActivity(), Details.class);
        intent.putExtra("NAME", listNail.get(position).getName());
        String reference= "Nail Care";
        intent.putExtra("Refe",reference);
        startActivity(intent);

    }
    private void updateGreeting() {
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        String greeting;

        if (hourOfDay >= 0 && hourOfDay < 12) {
            greeting = "Good Morning";
        } else if (hourOfDay >= 12 && hourOfDay < 18) {
            greeting = "Good Afternoon";
        } else if (hourOfDay >= 18 && hourOfDay < 21) {
            greeting = "Good Evening";
        } else {
            greeting = "Good Night";
        }

        // Set the greeting in the TextView
        greetingTextView.setText(greeting);
    }
}