package com.jegumi.marvel.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jegumi.marvel.MarvelApplication;
import com.jegumi.marvel.R;
import com.jegumi.marvel.ui.search.SearchActivity;
import com.squareup.otto.Bus;

import javax.inject.Inject;

public class BaseActivity extends AppCompatActivity {

    public static final String EXTRA_CHARACTER = "com.jegumi.marvel.character";

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.search:
                openSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openSearch() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}