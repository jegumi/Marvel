package com.jegumi.marvel.ui.home;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.jegumi.marvel.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadMainFragment();
    }

    private void loadMainFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(android.R.id.content, MainFragment.newInstance(), TAG);
        ft.commit();
    }
}