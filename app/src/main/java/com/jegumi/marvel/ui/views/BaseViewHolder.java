package com.jegumi.marvel.ui.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jegumi.marvel.MarvelApplication;
import com.squareup.otto.Bus;

import javax.inject.Inject;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @Inject
    protected Bus bus;

    protected BaseViewHolder(View itemView) {
        super(itemView);
        MarvelApplication.getMarvelApplication().getComponent().inject(this);
        itemView.setOnClickListener(this);
    }

    @Override
    public abstract void onClick(View v);
}
