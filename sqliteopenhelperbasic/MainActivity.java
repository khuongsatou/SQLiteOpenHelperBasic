package com.example.sqliteopenhelperbasic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvID,tvName,tvPhone,tvAddress;
    private Button btnThem, btnSua, btnXoa, btnDanhSach;

    private StudentReaderSqlite studentReaderSqlite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentReaderSqlite = new StudentReaderSqlite(MainActivity.this);

        Radiation();
        Event();

    }

    private void Event() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertStudent();
            }
        });

        btnDanhSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectAllStudent();
            }
        });
    }

    private void InsertStudent() {
        Student student = new Student();
        student.setId("" + System.currentTimeMillis());
        student.setName("Khương Satou");
        student.setPhone("0356241963");
        student.setAddress("Long Thạnh");

        long result = studentReaderSqlite.insertStudent(student);
        if (result > 0) {
            Toast.makeText(MainActivity.this, "Thêm Thành Công", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(MainActivity.this, "Thêm Thành Công", Toast.LENGTH_LONG).show();
        }
    }

    private void UpdateStudent() {
    }// Viết sau

    private void DeleteStudent() {

    }// Viết Sau

    private void SelectAllStudent() {
        List<Student> students = studentReaderSqlite.getAllStudent();

        String id = "";
        String name = "";
        String phone = "";
        String address = "";
        for (int i = 0 ; i< students.size() ; i++){
            id+="<br>"+students.get(i).getId();
            name += "\n"+students.get(i).getName();
            phone += "\n"+students.get(i).getPhone();
            address += "\n"+students.get(i).getAddress();
        }

        tvID.setText(android.text.Html.fromHtml(id));//Chuyển thành HTML
        tvName.setText(name);
        tvPhone.setText(phone);
        tvAddress.setText(address);
    }

    private void Radiation() {
        tvID = (TextView) findViewById(R.id.tvID);
        tvName = (TextView) findViewById(R.id.tvName);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        tvAddress = (TextView) findViewById(R.id.tvAddress);

        btnThem = (Button) findViewById(R.id.btnThem);
        btnSua = (Button) findViewById(R.id.btnSua);
        btnXoa = (Button) findViewById(R.id.btnXoa);
        btnDanhSach = (Button) findViewById(R.id.btnDanhSach);

    }

    @Override
    protected void onDestroy() {
        studentReaderSqlite.close();//đóng database tắt ứng dụng để không đóng mở nhiều lần
        super.onDestroy();
    }
}
