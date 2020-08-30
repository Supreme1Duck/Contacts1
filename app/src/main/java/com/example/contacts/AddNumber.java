package com.example.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;


public class AddNumber extends AppCompatActivity {
    private ImageView image;
    private RadioButton Phone;
    private RadioButton Email;
private ArrayList<Phone> phones;
private EditText text1;
private EditText text2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnumber);
        setTitle("Add");
        phones = (ArrayList<Phone>) getIntent().getSerializableExtra("ContactArray");
        image = findViewById(R.id.KartinkaKontakta);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        Phone = findViewById(R.id.check12);
        Email = findViewById(R.id.check22);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.applyAdd){
            if (text1.getText().toString().trim().equals("") || text2.getText().toString().trim().equals("")) {

                Toast.makeText(AddNumber.this, "Something wrong", Toast.LENGTH_SHORT).show();

            }else{

                Intent intent = new Intent();

                if (Phone.isChecked()){
                    phones.add(new Phone(R.drawable.face, text1.getText().toString(), text2.getText().toString()));
                }
                if (Email.isChecked()){
                    phones.add(new Phone(R.drawable.baseline_language_black_18dp, text1.getText().toString(), text2.getText().toString()));
                }

                intent.putExtra("ContactArray", phones);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
