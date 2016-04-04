package com.jegumi.marvel;

import com.jegumi.marvel.ui.base.BaseFragment;
import com.jegumi.marvel.ui.base.BasePresenter;
import com.jegumi.marvel.ui.search.SearchActivity;
import com.jegumi.marvel.ui.views.BaseViewHolder;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { AppModule.class })
public interface AppComponent {
    void inject(MarvelApplication app);
    void inject(SearchActivity activity);
    void inject(BaseFragment baseFragment);
    void inject(BaseViewHolder baseViewHolder);
    void inject(BasePresenter basePresenter);
}