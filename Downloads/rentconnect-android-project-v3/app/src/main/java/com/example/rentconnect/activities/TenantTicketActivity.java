package com.example.rentconnect.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rentconnect.R;
import com.example.rentconnect.adapters.TicketAdapter;
import com.example.rentconnect.database.RentConnectDbHelper;
import com.example.rentconnect.models.Ticket;
import com.example.rentconnect.utils.SharedPrefManager;
import java.util.List;

public class TenantTicketActivity extends AppCompatActivity {

    private Spinner spCategory, spPriority;
    private EditText edtDesc;
    private Button btnSend;
    private RecyclerView rvTickets;
    private RentConnectDbHelper dbHelper;
    private SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_ticket);

        dbHelper = new RentConnectDbHelper(this);
        prefManager = new SharedPrefManager(this);

        spCategory = findViewById(R.id.spCategory);
        spPriority = findViewById(R.id.spPriority);
        edtDesc = findViewById(R.id.edtTicketDesc);
        btnSend = findViewById(R.id.btnSendTicket);
        rvTickets = findViewById(R.id.rvTenantTickets);

        rvTickets.setLayoutManager(new LinearLayoutManager(this));
        loadTickets();

        btnSend.setOnClickListener(v -> {
            String cat = spCategory.getSelectedItem() != null ? spCategory.getSelectedItem().toString() : "Điện";
            String pri = spPriority.getSelectedItem() != null ? spPriority.getSelectedItem().toString() : "Gấp";
            String desc = edtDesc.getText().toString().trim();

            if (desc.isEmpty()) {
                Toast.makeText(this, "Mô tả không được để trống!", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean ok = dbHelper.createTicket(prefManager.getUserId(), 1, cat, pri, desc, "ticket_img.jpg");
            if (ok) {
                Toast.makeText(this, "Gửi báo cáo sự cố khẩn cấp (Ticket) thành công!", Toast.LENGTH_SHORT).show();
                edtDesc.setText("");
                loadTickets();
            }
        });
    }

    private void loadTickets() {
        List<Ticket> ticketList = dbHelper.getAllTickets();
        rvTickets.setAdapter(new TicketAdapter(this, ticketList, false));
    }
}
