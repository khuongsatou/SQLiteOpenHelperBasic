package com.example.sqliteopenhelperbasic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class StudentReaderSqlite extends SQLiteOpenHelper {

    public static final String TABLE_NAME ="student";

    public static final String COLUMN_ID ="id";
    public static final String COLUMN_NAME ="name";
    public static final String COLUMN_PHONE ="phone";
    public static final String COLUMN_ADDRESS ="address";

    public StudentReaderSqlite(Context context) {
        super(context, "students.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"(" +COLUMN_ID+ " INTEGER PRIMARY KEY , " +COLUMN_NAME+ " VARCHAR, " +COLUMN_PHONE+ " VARCHAR, " +COLUMN_ADDRESS +" VARCHAR)";
        Log.d("CREATE_TABLE",""+CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insertStudent(Student student){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID,student.getId());
        values.put(COLUMN_NAME,student.getName());
        values.put(COLUMN_PHONE,student.getPhone());
        values.put(COLUMN_ADDRESS,student.getAddress());

        long result = database.insert(TABLE_NAME,null,values);

        return  result;

    }

    public long updateStudent(Student student){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID,student.getId());
        values.put(COLUMN_NAME,student.getName());
        values.put(COLUMN_PHONE,student.getPhone());
        values.put(COLUMN_ADDRESS,student.getAddress());

        long result = database.update(TABLE_NAME,values,COLUMN_ID+"=?",new String[]{student.getId()+""});

        return  result;

    }

    public long deleteStudent(String id){

        SQLiteDatabase database = this.getWritableDatabase();//xin quyền ghi

        long result = database.delete(TABLE_NAME,COLUMN_ID+"=?",new String[]{id});

        return  result;

    }

    public List<Student> getAllStudent(){
        SQLiteDatabase database = this.getReadableDatabase();// đối với select phải xin quyền đọc
        List<Student> students = new ArrayList<Student>();
        String SELECT = "SELECT * FROM "+TABLE_NAME;
        Cursor cursor = database.rawQuery(SELECT,null);

        if (cursor.getCount() > 0){//để cho tối ưu nếu không có dữ liệu
            cursor.moveToFirst();//di chuyển con trỏ đến vị trí đầu tiên
            while (!cursor.isAfterLast()){//nếu là vị trí cuối thì là true

                String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE));
                String address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));

                Student student= new Student();
                student.setId(id);
                student.setName(name);
                student.setAddress(address);
                student.setPhone(phone);

                students.add(student);//Thêm vào mảng
                cursor.moveToNext();//chuyển sang ngăn xếp tiếp theo
            }
            cursor.close();
        }
        return students;
    }


}
