package com.example.juancarlos.quicksaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.juancarlos.quicksaapp.Utils.ViajeAdapter;
import com.example.juancarlos.quicksaapp.Utils.ViajeDBHelper;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ViajeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        populaterecyclerView();


    }

    private void populaterecyclerView() {
        ViajeDBHelper dbHelper = new ViajeDBHelper(this);
        adapter = new ViajeAdapter(dbHelper.peopleList(), this, mRecyclerView);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.addMenu:
                AgregarViaje();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void AgregarViaje() {
        Intent intent = new Intent(MainActivity.this, NuevoViajeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

}
