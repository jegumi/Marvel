package com.jegumi.marvel.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jegumi.marvel.MarvelApplication;
import com.jegumi.marvel.R;
import com.jegumi.marvel.events.OpenURLEvent;
import com.jegumi.marvel.model.URL;

import java.util.ArrayList;

public class UrlsAdapter extends RecyclerView.Adapter<UrlsAdapter.ItemsViewHolder> {

    private ArrayList<URL> itemsList;

    public UrlsAdapter(ArrayList<URL> items) {
        itemsList = items;
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.url_item, parent, false);
        return new ItemsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemsViewHolder holder, int position) {
        URL urlItem = itemsList.get(position);
        holder.url = urlItem.url;
        holder.title.setText(urlItem.type);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public static class ItemsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public String url;
        public TextView title;
        public int id;

        ItemsViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.title_text_view);
        }

        @Override
        public void onClick(View v) {
            MarvelApplication.getBus().post(new OpenURLEvent(url));
        }
    }
}
