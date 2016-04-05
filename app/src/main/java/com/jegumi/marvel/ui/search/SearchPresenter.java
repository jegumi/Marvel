package com.jegumi.marvel.ui.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jegumi.marvel.R;
import com.jegumi.marvel.adapters.SearchAdapter;
import com.jegumi.marvel.model.Character;
import com.jegumi.marvel.model.DataWrapper;
import com.jegumi.marvel.ui.base.BasePresenter;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class SearchPresenter extends BasePresenter {

    private static final String TAG = SearchPresenter.class.getSimpleName();

    public SearchPresenter(Context context) {
        super(context);
    }

    public void loadSearch(final RecyclerView recyclerView, final TextView errorTextView, String name) {
        if (TextUtils.isEmpty(name)) {
            errorTextView.setText("");
            recyclerView.setAdapter(new SearchAdapter(context, new ArrayList<Character>()));
            return;
        }

        String url = api.getSearchEndPoint(name);
        api.loadCharacters(url, new Response.Listener<DataWrapper>() {
            @Override
            public void onResponse(DataWrapper response) {
                recyclerView.setAdapter(new SearchAdapter(context, response.data.results));
                if (response.data.results.isEmpty()) {
                    errorTextView.setText(R.string.no_results);
                } else {
                    errorTextView.setText("");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Log.e(TAG, "Error loading list", volleyError);
                int resIdMessage = volleyError.getCause() instanceof UnknownHostException ?
                        R.string.error_no_connectivity : R.string.error_loading_items;
                errorTextView.setText(resIdMessage);
            }
        });
    }
}
