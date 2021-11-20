package com.example.personmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;

import com.example.personmanagementsystem.databinding.ActivityCreateBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateActivity extends AppCompatActivity {

    private ActivityCreateBinding binding;
    SQLiteDatabase database;
    final Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog datePickerDialog;


protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        binding=ActivityCreateBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
            Log.d("TAG", "onDateSet: girdi");
        }

        private void updateLabel() {
            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            binding.date.setText(sdf.format(myCalendar.getTime()));
        }

    };

    binding.date.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            new DatePickerDialog(CreateActivity.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            Log.d("TAG", "onClick: girdi");
        }
    });


        }
public void save(View view){
        String name=binding.name.getText().toString();
        String surname=binding.surname.getText().toString();
        String username=binding.username.getText().toString();
        String password=binding.password.getText().toString();
        String birthday=binding.date.getText().toString();


        try{
        database=this.openOrCreateDatabase("Persons",MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF NOT EXISTS persons(id INTEGER PRIMARY KEY,name VARCHAR,surname VARCHAR,username VARCHAR,password VARCHAR,birthday VARCHAR)");
        String sqlString="INSERT INTO persons(name,surname,username,password,birthday) VALUES (?,?,?,?,?)";
        SQLiteStatement sqLiteStatement=database.compileStatement(sqlString);
        sqLiteStatement.bindString(1,name);
        sqLiteStatement.bindString(2,surname);
        sqLiteStatement.bindString(3,username);
        sqLiteStatement.bindString(4,password);
        sqLiteStatement.bindString(5,birthday);
        sqLiteStatement.execute();
        }catch(Exception e){
        e.printStackTrace();
        }
        Intent intent=new Intent(CreateActivity.this,MainActivity.class);
        startActivity(intent);
        }


        }