package com.jegumi.marvel.ui.home;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jegumi.marvel.MarvelApplication;
import com.jegumi.marvel.R;
import com.jegumi.marvel.adapters.CharactersAdapter;
import com.jegumi.marvel.model.DataWrapper;
import com.jegumi.marvel.network.Api;
import com.jegumi.marvel.ui.base.BasePresenter;

import java.net.UnknownHostException;

public class MainPresenter extends BasePresenter {

    private static final String TAG = MainPresenter.class.getSimpleName();
    private Api api;

    public MainPresenter(Context context) {
        super(context);
        api = MarvelApplication.getApi();
    }

    public void loadCharacters(final CharactersAdapter adapter, final TextView errorTextView, final ProgressBar progressBar, int page) {
        String url = api.getCharactersEndPoint(page);
        api.loadCharacters(url, new Response.Listener<DataWrapper>() {
            @Override
            public void onResponse(DataWrapper response) {
                progressBar.setVisibility(View.GONE);
                if (response.data.results.isEmpty()) {
                    errorTextView.setVisibility(View.VISIBLE);
                    errorTextView.setText(R.string.no_results);
                    return;
                }
                errorTextView.setVisibility(View.GONE);
                adapter.addElements(response.data.results);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "Error loading list", volleyError);
                int resIdMessage = volleyError.getCause() instanceof UnknownHostException ?
                        R.string.error_no_connectivity : R.string.error_loading_items;

                errorTextView.setVisibility(View.VISIBLE);
                errorTextView.setText(resIdMessage);
            }
        });
    }
}
