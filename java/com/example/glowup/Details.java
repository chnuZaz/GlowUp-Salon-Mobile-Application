package com.example.glowup;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Details extends AppCompatActivity {

    TextView Title , Price , Description , Benifits ;
    ImageView Image ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String Reference= getIntent().getStringExtra("Refe");
        String Name=getIntent().getStringExtra("NAME");

        Title = findViewById(R.id.Topic);
        Price = findViewById(R.id.priceTV);
        Description = findViewById(R.id.descriptiontopic);
        Benifits = findViewById(R.id.benifit);
        Image=findViewById(R.id.imageView2);

        loadDetails(Reference,Name);


    }

    private void loadDetails(String reference, String name) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(reference).child(name);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Category service = dataSnapshot.getValue(Category.class);

                    if (service != null) {
                        // Make sure to use the correct IDs from your XML layout
                        TextView nameTextView = findViewById(R.id.Topic);
                        nameTextView.setText(service.getName());

                        TextView price = findViewById(R.id.priceTV);
                        price.setText(service.getPrice());

                        TextView description = findViewById(R.id.description);
                        description.setText(service.getDescription());

                        TextView benefits = findViewById(R.id.benifit);
                        benefits.setText(service.getBenefits());

                        ImageView location = findViewById(R.id.imageView2);
                        Picasso.get().load(service.getPhoto()).into(location);
                    } else {
                        Log.e(TAG, "Category is null");
                    }
                } else {
                    Log.e(TAG, "DataSnapshot does not exist");
                    Toast.makeText(Details.this, "Data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error fetching data: " + error.getMessage());
                Toast.makeText(Details.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}