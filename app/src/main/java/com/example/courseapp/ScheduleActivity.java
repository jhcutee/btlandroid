package com.example.courseapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.courseapp.database.DatabaseHelper;
import com.example.courseapp.database.model.Support;
import com.example.courseapp.database.repository.SupportRepository;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {
    Button btndatLich, btnHuy;
    TextInputEditText tiehoTen, tiemsv, tiethoiGian, tiediaDiem, tiesoGio;
    private SupportRepository supportRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_schedule);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        supportRepository = new SupportRepository(databaseHelper);

        getWidgetsControl();

        btndatLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInputs()){
                    String fullName = tiehoTen.getText().toString().trim();
                    String studentId = tiemsv.getText().toString().trim();
                    String time = tiethoiGian.getText().toString().trim();
                    String location = tiediaDiem.getText().toString().trim();
                    String duration = tiesoGio.getText().toString().trim();

                    performSupport(fullName, studentId, time, location, duration);
                    showSuccessDialog();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tiethoiGian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTime();
            }
        });
    }
    private void performSupport(String fullName, String studentId, String time, String location, String duration) {
        Support support = new Support();
        support.setType(fullName);
        support.setUserId(studentId);
        support.setTime(time);
        support.setAddress(location);
        support.setNote(duration);
        Log.d("SupportInfo", "Dữ liệu hỗ trợ đã được thêm vào cơ sở dữ liệu");
        supportRepository.SaveSchedule(support);
        Log.d("SupportInfo", "Dữ liệu hỗ trợ đã được thêm vào cơ sở dữ liệu");
}
    void showTime(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, y, m, d) -> {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(
                            this,
                            (timeView, h, min) -> {
                                Calendar selectedTime = Calendar.getInstance();
                                selectedTime.set(y, m, d, h, min);
                                tiethoiGian.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(selectedTime.getTime()));
                            },
                            hour, minute, true
                    );
                    timePickerDialog.show();
                },
                year, month, day
        );
        datePickerDialog.show();
    }
    private void showSuccessDialog() {

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean validateInputs() {
        String fullName = tiehoTen.getText().toString().trim();
        String studentId = tiemsv.getText().toString().trim();
        String time = tiethoiGian.getText().toString().trim();
        String location = tiediaDiem.getText().toString().trim();
        String duration = tiesoGio.getText().toString().trim();

        boolean isValid = true;

        // Kiểm tra họ tên không được chứa số và ký tự đặc biệt
        String nameRegex = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$";

        if (!fullName.matches(nameRegex)) {
            tiehoTen.setError("Họ tên không hợp lệ");
            isValid = false;
        } else {
            tiehoTen.setError(null);
        }

         //Kiểm tra mã sinh viên phải là số và có ít nhất 8 ký tự
        if (!studentId.matches("\\d{8,}")) {
            tiemsv.setError("Mã sinh viên không hợp lệ");
            isValid = false;
        } else {
            tiemsv.setError(null);
        }

        if (time.isEmpty()) {
            tiethoiGian.setError("Vui lòng nhập thời gian");
            isValid = false;;
        }
        if (location.isEmpty()) {
            tiediaDiem.setError("Vui lòng nhâp địa điểm");
            isValid = false;;
        }
        if (duration.isEmpty()) {
            tiesoGio.setError("Vui lòng nhập số giờ");
            isValid = false;;
        }
        return isValid;
    }
    private void getWidgetsControl(){
        btndatLich = (Button) findViewById(R.id.btn_datlich);
        btnHuy = (Button) findViewById(R.id.btn_huy);
        tiehoTen = (TextInputEditText) findViewById(R.id.tie_hoten);
        tiemsv = (TextInputEditText) findViewById(R.id.tie_msv);
        tiesoGio= (TextInputEditText) findViewById(R.id.tie_sogio);
        tiethoiGian = (TextInputEditText) findViewById(R.id.tie_thoigian);
        tiediaDiem = (TextInputEditText) findViewById(R.id.tie_diadiem);
    }
}