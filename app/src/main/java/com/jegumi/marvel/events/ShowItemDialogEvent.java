package com.jegumi.marvel.events;

import com.jegumi.marvel.model.Item;

public class ShowItemDialogEvent {

    public Item item;

    public ShowItemDialogEvent(Item item) {
        this.item = item;
    }

}
