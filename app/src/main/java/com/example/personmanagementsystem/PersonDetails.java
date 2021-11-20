package com.example.personmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;

import com.example.personmanagementsystem.databinding.ActivityPersonDetailsBinding;

public class PersonDetails extends AppCompatActivity {

    SQLiteDatabase database;
    private ActivityPersonDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        database = this.openOrCreateDatabase("Persons",MODE_PRIVATE,null);
        Intent intent = getIntent();
        String info = intent.getStringExtra("info");
        if (info.equals("new")){


            int id = intent.getIntExtra("id", 1);
            try {

                Cursor cursor = database.rawQuery("SELECT * FROM persons WHERE id = ?", new String[]{String.valueOf(id)});

                int nameIx = cursor.getColumnIndex("name");
                int surnameIx = cursor.getColumnIndex("surname");
                int usernameIx = cursor.getColumnIndex("username");
                int birthdayIx = cursor.getColumnIndex("birthday");
                int passwordIx = cursor.getColumnIndex("password");
                while (cursor.moveToNext()) {

                    binding.name.setText(cursor.getString(nameIx));
                    binding.surname.setText(cursor.getString(surnameIx));
                    binding.username.setText(cursor.getString(usernameIx));
                    binding.birthday.setText(cursor.getString(birthdayIx));
                    binding.password.setText(cursor.getString(passwordIx));

                }

                cursor.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        binding.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = intent.getIntExtra("id", 1);
                String name = binding.name.getText().toString();
                String surname = binding.surname.getText().toString();
                String username = binding.username.getText().toString();
                String password = binding.password.getText().toString();
                String birthday = binding.birthday.getText().toString();


                try {
//                    database = this.openOrCreateDatabase("Persons", MODE_PRIVATE, null);
                    String sqlString = "UPDATE persons SET name=?, surname=?, username=? ,password=? ,birthday=?  WHERE id=? ";
                    SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
                    sqLiteStatement.bindString(1, name);
                    sqLiteStatement.bindString(2, surname);
                    sqLiteStatement.bindString(3, username);
                    sqLiteStatement.bindString(4, password);
                    sqLiteStatement.bindString(5, birthday);
                    sqLiteStatement.bindString(6, String.valueOf(id));
                    sqLiteStatement.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(v.getContext(), PersonActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                v.getContext().startActivity(intent);
            }
        });

    }
    }
