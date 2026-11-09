package com.example.rentconnect.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rentconnect.R;
import com.example.rentconnect.adapters.TicketAdapter;
import com.example.rentconnect.database.RentConnectDbHelper;
import com.example.rentconnect.models.Ticket;
import java.util.List;

public class LandlordTicketActivity extends AppCompatActivity {

    private RecyclerView rvTickets;
    private RentConnectDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_ticket);

        dbHelper = new RentConnectDbHelper(this);
        rvTickets = findViewById(R.id.rvLandlordTickets);
        rvTickets.setLayoutManager(new LinearLayoutManager(this));

        List<Ticket> ticketList = dbHelper.getAllTickets();
        rvTickets.setAdapter(new TicketAdapter(this, ticketList, true));
    }
}
