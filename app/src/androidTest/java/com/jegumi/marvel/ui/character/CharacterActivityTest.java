package com.jegumi.marvel.ui.character;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.text.TextUtils;
import android.widget.TextView;

import com.jegumi.marvel.R;
import com.jegumi.marvel.model.Character;
import com.jegumi.marvel.ui.base.BaseActivity;

import org.junit.Before;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class CharacterActivityTest extends ActivityInstrumentationTestCase2<CharacterActivity> {

    private CharacterActivity characterActivity;

    private CollapsingToolbarLayout collapsingToolbar;

    private TextView description;
    private Character character;

    public CharacterActivityTest() {
        super(CharacterActivity.class);
    }

    private void setFakeCharacter() {
        character = new Character();
        character.name = "Hulk";
        character.description = "Caught in a gamma bomb explosion while trying to save the life of a teenager.";
    }

    @Override
    @Before
    protected void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());

        setFakeCharacter();

        Intent intent = new Intent(getInstrumentation().getTargetContext(), CharacterActivity.class);
        intent.putExtra(BaseActivity.EXTRA_CHARACTER, character);
        setActivityIntent(intent);
        characterActivity = getActivity();

        description = (TextView) characterActivity.findViewById(R.id.description_text_view);
        collapsingToolbar = (CollapsingToolbarLayout) characterActivity.findViewById(R.id.collapsing_toolbar);
    }

    public void testPreconditions() {
        assertThat("characterActivity is null", characterActivity, is(notNullValue()));
        assertThat("collapsingToolbar is null", collapsingToolbar, is(notNullValue()));
        assertThat("description is null", description, is(notNullValue()));
    }

    public void testDescriptionView_labelText() {
        String expected = character.description;
        String actual = description.getText().toString();
        assertThat("description contains wrong text", expected, is(actual));
    }

    public void testCollapsingTollser_labelText() {
        String expected = character.name;
        CharSequence title = collapsingToolbar.getTitle();
        String actual = TextUtils.isEmpty(title) ? "" : title.toString();
        assertThat("collapsingToolbar titles contains wrong text", expected, is(actual));
    }
}
