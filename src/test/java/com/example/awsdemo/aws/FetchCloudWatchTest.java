package com.example.awsdemo.aws;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FetchCloudWatchTest {

    @Test
    void fetchData() {
        new FetchCloudWatch().fetchData();
    }
}