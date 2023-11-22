package com.example.glowup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class AddReviews extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST_BEFORE = 1;
    private static final int PICK_IMAGE_REQUEST_AFTER = 2;

    private EditText edtUserName, edtReview;
    private ImageView imgBefore, imgAfter;
    RelativeLayout imageBeforeBtn,imageAfterBtn;
    private Button btnSubmitReview;

    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private Uri beforeImageUri, afterImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reviews);

        // Initialize Firebase
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        // Initialize Views
        edtUserName = findViewById(R.id.edtUserName);
        edtReview = findViewById(R.id.edtReview);
        imageBeforeBtn = findViewById(R.id.beforePictureBtn);
        imageAfterBtn = findViewById(R.id.afterPictureBtn);
        btnSubmitReview = findViewById(R.id.btnSubmitReview);
        imgBefore = findViewById(R.id.imgBefore);
        imgAfter = findViewById(R.id.imgAfter);

        // Set onClickListener for imgBefore and imgAfter to pick images from the gallery
        imageBeforeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage(PICK_IMAGE_REQUEST_BEFORE);
            }
        });

        imageAfterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage(PICK_IMAGE_REQUEST_AFTER);
            }
        });

        // Set onClickListener for btnSubmitReview to upload data to Firebase
        btnSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadDataToFirebase();
                Toast.makeText(AddReviews.this, "Review submitted successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AddReviews.this,HomeFragment.class);
                startActivity(intent);

            }
        });
    }

    private void pickImage(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            if (requestCode == PICK_IMAGE_REQUEST_BEFORE) {
                beforeImageUri = selectedImageUri;
                imgBefore.setImageURI(beforeImageUri);
            } else if (requestCode == PICK_IMAGE_REQUEST_AFTER) {
                afterImageUri = selectedImageUri;
                imgAfter.setImageURI(afterImageUri);
            }
        }
    }

    private void uploadDataToFirebase() {
        // Get user input
        String userName = edtUserName.getText().toString().trim();
        String reviewText = edtReview.getText().toString().trim();

        // Validate user input
        if (userName.isEmpty() || reviewText.isEmpty() || beforeImageUri == null || afterImageUri == null) {
            Toast.makeText(this, "Please fill in all fields and select images", Toast.LENGTH_SHORT).show();
            return;
        }

        // Upload images to Firebase Storage and get download URLs
        uploadImageToFirebaseStorage(beforeImageUri, "before_image.jpg");
        uploadImageToFirebaseStorage(afterImageUri, "after_image.jpg");
    }

    private void uploadImageToFirebaseStorage(Uri imageUri, String imageName) {
        StorageReference imageRef = storageReference.child("images/" + imageName);
        String userName = edtUserName.getText().toString().trim();
        String reviewText = edtReview.getText().toString().trim();

        imageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Image uploaded successfully, now get the download URL
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Add the image URL to the reviewData map
                                String imageUrl = uri.toString();

                                // Create a review object
                                Map<String, Object> reviewData = new HashMap<>();
                                reviewData.put("userName", userName);
                                reviewData.put("reviewText", reviewText);
                                reviewData.put("beforeImageUrl", imageUrl); // Add the appropriate key for before and after images
                                // Add the review to Firebase Firestore
                                addReviewToFirestore(reviewData);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddReviews.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addReviewToFirestore(Map<String, Object> reviewData) {
        // Add the review to Firebase Firestore
        db.collection("reviews")
                .add(reviewData)
                .addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(AddReviews.this, "Review submitted successfully", Toast.LENGTH_SHORT).show();
                        // Clear input fields and reset image views if needed
                        edtUserName.setText("");
                        edtReview.setText("");
                        imgBefore.setImageURI(null);
                        imgAfter.setImageURI(null);

                        Intent intent=new Intent(AddReviews.this,HomeFragment.class);
                        startActivity(intent);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddReviews.this, "Failed to submit review", Toast.LENGTH_SHORT).show();
                        Toast.makeText(AddReviews.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                        Log.e("AddReviews", "Failed to upload image", e);
                    }
                });

    }
}

