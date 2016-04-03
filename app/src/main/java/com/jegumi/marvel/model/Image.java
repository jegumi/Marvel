package com.jegumi.marvel.model;

import java.io.Serializable;

public class Image implements Serializable {
    public String path;
    public String extension;

    public String getPictureUrl() {
        return path + "." + extension;
    }
}
