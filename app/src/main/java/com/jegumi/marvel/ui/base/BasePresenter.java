package com.jegumi.marvel.ui.base;


import android.content.Context;
import android.content.Intent;

import com.jegumi.marvel.MarvelApplication;
import com.jegumi.marvel.model.Character;
import com.jegumi.marvel.network.Api;
import com.jegumi.marvel.ui.character.CharacterActivity;
import com.jegumi.marvel.ui.home.MainActivity;

import javax.inject.Inject;

public class BasePresenter {

    protected Context context;
    @Inject
    protected Api api;

    public BasePresenter(Context context) {
        this.context = context;
        MarvelApplication.getMarvelApplication().getComponent().inject(this);
    }

    public void openCharacter(Character character) {
        Intent intent = new Intent(context, CharacterActivity.class);
        intent.putExtra(MainActivity.EXTRA_CHARACTER, character);
        context.startActivity(intent);
    }
}
