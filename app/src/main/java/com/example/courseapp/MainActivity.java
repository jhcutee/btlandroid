package com.example.courseapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import com.example.courseapp.database.model.User;
import com.example.courseapp.database.repository.UserRepository;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btndangky;
    private EditText edthoten, edtmsv, edtemail, edtmatkhau;
    private TextView loginlink;
    private CheckBox checkBoxShowPassword;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        userRepository = new UserRepository(databaseHelper);

        getWidgetsControl();

        btndangky.setOnClickListener(v -> handleRegister());

        edtmatkhau.setTransformationMethod(PasswordTransformationMethod.getInstance());

        loginlink.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, loginActivity.class);
            startActivity(intent);
        });

        checkBoxShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> handlePasswordVisibilityToggle(isChecked));
    }

    private void handlePasswordVisibilityToggle(boolean isChecked) {
        if (isChecked) {
            edtmatkhau.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            edtmatkhau.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        edtmatkhau.setSelection(edtmatkhau.getText().length());
    }

    private void handleRegister() {
        String fullName = edthoten.getText().toString().trim();
        String studentId = edtmsv.getText().toString().trim();
        String email = edtemail.getText().toString().trim();
        String password = edtmatkhau.getText().toString().trim();

        if (validateInput(fullName, studentId, email, password)) {
            if (isStudentIdDuplicate(studentId)) {
                edtmsv.setError("Mã sinh viên đã tồn tại");
            } else if (isEmailDuplicate(email)) {
                edtemail.setError("Email đã tồn tại");
            } else {
                edtmsv.setError(null);
                edtemail.setError(null);
                performRegistration(fullName, studentId, email, password);
            }
        }
    }

    private boolean validateInput(String fullName, String studentId, String email, String password) {
        boolean isValid = true;

        String nameRegex = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$";

        if (!fullName.matches(nameRegex)) {
            edthoten.setError("Họ tên không hợp lệ");
            isValid = false;
        } else {
            edthoten.setError(null);
        }

        if (!studentId.matches("\\d{8,}")) {
            edtmsv.setError("Mã sinh viên không hợp lệ");
            isValid = false;
        } else {
            edtmsv.setError(null);
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtemail.setError("Email không hợp lệ");
            isValid = false;
        } else {
            edtemail.setError(null);
        }

        if (password.length() < 8 || !password.matches(".*\\d.*")) {
            edtmatkhau.setError("Mật khẩu phải tối thiểu nhất 8 ký tự và ít nhất một ký tự số");
            isValid = false;
        } else {
            edtmatkhau.setError(null);
        }
        return isValid;
    }

    private boolean isStudentIdDuplicate(String studentId) {
        ArrayList<User> users = userRepository.find();
        for (User user : users) {
            if (user.getUsername() != null && user.getUsername().equals(studentId)) {
                return true;
            }
        }
        return false;
    }

    private boolean isEmailDuplicate(String email) {
        ArrayList<User> users = userRepository.find();
        for (User user : users) {
            if (user.getEmail() != null && user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    private void performRegistration(String fullName, String studentId, String email, String password) {
        User user = new User();
        user.setFullName(fullName);
        user.setUsername(studentId);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.registerUser(user);

        Intent intent = new Intent(MainActivity.this, loginActivity.class);
        startActivity(intent);
        finish();
    }

    private void getWidgetsControl() {
        btndangky = findViewById(R.id.btndangky);
        edthoten = findViewById(R.id.edthoten);
        edtmsv = findViewById(R.id.edtmsv);
        edtemail = findViewById(R.id.edtemail);
        edtmatkhau = findViewById(R.id.edtmatkhau);
        loginlink = findViewById(R.id.loginlink);
        checkBoxShowPassword = findViewById(R.id.checkBoxShowPassword);
    }
}
