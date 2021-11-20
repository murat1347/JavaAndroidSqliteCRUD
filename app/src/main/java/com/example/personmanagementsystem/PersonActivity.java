package com.example.personmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.example.personmanagementsystem.databinding.ActivityPersonBinding;

import java.util.ArrayList;

public class PersonActivity extends AppCompatActivity {

    private ActivityPersonBinding binding;
    PersonAdapter personAdapter;
    ArrayList<PersonItems> personArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        personArrayList = new ArrayList<>();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        personAdapter = new PersonAdapter(personArrayList);
        binding.recyclerView.setAdapter(personAdapter);
        getData();
    }

    public void getData(){


        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Persons",MODE_PRIVATE,null);

            Cursor cursor = database.rawQuery("SELECT * FROM persons", null);
            int nameIx = cursor.getColumnIndex("name");
            int idIx = cursor.getColumnIndex("id");

            while (cursor.moveToNext()) {
                String name = cursor.getString(nameIx);
                int id = cursor.getInt(idIx);
                PersonItems personItems = new PersonItems(name,id);
                personArrayList.add(personItems);
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}