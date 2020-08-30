package com.example.contacts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnNoteClickListener  {
    private RecyclerView recycle;
    private TextView main_text;
    ArrayList<Phone> phones;
    PhoneAdapter adapter;
    FloatingActionButton actionButton;
    ImageView KartinkaKontakta;
    OnNoteClickListener listener;
    private final String TAG = "Phones";
    private final String POSITION_TAG = "position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        main_text = findViewById(R.id.main_Text);
        Load();
        recycle = findViewById(R.id.recycle);
        KartinkaKontakta = findViewById(R.id.KartinkaKontakta);
        actionButton = findViewById(R.id.floatingActionButton);

        recycle.setLayoutManager(new LinearLayoutManager(this));



        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNumber.class);
                intent.putExtra("ContactArray", phones);
                startActivityForResult(intent,1);
            }
        };
        actionButton.setOnClickListener(listener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (phones != null && phones.size()!=0) {
            adapter = new PhoneAdapter(phones, this);
            recycle.setAdapter(adapter);
            main_text.setText("");
        }else{
            phones = new ArrayList<>();
            main_text.setText(getString(R.string.nocontacts));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data == null) return;
            phones = (ArrayList<Phone>) data.getSerializableExtra("ContactArray");
            //Objects.requireNonNull(recycle.getAdapter()).notifyDataSetChanged();
        }
        if (requestCode ==2){
            phones = (ArrayList<Phone>) data.getSerializableExtra(TAG);
        }
    }

    private void Save(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(phones);
        editor.putString("task list", json);
        editor.apply();
    }

    private void Load(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Phone>>(){}.getType();
        phones = gson.fromJson(json, type);

        if (phones == null){
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
        phones = (ArrayList<Phone>) savedInstanceState.getSerializable("Phones");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Save();
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
        Intent intent = new Intent(MainActivity.this, EditNumber.class);
        intent.putExtra("position", position);
        intent.putExtra(TAG, phones);
        startActivityForResult(intent, 2);
    }

}