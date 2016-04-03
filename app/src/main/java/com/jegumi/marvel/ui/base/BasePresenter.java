package com.jegumi.marvel.ui.base;


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
import com.jegumi.marvel.ui.character.CharacterActivity;
import com.jegumi.marvel.ui.home.MainActivity;

import java.net.UnknownHostException;

public class BasePresenter {

    protected Context context;

    public BasePresenter(Context context) {
        this.context = context;
    }

    public void openCharacter(Character character) {
        Intent intent = new Intent(context, CharacterActivity.class);
        intent.putExtra(MainActivity.EXTRA_CHARACTER, character);
        context.startActivity(intent);
    }
}
