package com.jegumi.marvel.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Character implements Serializable {
    public int id;
    public String name;
    public String description;
    public ArrayList<URL> urls;
    public Image thumbnail;
    public ItemList comics;
    public ItemList stories;
    public ItemList events;
    public ItemList series;
}
