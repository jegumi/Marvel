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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ItemsViewHolder> {

    private ArrayList<Character> itemsList;
    private Context context;

    public SearchAdapter(Context context, ArrayList<Character> items) {
        this.context = context;
        itemsList = items;
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new ItemsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemsViewHolder holder, int position) {
        Character character = itemsList.get(position);
        holder.character = character;
        holder.name.setText(character.name);
        if (character.thumbnail != null) {
            Picasso.with(context).load(character.thumbnail.getPictureUrl()).into(holder.thumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public static class ItemsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView thumbnail;
        public TextView name;
        public Character character;

        ItemsViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            name = (TextView) itemView.findViewById(R.id.name_text_view);
        }

        @Override
        public void onClick(View v) {
            MarvelApplication.getBus().post(new OpenCharacterEvent(character));
        }
    }
}
