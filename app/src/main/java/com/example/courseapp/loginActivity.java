package com.example.courseapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.courseapp.database.DatabaseHelper;
import com.example.courseapp.database.model.TemporaryLoginInfo;
import com.example.courseapp.database.model.User;
import com.example.courseapp.database.repository.UserRepository;

import java.util.ArrayList;

public class loginActivity extends AppCompatActivity {

    private Button btndangnhap;
    private EditText edtmsv, edtmatkhau;
    private TextView registerlink;
    private CheckBox checkBoxShowPassword;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        userRepository = new UserRepository(databaseHelper);

        getWidgetsControl();

        // Đặt mặc định ẩn mật khẩu
        edtmatkhau.setTransformationMethod(PasswordTransformationMethod.getInstance());

        btndangnhap.setOnClickListener(v -> {
            if (login()) {
                handleLogin();
            } else {
                showDialog("Đăng nhập thất bại", "Msv hoặc mật khẩu không đúng");
            }
        });

        registerlink.setOnClickListener(v -> {
            Intent intent = new Intent(loginActivity.this, MainActivity.class);
            startActivity(intent);
        });

        checkBoxShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> handlePasswordVisibilityToggle(isChecked));
    }

    private void handleLogin() {
        String studentId = edtmsv.getText().toString().trim();
        String password = edtmatkhau.getText().toString().trim();

        if (validateInput(studentId, password)) {
            performLogin(studentId, password);
        }
    }

    private void handlePasswordVisibilityToggle(boolean isChecked) {
        if (isChecked) {
            edtmatkhau.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            edtmatkhau.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        edtmatkhau.setSelection(edtmatkhau.getText().length());
    }

    private void showDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    private boolean login() {
        String msv = edtmsv.getText().toString();
        String pass = edtmatkhau.getText().toString();
        return loginUser(msv, pass);
    }

    private boolean loginUser(String msv, String pass) {
        ArrayList<User> users = userRepository.find();

        for (User user : users) {
            if (user.getUsername() != null && user.getUsername().equals(msv) &&
                    user.getPassword() != null && user.getPassword().equals(pass)) {
                TemporaryLoginInfo.getInstance().setLoginInfo(msv, pass);
                return true;
            }
        }
        return false;
    }

    private boolean validateInput(String studentId, String password) {
        boolean isValid = true;

        if (!studentId.matches("\\d{8,}")) {
            edtmsv.setError("Mã sinh viên không hợp lệ");
            isValid = false;
        } else {
            edtmsv.setError(null);
        }

        if (password.length() < 8 || !password.matches(".*\\d.*")) {
            edtmatkhau.setError("Mật khẩu phải tối thiểu nhất 8 ký tự và ít nhất một ký tự số");
            isValid = false;
        } else {
            edtmatkhau.setError(null);
        }
        return isValid;
    }

    private void performLogin(String studentId, String password) {
        Intent intent = new Intent(loginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void getWidgetsControl() {
        btndangnhap = findViewById(R.id.btndangnhap);
        edtmsv = findViewById(R.id.edtmsv);
        edtmatkhau = findViewById(R.id.edtmatkhau);
        checkBoxShowPassword = findViewById(R.id.checkBoxShowPassword);
        registerlink = findViewById(R.id.registerlink);
    }
}
