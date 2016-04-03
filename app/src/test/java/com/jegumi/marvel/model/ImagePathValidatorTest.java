package com.jegumi.marvel.model;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ImagePathValidatorTest {

    @Test
    public void imagePathValidator_ReturnsTrue() {
        Image image = new Image();
        image.path = "http://www.myimages.com/mytestImage";
        image.extension = "png";

        assertThat(image.getPictureUrl(), equalTo("http://www.myimages.com/mytestImage.png"));
    }
}