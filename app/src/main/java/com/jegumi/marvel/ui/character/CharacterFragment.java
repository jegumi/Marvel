package com.jegumi.marvel.ui.character;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jegumi.marvel.R;
import com.jegumi.marvel.events.OpenURLEvent;
import com.jegumi.marvel.events.ShowItemDialogEvent;
import com.jegumi.marvel.helpers.DisplayHelper;
import com.jegumi.marvel.model.Character;
import com.jegumi.marvel.ui.base.BaseFragment;
import com.jegumi.marvel.ui.home.MainActivity;
import com.jegumi.marvel.ui.views.TrapezoidTransformation;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

public class CharacterFragment extends BaseFragment {

    @Bind(R.id.character_image_view)
    ImageView imageView;
    @Bind(R.id.description_text_view)
    TextView descriptionTextView;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    private CharacterPresenter characterPresenter;

    public static CharacterFragment newInstance(Character character) {
        CharacterFragment characterFragment = new CharacterFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(MainActivity.EXTRA_CHARACTER, character);
        characterFragment.setArguments(bundle);
        return characterFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        characterPresenter = new CharacterPresenter(getContext());
    }

    @Override
    public int getResId() {
        return R.layout.character;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initActionbar(false);
        if (Build.VERSION.SDK_INT >= 21) {
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        Character character = (Character) getArguments().getSerializable(MainActivity.EXTRA_CHARACTER);
        if (character != null) {
            setCharacter(view, character);
            collapsingToolbarLayout.setTitle(character.name);
        }

        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
    }

    private void setCharacter(View view, Character character) {
        int width = DisplayHelper.getWidthScreen(getActivity());
        int height = getContext().getResources().getDimensionPixelSize(R.dimen.character_height);
        int diagonalSize = getContext().getResources().getDimensionPixelSize(R.dimen.character_diagonal_size);
        Picasso.with(getContext())
                .load(character.thumbnail.getPictureUrl())
                .resize(width, height)
                .centerCrop()
                .transform(new TrapezoidTransformation(diagonalSize, true, Color.RED))
                .into(imageView);

        descriptionTextView.setText(character.description);
        characterPresenter.setSection(view, R.id.comics_container, R.string.comics_title, character.comics);
        characterPresenter.setSection(view, R.id.series_container, R.string.series_title, character.series);
        characterPresenter.setSection(view, R.id.events_container, R.string.events_title, character.events);
        characterPresenter.setSection(view, R.id.stories_container, R.string.stories_title, character.stories);
        characterPresenter.setUrls(view, R.id.links_container, R.string.links_title, character.urls);
    }

    @Subscribe
    public void showComic(ShowItemDialogEvent event) {
        characterPresenter.showItemDialog(event.item);
    }

    @Subscribe
    public void openUrl(OpenURLEvent event) {
        characterPresenter.openUrl(event.url);
    }
}
