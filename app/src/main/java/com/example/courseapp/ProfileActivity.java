package com.example.courseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.courseapp.database.DatabaseHelper;
import com.example.courseapp.database.model.TemporaryLoginInfo;
import com.example.courseapp.database.model.User;
import com.example.courseapp.database.repository.UserRepository;

public class ProfileActivity extends AppCompatActivity {

    private ImageView imgProfileArrow, imgProfileEdit, imgProfileAvatar;
    private TextView txtProfileFullName, txtMSV, txtHoten, txtEmail, txtNgaysinh, txtGioitinh, txtDiachi;

    private UserRepository userRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        getWidget();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        userRepository = new UserRepository(dbHelper);
        String username = TemporaryLoginInfo.getInstance().getUsername();

        User user = userRepository.getUserByUsername(username);

        if (user != null) {
            String name = user.getFullName();
            String avatarUrl = user.getAvatar();

            txtProfileFullName.setText(name);

            if (avatarUrl != null) {
                Glide.with(this)
                        .load(  "file:///android_asset/" + avatarUrl)
                        .into(imgProfileAvatar);
            }

            txtMSV.setText(user.getUsername());
            txtHoten.setText(user.getFullName());
            txtEmail.setText(user.getEmail());
            txtNgaysinh.setText(user.getDob());
            txtGioitinh.setText(user.getGender());
            txtDiachi.setText(user.getAddress());
        }
        imgProfileArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imgProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    public void getWidget(){
        imgProfileArrow = findViewById(R.id.img_profile_arrow);
        imgProfileEdit = findViewById(R.id.img_profile_edit);
        imgProfileAvatar = findViewById(R.id.img_profile_avatar);
        txtProfileFullName = findViewById(R.id.txt_profile_name);
        txtMSV = findViewById(R.id.txt_profile_msv);
        txtHoten = findViewById(R.id.txt_profile_hoten);
        txtEmail = findViewById(R.id.txt_profile_email);
        txtNgaysinh = findViewById(R.id.txt_profile_ngaysinh);
        txtGioitinh = findViewById(R.id.txt_profile_gioitinh);
        txtDiachi = findViewById(R.id.txt_profile_diachi);
    }
}