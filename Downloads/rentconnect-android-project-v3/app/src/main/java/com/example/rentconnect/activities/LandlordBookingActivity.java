
package com.example.rentconnect.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rentconnect.R;
import com.example.rentconnect.adapters.BookingAdapter;
import com.example.rentconnect.database.RentConnectDbHelper;
import com.example.rentconnect.models.Booking;
import java.util.List;

public class LandlordBookingActivity extends AppCompatActivity {

    private RecyclerView rvBookings;
    private RentConnectDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_booking);

        dbHelper = new RentConnectDbHelper(this);
        rvBookings = findViewById(R.id.rvLandlordBookings);
        rvBookings.setLayoutManager(new LinearLayoutManager(this));

        List<Booking> bookingList = dbHelper.getAllBookings();
        rvBookings.setAdapter(new BookingAdapter(this, bookingList, true));
    }
}
