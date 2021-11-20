package com.example.personmanagementsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personmanagementsystem.databinding.RecyclerRowBinding;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonHolder> {


    ArrayList<PersonItems> PersonItemList;

    public PersonAdapter(ArrayList<PersonItems> PersonItemList) {
        this.PersonItemList = PersonItemList;

    }

    @Override
    public PersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PersonHolder(recyclerRowBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull PersonHolder holder, int position) {

        Button button_update = holder.binding.btnUpdate;
        button_update.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(holder.itemView.getContext(), PersonDetails.class);
                        intent.putExtra("id", PersonItemList.get(position).id);
                        intent.putExtra("info", "new");
                        holder.itemView.getContext().startActivity(intent);
                    }
                }
        );
        Button button_delete = holder.binding.btnDelete;
        button_delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = PersonItemList.get(position).id;
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("Delete");
                        builder.setMessage("Do you want to delete record?");
                        builder.setNegativeButton("No", null);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SQLiteDatabase database;
                                database = v.getContext().openOrCreateDatabase("Persons", MODE_PRIVATE, null);
                                String sqlString = "DELETE FROM persons WHERE id = ?";
                                SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
                                sqLiteStatement.bindString(1, String.valueOf(PersonItemList.get(position).id));
                                sqLiteStatement.execute();
                                Intent intent = new Intent(v.getContext(), PersonActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                v.getContext().startActivity(intent);
                            }
                        });
                        builder.show();

                    }
                }
        );
        holder.binding.recylerViewTextView.setText(PersonItemList.get(position).name);

    }

    @Override
    public int getItemCount() {
        return PersonItemList.size();
    }

    public class PersonHolder extends RecyclerView.ViewHolder {

        private RecyclerRowBinding binding;

        public PersonHolder(@NonNull RecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
