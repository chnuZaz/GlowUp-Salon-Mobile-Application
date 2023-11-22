package com.example.glowup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/* *
 * A simple {@link Fragment} subclass.
 * Use the {@link AppontmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppontmentFragment extends Fragment {

    /* // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AppontmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppontmentFragment.
     */
    /*
    // TODO: Rename and change types and number of parameters
    public static AppontmentFragment newInstance(String param1, String param2) {
        AppontmentFragment fragment = new AppontmentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appontment, container, false);
    }
    */
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button btnBook;
    private ListView listViewBookings;
    private TextView tvSelectedDateTime;

    private DatabaseReference bookingsReference;
    private List<String> bookedSlots;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appontment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Firebase
        bookingsReference = FirebaseDatabase.getInstance().getReference("bookings");

        // Initialize UI components
        datePicker = view.findViewById(R.id.datePicker);
        timePicker = view.findViewById(R.id.timePicker);
        btnBook = view.findViewById(R.id.btnBook);
        listViewBookings = view.findViewById(R.id.listViewBookings);
        tvSelectedDateTime = view.findViewById(R.id.tvSelectedDateTime);

        bookedSlots = new ArrayList<>();

        // Set up the ListView adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, bookedSlots);
        listViewBookings.setAdapter(adapter);

        // Set up onClickListener for the Book button
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookSlot();
            }
        });

        // Set up the OnDateChangedListener for the DatePicker
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                updateSelectedDateTime();
            }
        });

        // Set up the OnTimeChangedListener for the TimePicker
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                updateSelectedDateTime();
            }
        });

        // Retrieve and display existing bookings from Firebase
        retrieveBookingsFromFirebase();
    }

    private void updateSelectedDateTime() {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1; // Month is zero-based
        int year = datePicker.getYear();
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        String selectedDateTime = String.format("%02d/%02d/%d %02d:%02d", day, month, year, hour, minute);
        tvSelectedDateTime.setText("Selected Date and Time: " + selectedDateTime);
    }

    private void bookSlot() {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1; // Month is zero-based
        int year = datePicker.getYear();
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        String slot = String.format("%02d/%02d/%d %02d:%02d", day, month, year, hour, minute);

        // Check if the slot is already booked
        if (!bookedSlots.contains(slot)) {
            // Book the slot and update Firebase
            bookedSlots.add(slot);
            bookingsReference.setValue(bookedSlots);

            // Clear the selected date and time
            datePicker.updateDate(0, 0, 0);
            timePicker.setHour(0);
            timePicker.setMinute(0);
            tvSelectedDateTime.setText("Selected Date and Time");

            // Update the ListView adapter
            ((ArrayAdapter) listViewBookings.getAdapter()).notifyDataSetChanged();
        } else {
            // Slot already booked
            // You can display a message or handle it in a way suitable for your app
        }
    }

    private void retrieveBookingsFromFirebase() {
        // Retrieve existing bookings from Firebase
        bookingsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear existing bookings
                bookedSlots.clear();

                // Retrieve new bookings
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String booking = snapshot.getValue(String.class);
                    bookedSlots.add(booking);
                }

                // Update the ListView adapter
                ((ArrayAdapter) listViewBookings.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });
    }
}