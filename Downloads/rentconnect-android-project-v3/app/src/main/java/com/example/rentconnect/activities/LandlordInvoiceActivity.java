package com.example.rentconnect.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rentconnect.R;
import com.example.rentconnect.adapters.InvoiceAdapter;
import com.example.rentconnect.database.RentConnectDbHelper;
import com.example.rentconnect.models.Invoice;
import java.util.List;

public class LandlordInvoiceActivity extends AppCompatActivity {

    private RecyclerView rvInvoices;
    private RentConnectDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_invoice);

        dbHelper = new RentConnectDbHelper(this);
        rvInvoices = findViewById(R.id.rvLandlordInvoices);
        rvInvoices.setLayoutManager(new LinearLayoutManager(this));

        List<Invoice> invoiceList = dbHelper.getAllInvoices();
        rvInvoices.setAdapter(new InvoiceAdapter(this, invoiceList, true));
    }
}
