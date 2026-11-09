package com.example.rentconnect.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.rentconnect.R;
import com.example.rentconnect.database.RentConnectDbHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtName, edtEmail, edtPhone, edtPassword;
    private RadioGroup rgRole;
    private RadioButton rbTenant;
    private Button btnRegisterSubmit;
    private RentConnectDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new RentConnectDbHelper(this);

        edtName = findViewById(R.id.edtRegName);
        edtEmail = findViewById(R.id.edtRegEmail);
        edtPhone = findViewById(R.id.edtRegPhone);
        edtPassword = findViewById(R.id.edtRegPassword);
        rgRole = findViewById(R.id.rgRole);
        rbTenant = findViewById(R.id.rbTenant);
        btnRegisterSubmit = findViewById(R.id.btnRegisterSubmit);

        btnRegisterSubmit.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String pass = edtPassword.getText().toString().trim();
            String role = rbTenant.isChecked() ? "Tenant" : "Landlord";

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền các thông tin bắt buộc!", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean ok = dbHelper.registerUser(name, email, pass, phone, role);
            if (ok) {
                Toast.makeText(this, "Đăng ký thành công! Hãy đăng nhập.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Email đã tồn tại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
