package com.example.rentconnect.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import com.example.rentconnect.R;
import com.example.rentconnect.database.RentConnectDbHelper;
import com.example.rentconnect.models.User;
import com.example.rentconnect.utils.SharedPrefManager;
import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnLogin, btnBiometricLogin, btnGoRegister;
    private RentConnectDbHelper dbHelper;
    private SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new RentConnectDbHelper(this);
        prefManager = new SharedPrefManager(this);

        if (prefManager.isLoggedIn()) {
            navigateByRole(prefManager.getUserRole());
            return;
        }

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnBiometricLogin = findViewById(R.id.btnBiometric);
        btnGoRegister = findViewById(R.id.btnGoRegister);

        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String pass = edtPassword.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ Email và Mật khẩu!", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = dbHelper.checkLogin(email, pass);
            if (user != null) {
                prefManager.saveUserSession(user.getId(), user.getFullName(), user.getEmail(), user.getRole());
                Toast.makeText(this, "Đăng nhập thành công với vai trò: " + user.getRole(), Toast.LENGTH_SHORT).show();
                navigateByRole(user.getRole());
            } else {
                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
            }
        });

        btnBiometricLogin.setOnClickListener(v -> setupBiometricPrompt());

        btnGoRegister.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
    }

    private void setupBiometricPrompt() {
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(LoginActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                prefManager.saveUserSession(2, "Nguyễn Tiến Huy", "tenant@rentconnect.vn", "Tenant");
                Toast.makeText(LoginActivity.this, "Xác thực sinh trắc học thành công!", Toast.LENGTH_SHORT).show();
                navigateByRole("Tenant");
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(LoginActivity.this, "Xác thực không thành công!", Toast.LENGTH_SHORT).show();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Xác thực Sinh trắc học RentConnect")
                .setSubtitle("Sử dụng vân tay hoặc khuôn mặt để đăng nhập")
                .setNegativeButtonText("Hủy")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }

    private void navigateByRole(String role) {
        if ("Landlord".equalsIgnoreCase(role)) {
            startActivity(new Intent(this, LandlordMainActivity.class));
        } else {
            startActivity(new Intent(this, TenantMainActivity.class));
        }
        finish();
    }
}
