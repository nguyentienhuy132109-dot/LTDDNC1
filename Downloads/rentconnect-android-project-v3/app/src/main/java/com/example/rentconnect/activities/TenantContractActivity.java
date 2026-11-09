package com.example.rentconnect.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.rentconnect.R;
import com.example.rentconnect.database.RentConnectDbHelper;
import com.example.rentconnect.models.Contract;
import com.example.rentconnect.utils.SharedPrefManager;
import java.util.Locale;

public class TenantContractActivity extends AppCompatActivity {

    private TextView tvRoomTitle, tvDates, tvPriceDeposit, tvTerms, tvStatus;
    private Button btnConfirm;
    private RentConnectDbHelper dbHelper;
    private SharedPrefManager prefManager;
    private Contract contract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_contract);

        dbHelper = new RentConnectDbHelper(this);
        prefManager = new SharedPrefManager(this);

        tvRoomTitle = findViewById(R.id.tvContractRoomTitle);
        tvDates = findViewById(R.id.tvContractDates);
        tvPriceDeposit = findViewById(R.id.tvContractPriceDeposit);
        tvTerms = findViewById(R.id.tvContractTerms);
        tvStatus = findViewById(R.id.tvContractStatus);
        btnConfirm = findViewById(R.id.btnConfirmContract);

        contract = dbHelper.getContractByTenant(prefManager.getUserId());
        if (contract != null) {
            tvRoomTitle.setText("Phòng thuê: " + contract.getRoomTitle());
            tvDates.setText("Thời hạn hợp đồng: " + contract.getStartDate() + " đến " + contract.getEndDate());
            tvPriceDeposit.setText(String.format(Locale.getDefault(), "Giá thuê: %,.0f VNĐ/tháng | Tiền cọc: %,.0f VNĐ", contract.getRentPrice(), contract.getDeposit()));
            tvTerms.setText("ĐIỀU KHỎAN HỢP ĐỒNG (BM03):\n" + contract.getTerms());
            tvStatus.setText("Trạng thái hợp đồng: " + (contract.getStatus().equals("ACTIVE") ? "ĐÃ XÁC NHẬN (Read-Only)" : "CHỜ XÁC NHẬN"));

            if (contract.getStatus().equals("ACTIVE")) {
                btnConfirm.setEnabled(false);
                btnConfirm.setText("🔒 HỢP ĐỒNG ĐÃ KÍCH HOẠT (Chỉ đọc)");
            }
        }

        btnConfirm.setOnClickListener(v -> {
            if (contract != null) {
                dbHelper.confirmContract(contract.getId());
                Toast.makeText(this, "Đã xác nhận hợp đồng điện tử thành công (FR06)!", Toast.LENGTH_SHORT).show();
                tvStatus.setText("Trạng thái hợp đồng: ĐÃ XÁC NHẬN (Read-Only)");
                btnConfirm.setEnabled(false);
                btnConfirm.setText("🔒 HỢP ĐỒNG ĐÃ KÍCH HOẠT (Chỉ đọc)");
            }
        });
    }
}
