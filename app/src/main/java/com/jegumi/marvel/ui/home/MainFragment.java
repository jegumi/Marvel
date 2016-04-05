package com.jegumi.marvel.ui.home;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jegumi.marvel.R;
import com.jegumi.marvel.adapters.CharactersAdapter;
import com.jegumi.marvel.events.OpenCharacterEvent;
import com.jegumi.marvel.model.Character;
import com.jegumi.marvel.ui.base.BaseFragment;
import com.jegumi.marvel.ui.views.EndlessRecyclerOnScrollListener;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;

public class MainFragment extends BaseFragment {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.loading_progress_bar)
    ProgressBar loadingProgressBar;
    @Bind(R.id.error_message_text_view)
    TextView errorMessage;

    private MainPresenter mainPresenter;
    private CharactersAdapter adapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainPresenter = new MainPresenter(getContext());
    }

    @Override
    public int getResId() {
        return R.layout.main_fragment_layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initActionbar(true);
        GridLayoutManager llm = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.num_columns));
        adapter = new CharactersAdapter(getContext(), new ArrayList<Character>());
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(llm) {
            @Override
            public void onLoadMore(int current_page) {
                mainPresenter.loadCharacters(adapter, errorMessage, loadingProgressBar, current_page);
            }
        });
        mainPresenter.loadCharacters(adapter, errorMessage, loadingProgressBar, 0);
    }

    @Subscribe
    public void openCharacter(OpenCharacterEvent event) {
        mainPresenter.openCharacter(event.character);
    }
}
