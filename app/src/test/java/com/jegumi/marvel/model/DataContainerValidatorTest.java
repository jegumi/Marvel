package com.jegumi.marvel.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DataContainerValidatorTest {

    @Test
    public void dataContainerConsistencyDataValidator_ReturnsTrue() {
        DataContainer dataContainer = new DataContainer();
        dataContainer.count = 10;
        dataContainer.results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataContainer.results.add(new Character());
        }

        assertThat(dataContainer.count, equalTo(dataContainer.results.size()));
    }
}