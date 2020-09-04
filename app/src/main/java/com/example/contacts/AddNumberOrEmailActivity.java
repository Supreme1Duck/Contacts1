package com.example.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddNumberOrEmailActivity extends AppCompatActivity {
    private RadioButton phoneRadioButton;
    private RadioButton email;
    private EditText text1;
    private EditText text2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnumber);
        setTitle("Add");
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        phoneRadioButton = findViewById(R.id.check12);
        email = findViewById(R.id.check22);

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
        if (item.getItemId() == R.id.applyAdd) {
            if (text1.getText().toString().trim().equals("") || text2.getText().toString().trim().equals("")) {
                Toast.makeText(AddNumberOrEmailActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
            } else {
                Contact phone = null;
                Intent intent = new Intent();

                if (phoneRadioButton.isChecked()) {
                   phone = new Contact(R.drawable.face, text1.getText().toString(), text2.getText().toString());
                }
                if (email.isChecked()) {
                    phone = new Contact(R.drawable.baseline_language_black_18dp, text1.getText().toString(), text2.getText().toString());
                }

                intent.putExtra("ContactArray", phone);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
