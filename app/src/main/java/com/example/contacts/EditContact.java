package com.example.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditContact extends AppCompatActivity {
    private final String POSITION_TAG = "position";
    private final String TAG = "Phones";
    private EditText edit_name, edit_number;
    private int SendedPosition;
    private Contact phoneToEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle("Edit");
        edit_name = findViewById(R.id.text_name);
        edit_number = findViewById(R.id.text_number);
        Button button_remove = findViewById(R.id.button_remove);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();

        SendedPosition = intent.getIntExtra(POSITION_TAG, 0);
        phoneToEdit = (Contact) intent.getSerializableExtra(TAG);

        assert phoneToEdit != null;
        edit_number.setText(phoneToEdit.getPhoneNumber());
        edit_name.setText(phoneToEdit.getHolderName());

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("Check", 1);
                intent.putExtra(POSITION_TAG, SendedPosition);
                setResult(RESULT_OK, intent);
                finish();
            }
        };
        button_remove.setOnClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_number_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.apply) {
            int ResourceID = phoneToEdit.getResourceId();
            phoneToEdit = new Contact(ResourceID, edit_number.getText().toString(), edit_name.getText().toString());
            Intent intent_back = new Intent();
            intent_back.putExtra(TAG, phoneToEdit);
            intent_back.putExtra(POSITION_TAG, getIntent().getIntExtra(POSITION_TAG, 0));
            setResult(RESULT_OK, intent_back);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
