package com.jegumi.marvel.ui.search;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

import com.jegumi.marvel.MarvelApplication;
import com.jegumi.marvel.R;
import com.jegumi.marvel.events.DoSearchEvent;
import com.jegumi.marvel.ui.base.BaseActivity;
import com.squareup.otto.Bus;

import javax.inject.Inject;

public class SearchActivity extends BaseActivity {
    private static final String TAG = SearchActivity.class.getName();

    @Inject
    protected Bus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MarvelApplication) getApplication()).getComponent().inject(this);
        loadMainFragment();
    }

    private void loadMainFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(android.R.id.content, SearchFragment.newInstance(), TAG);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        initSearch(menu);
        return true;
    }

    public void initSearch(Menu menu) {
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
        search.setQueryHint(getString(R.string.search_hint));
        search.setFocusable(true);
        search.setIconified(false);
        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                bus.post(new DoSearchEvent(query));
                return true;
            }
        });
        search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                bus.post(new DoSearchEvent(null));
                return true;
            }
        });
    }
}