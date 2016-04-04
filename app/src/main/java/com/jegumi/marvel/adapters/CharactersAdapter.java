package com.jegumi.marvel.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jegumi.marvel.MarvelApplication;
import com.jegumi.marvel.R;
import com.jegumi.marvel.events.OpenCharacterEvent;
import com.jegumi.marvel.model.Character;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder> {

    private ArrayList<Character> characterList;
    private Context context;

    public CharactersAdapter(Context context, ArrayList<Character> characters) {
        this.context = context;
        characterList = characters;
    }

    @Override
    public CharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_item, parent, false);
        return new CharacterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CharacterViewHolder holder, int position) {
        Character character = characterList.get(position);
        holder.character = character;
        holder.id = character.id;
        holder.name.setText(character.name);
        Picasso.with(context).load(character.thumbnail.getPictureUrl()).into(holder.thumbnail);
    }

    public void addElements(ArrayList<Character> characters) {
        characterList.addAll(characters);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    public static class CharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public Character character;
        public ImageView thumbnail;
        public int id;

        CharacterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = (TextView) itemView.findViewById(R.id.name_text_view);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        }

        @Override
        public void onClick(View v) {
            MarvelApplication.getBus().post(new OpenCharacterEvent(character));
        }
    }
}
