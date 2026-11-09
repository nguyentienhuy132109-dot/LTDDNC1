package com.example.rentconnect.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.rentconnect.R;
import com.example.rentconnect.database.RentConnectDbHelper;
import com.example.rentconnect.utils.SharedPrefManager;

public class BookingActivity extends AppCompatActivity {

    private EditText edtDate, edtTimeSlot, edtPhone, edtNotes;
    private Button btnSubmit;
    private RentConnectDbHelper dbHelper;
    private SharedPrefManager prefManager;
    private int roomId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        dbHelper = new RentConnectDbHelper(this);
        prefManager = new SharedPrefManager(this);
        roomId = getIntent().getIntExtra("ROOM_ID", 1);

        edtDate = findViewById(R.id.edtBookingDate);
        edtTimeSlot = findViewById(R.id.edtBookingTimeSlot);
        edtPhone = findViewById(R.id.edtBookingPhone);
        edtNotes = findViewById(R.id.edtBookingNotes);
        btnSubmit = findViewById(R.id.btnSubmitBooking);

        btnSubmit.setOnClickListener(v -> {
            String date = edtDate.getText().toString().trim();
            String time = edtTimeSlot.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String notes = edtNotes.getText().toString().trim();

            if (date.isEmpty() || time.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập Ngày, Khung giờ và SĐT!", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean ok = dbHelper.insertBooking(roomId, prefManager.getUserId(), date, time, phone, notes);
            if (ok) {
                Toast.makeText(this, "Gửi yêu cầu đặt lịch xem phòng thành công (BM02)! Wait for Landlord approval.", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
