package com.example.rentconnect.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rentconnect.R;
import com.example.rentconnect.adapters.RoomAdapter;
import com.example.rentconnect.database.RentConnectDbHelper;
import com.example.rentconnect.models.Room;
import com.example.rentconnect.utils.SharedPrefManager;
import java.util.List;

public class LandlordMainActivity extends AppCompatActivity {

    private TextView tvLandlordHeader;
    private RecyclerView rvRooms;
    private Button btnAddRoom, btnManageBookings, btnUtility, btnInvoice, btnTicket, btnLogout;
    private RentConnectDbHelper dbHelper;
    private SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_main);

        dbHelper = new RentConnectDbHelper(this);
        prefManager = new SharedPrefManager(this);

        tvLandlordHeader = findViewById(R.id.tvLandlordHeader);
        rvRooms = findViewById(R.id.rvLandlordRooms);
        btnAddRoom = findViewById(R.id.btnAddRoom);
        btnManageBookings = findViewById(R.id.btnManageBookings);
        btnUtility = findViewById(R.id.btnLandlordUtility);
        btnInvoice = findViewById(R.id.btnLandlordInvoice);
        btnTicket = findViewById(R.id.btnLandlordTicket);
        btnLogout = findViewById(R.id.btnLandlordLogout);

        tvLandlordHeader.setText("CHỦ TRỌ: " + prefManager.getUserName().toUpperCase());

        rvRooms.setLayoutManager(new LinearLayoutManager(this));
        loadRooms();

        btnAddRoom.setOnClickListener(v -> startActivity(new Intent(this, AddRoomActivity.class)));
        btnManageBookings.setOnClickListener(v -> {
            Intent intent = new Intent(LandlordMainActivity.this, LandlordBookingListActivity.class);
            startActivity(intent);
        });
        btnUtility.setOnClickListener(v -> startActivity(new Intent(this, UtilityRecordActivity.class)));
        btnInvoice.setOnClickListener(v -> startActivity(new Intent(this, LandlordInvoiceActivity.class)));
        btnTicket.setOnClickListener(v -> startActivity(new Intent(this, LandlordTicketActivity.class)));

        btnLogout.setOnClickListener(v -> {
            prefManager.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void loadRooms() {
        List<Room> roomList = dbHelper.getAllRooms();
        rvRooms.setAdapter(new RoomAdapter(this, roomList));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRooms();
    }
}
