package com.example.courseapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;

public class EditProfileActivity extends AppCompatActivity {

    private Button btnEditProfile;
    private ImageView imgEditProfileArrow, imgEditAvatar;

    private EditText editFullName, editMSV, editHoten, editEmail, editNgaysinh, editGioitinh, editDiachi;

    private UserRepository userRepository;
    private String currentAvatarUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        getWidget();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        userRepository = new UserRepository(databaseHelper);

        String username = TemporaryLoginInfo.getInstance().getUsername();

        User user = userRepository.getUserByUsername(username);

        if (user != null) {
            String name = user.getFullName();
            String avatarUrl = user.getAvatar();
            currentAvatarUrl = user.getAvatar();
            editFullName.setText(name);

            if (avatarUrl != null) {
                Glide.with(this)
                        .load( "file:///android_asset/" + avatarUrl)
                        .into(imgEditAvatar);
            }

            editMSV.setText(user.getUsername());
            editHoten.setText(user.getFullName());
            editEmail.setText(user.getEmail());
            editNgaysinh.setText(user.getDob());
            editGioitinh.setText(user.getGender());
            editDiachi.setText(user.getAddress());
        }

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleUpdateProfile();
            }
        });

        imgEditProfileArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void handleUpdateProfile() {
        String msv = editMSV.getText().toString().trim();
        String hoten = editHoten.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String ngaysinh = editNgaysinh.getText().toString().trim();
        String gioitinh = editGioitinh.getText().toString().trim();
        String diachi = editDiachi.getText().toString().trim();

        performUpdateProfile(hoten, msv, email, ngaysinh, gioitinh, diachi);
    }

    private void performUpdateProfile(String fullName, String studentId, String email, String birth, String gender, String address) {
        String username = TemporaryLoginInfo.getInstance().getUsername();
        User currentUser = userRepository.getUserByUsername(username);
        if (currentAvatarUrl != null) {
            currentUser.setAvatar(currentAvatarUrl);
        } else {
            currentUser.setAvatar("default_avatar.jpg");
        }
        if (currentUser == null) {
            return;
        }

        if (!fullName.isEmpty()) {
            currentUser.setFullName(fullName);
        }
        if (!studentId.isEmpty()) {
            currentUser.setUsername(studentId);
        }
        if (!email.isEmpty()) {
            currentUser.setEmail(email);
        }
        if (!birth.isEmpty()) {
            currentUser.setDob(birth);
        }
        if (!gender.isEmpty()) {
            currentUser.setGender(gender);
        }
        if (!address.isEmpty()) {
            currentUser.setAddress(address);
        }

        userRepository.updatedProfile(currentUser, username);

        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
        intent.putExtra("user", currentUser);
        startActivity(intent);
        finish();
    }


    public void getWidget(){
        btnEditProfile = findViewById(R.id.btn_edit_profile);
        imgEditProfileArrow = findViewById(R.id.img_edit_profile_arrow);
        imgEditAvatar = findViewById(R.id.img_edit_profile_avatar);
        editFullName = findViewById(R.id.box_edit_profile_username);
        editHoten = findViewById(R.id.box_edit_profile_name);
        editEmail = findViewById(R.id.box_edit_profile_email);
        editMSV = findViewById(R.id.box_edit_profile_msv);
        editNgaysinh = findViewById(R.id.box_edit_profile_birthdate);
        editGioitinh = findViewById(R.id.box_edit_profile_gender);
        editDiachi = findViewById(R.id.box_edit_profile_address);
    }
}