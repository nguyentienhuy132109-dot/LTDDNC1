package com.example.rentconnect.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rentconnect.R;
import com.example.rentconnect.adapters.InvoiceAdapter;
import com.example.rentconnect.database.RentConnectDbHelper;
import com.example.rentconnect.models.Invoice;
import com.example.rentconnect.utils.VietQRHelper;
import java.util.List;

public class TenantInvoiceActivity extends AppCompatActivity {

    private RecyclerView rvInvoices;
    private Button btnPayVietQR;
    private RentConnectDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_invoice);

        dbHelper = new RentConnectDbHelper(this);

        rvInvoices = findViewById(R.id.rvTenantInvoices);
        btnPayVietQR = findViewById(R.id.btnPayVietQR);

        rvInvoices.setLayoutManager(new LinearLayoutManager(this));
        List<Invoice> invoiceList = dbHelper.getAllInvoices();
        rvInvoices.setAdapter(new InvoiceAdapter(this, invoiceList, false));

        btnPayVietQR.setOnClickListener(v -> {
            String payload = VietQRHelper.generateQrPayload(1, 4269000, "1903456789");
            Toast.makeText(this, "Mã VietQR 1-Touch:\n" + payload, Toast.LENGTH_LONG).show();
        });
    }
}
