package com.jegumi.marvel.ui.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jegumi.marvel.R;
import com.jegumi.marvel.events.DoSearchEvent;
import com.jegumi.marvel.events.OpenCharacterEvent;
import com.jegumi.marvel.ui.base.BaseFragment;
import com.squareup.otto.Subscribe;

import butterknife.Bind;

public class SearchFragment extends BaseFragment {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.error_message_text_view)
    TextView errorMessageTextView;

    private SearchPresenter searchPresenter;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchPresenter = new SearchPresenter(getContext());
    }

    @Override
    public int getResId() {
        return R.layout.search;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initActionbar(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Subscribe
    public void doSearch(DoSearchEvent event) {
        searchPresenter.loadSearch(recyclerView, errorMessageTextView, event.name);
    }

    @Subscribe
    public void openCharacter(OpenCharacterEvent event) {
        searchPresenter.openCharacter(event.character);
    }
}
