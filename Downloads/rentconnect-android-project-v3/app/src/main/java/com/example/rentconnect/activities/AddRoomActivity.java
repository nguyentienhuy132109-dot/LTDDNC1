package com.example.rentconnect.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.rentconnect.R;
import com.example.rentconnect.database.RentConnectDbHelper;
import com.example.rentconnect.utils.SharedPrefManager;

public class AddRoomActivity extends AppCompatActivity {

    private EditText edtTitle, edtCity, edtAddressDetail, edtArea, edtCapacity, edtPrice, edtDeposit, edtElec, edtWater, edtService, edtAmenities;
    private Button btnSave;
    private RentConnectDbHelper dbHelper;
    private SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        dbHelper = new RentConnectDbHelper(this);
        prefManager = new SharedPrefManager(this);

        edtTitle = findViewById(R.id.edtAddTitle);
        edtCity = findViewById(R.id.edtAddCity);
        edtAddressDetail = findViewById(R.id.edtAddAddressDetail);
        edtArea = findViewById(R.id.edtAddArea);
        edtCapacity = findViewById(R.id.edtAddCapacity);
        edtPrice = findViewById(R.id.edtAddPrice);
        edtDeposit = findViewById(R.id.edtAddDeposit);
        edtElec = findViewById(R.id.edtAddElecPrice);
        edtWater = findViewById(R.id.edtAddWaterPrice);
        edtService = findViewById(R.id.edtAddServiceFee);
        edtAmenities = findViewById(R.id.edtAddAmenities);
        btnSave = findViewById(R.id.btnSaveRoom);

        btnSave.setOnClickListener(v -> {
            String title = edtTitle.getText().toString().trim();
            String city = edtCity.getText().toString().trim();
            String detail = edtAddressDetail.getText().toString().trim();

            if (title.isEmpty() || city.isEmpty() || detail.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tiêu đề và địa chỉ!", Toast.LENGTH_SHORT).show();
                return;
            }

            double area = parseDouble(edtArea.getText().toString(), 25.0);
            int capacity = parseInt(edtCapacity.getText().toString(), 2);
            double price = parseDouble(edtPrice.getText().toString(), 3500000);
            double deposit = parseDouble(edtDeposit.getText().toString(), 3500000);
            double elec = parseDouble(edtElec.getText().toString(), 3800);
            double water = parseDouble(edtWater.getText().toString(), 25000);
            double service = parseDouble(edtService.getText().toString(), 100000);
            String amenities = edtAmenities.getText().toString().trim();

            boolean ok = dbHelper.insertRoom(prefManager.getUserId(), title, city, detail, area, capacity, price, deposit, elec, water, service, amenities, "room_default.jpg");
            if (ok) {
                Toast.makeText(this, "Đăng tin phòng trọ thành công (BM01 / FR02)!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private double parseDouble(String s, double def) {
        try { return Double.parseDouble(s); } catch (Exception e) { return def; }
    }

    private int parseInt(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }
}
