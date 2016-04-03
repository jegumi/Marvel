package com.jegumi.marvel.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jegumi.marvel.MarvelApplication;
import com.jegumi.marvel.R;
import com.jegumi.marvel.events.ShowItemDialogEvent;
import com.jegumi.marvel.model.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {

    private ArrayList<Item> itemsList;
    private Context context;

    public ItemsAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        itemsList = items;
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.generic_item, parent, false);
        return new ItemsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemsViewHolder holder, int position) {
        Item item = itemsList.get(position);
        holder.item = item;
        holder.name.setText(item.title);
        if (item.thumbnail != null) {
            Picasso.with(context)
                    .load(item.thumbnail.getPictureUrl())
                    .placeholder(R.drawable.marvel_placeholder)
                    .into(holder.thumbnail);
        } else {
            Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.marvel_placeholder, null);
            holder.thumbnail.setImageDrawable(drawable);
        }
    }

    public void updateData(ArrayList<Item> items) {
        itemsList.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public static class ItemsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Item item;
        public TextView name;
        public ImageView thumbnail;
        public int id;

        ItemsViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = (TextView) itemView.findViewById(R.id.name_text_view);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        }

        @Override
        public void onClick(View v) {
            MarvelApplication.getBus().post(new ShowItemDialogEvent(item));
        }
    }
}
