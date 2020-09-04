package com.example.contacts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnNoteClickListener {
    private final String TAG = "Phones";
    private final String POSITION_TAG = "position";
    private RecyclerView recycle;
    private TextView main_text;
    private ArrayList<Contact> phones;
    private ContactsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        main_text = findViewById(R.id.main_Text);
        load();
        recycle = findViewById(R.id.recycle);
        FloatingActionButton actionButton = findViewById(R.id.floatingActionButton);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNumberOrEmailActivity.class);
                startActivityForResult(intent, 1);
            }
        };
        actionButton.setOnClickListener(listener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (phones != null && phones.size() != 0) {
            adapter = new ContactsAdapter(phones, this);
            recycle.setAdapter(adapter);
            main_text.setText("");
        } else {
            phones = new ArrayList<>();
            main_text.setText(getString(R.string.nocontacts));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data == null) return;
            phones.add((Contact) data.getSerializableExtra("ContactArray"));
        }
        if (requestCode == 2) {
            assert data != null;
            if (1 != data.getIntExtra("Check", 0)){
                phones.add(data.getIntExtra(POSITION_TAG, 0), (Contact)data.getSerializableExtra(TAG));
            }
        }
    }

    private void save() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(phones);
        editor.putString("task list", json);
        editor.apply();
    }

    private void load() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Contact>>() {
        }.getType();
        phones = gson.fromJson(json, type);
        if (phones == null) {
            main_text.setText(getString(R.string.nocontacts));
            phones = new ArrayList<>();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(TAG, phones);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        phones = (ArrayList<Contact>) savedInstanceState.getSerializable("Phones");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        save();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void OnClick(int position) {
        Intent intent = new Intent(MainActivity.this, EditContact.class);
        intent.putExtra(POSITION_TAG, position);
        intent.putExtra(TAG, phones.get(position));
        phones.remove(phones.get(position));
        startActivityForResult(intent, 2);
    }
}