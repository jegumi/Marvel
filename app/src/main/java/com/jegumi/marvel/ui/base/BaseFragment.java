package com.jegumi.marvel.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jegumi.marvel.MarvelApplication;
import com.jegumi.marvel.R;
import com.jegumi.marvel.network.Api;
import com.squareup.otto.Bus;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    protected Api api;
    protected Bus bus;
    protected Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = MarvelApplication.getApi();
        bus = MarvelApplication.getBus();
    }

    public abstract int getResId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getResId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        api.cancelAllRequests();
        bus.unregister(this);
    }

    protected void initActionbar(boolean isHome) {
        AppCompatActivity parentActivity = (AppCompatActivity) getActivity();
        toolbar = (Toolbar) parentActivity.findViewById(R.id.toolbar);
        if (toolbar != null) {
            parentActivity.setSupportActionBar(toolbar);
            ActionBar actionBar = parentActivity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayShowHomeEnabled(!isHome);
                actionBar.setDisplayHomeAsUpEnabled(!isHome);
            }
        }
    }
}
