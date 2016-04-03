package com.jegumi.marvel.events;


import com.jegumi.marvel.model.Character;

public class OpenCharacterEvent {

    public Character character;

    public OpenCharacterEvent(Character character) {
        this.character = character;
    }
}
