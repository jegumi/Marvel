package com.jegumi.marvel.ui.character;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.jegumi.marvel.R;
import com.jegumi.marvel.model.Character;
import com.jegumi.marvel.ui.base.BaseActivity;

public class CharacterActivity extends BaseActivity {
    private static final String TAG = CharacterActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_activity);
        loadFragment((Character)getIntent().getSerializableExtra(EXTRA_CHARACTER));
    }

    private void loadFragment(Character character) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        CharacterFragment startFragment = CharacterFragment.newInstance(character);
        ft.replace(R.id.fragment_container, startFragment, TAG);
        ft.commit();
    }
}