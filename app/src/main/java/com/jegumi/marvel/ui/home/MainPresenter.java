package com.jegumi.marvel.ui.home;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jegumi.marvel.MarvelApplication;
import com.jegumi.marvel.R;
import com.jegumi.marvel.adapters.CharactersAdapter;
import com.jegumi.marvel.model.Character;
import com.jegumi.marvel.model.DataWrapper;
import com.jegumi.marvel.network.Api;
import com.jegumi.marvel.ui.base.BasePresenter;
import com.jegumi.marvel.ui.character.CharacterActivity;

import java.net.UnknownHostException;

public class MainPresenter extends BasePresenter {

    private static final String TAG = MainPresenter.class.getSimpleName();
    private Api api;

    public MainPresenter(Context context) {
        super(context);
        api = MarvelApplication.getApi();
    }

    public void loadCharacters(final CharactersAdapter adapter, int page) {
        String url = api.getCharactersEndPoint(page);
        api.loadCharacters(url, new Response.Listener<DataWrapper>() {
            @Override
            public void onResponse(DataWrapper response) {
                adapter.addElements(response.data.results);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Error loading list", volleyError);
                int resIdMessage = volleyError.getCause() instanceof UnknownHostException ?
                        R.string.error_no_connectivity : R.string.error_loading_items;

                Toast.makeText(context, context.getString(resIdMessage), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
