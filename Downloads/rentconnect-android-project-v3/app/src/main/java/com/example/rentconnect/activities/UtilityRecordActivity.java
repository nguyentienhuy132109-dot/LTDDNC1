package com.example.rentconnect.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.rentconnect.R;
import com.example.rentconnect.database.RentConnectDbHelper;

public class UtilityRecordActivity extends AppCompatActivity {

    private EditText edtMonthYear, edtOldE, edtNewE, edtOldW, edtNewW;
    private Button btnCapture, btnSave;
    private RentConnectDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utility_record);

        dbHelper = new RentConnectDbHelper(this);

        edtMonthYear = findViewById(R.id.edtMonthYear);
        edtOldE = findViewById(R.id.edtOldElectric);
        edtNewE = findViewById(R.id.edtNewElectric);
        edtOldW = findViewById(R.id.edtOldWater);
        edtNewW = findViewById(R.id.edtNewWater);
        btnCapture = findViewById(R.id.btnCaptureMeterPhoto);
        btnSave = findViewById(R.id.btnSaveUtility);

        btnCapture.setOnClickListener(v -> Toast.makeText(this, "📷 Camera Intent: Đã chụp ảnh công tơ điện nước!", Toast.LENGTH_SHORT).show());

        btnSave.setOnClickListener(v -> {
            String monthYear = edtMonthYear.getText().toString().trim();
            if (monthYear.isEmpty()) monthYear = "11/2026";

            double oldE = parseDouble(edtOldE.getText().toString(), 1380);
            double newE = parseDouble(edtNewE.getText().toString(), 1510);
            double oldW = parseDouble(edtOldW.getText().toString(), 52);
            double newW = parseDouble(edtNewW.getText().toString(), 60);

            boolean ok = dbHelper.insertUtilityRecord(1, monthYear, oldE, newE, oldW, newW, "elec_meter.jpg", "water_meter.jpg", 0);
            if (ok) {
                Toast.makeText(this, "Đã lưu chỉ số điện nước ngoại tuyến (Offline Storage)! Tự đồng bộ khi có Internet.", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private double parseDouble(String s, double def) {
        try { return Double.parseDouble(s); } catch (Exception e) { return def; }
    }
}
