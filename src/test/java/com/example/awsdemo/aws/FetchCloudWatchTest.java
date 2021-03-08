package com.example.awsdemo.aws;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FetchCloudWatchTest {

    @Test
    void fetchData() {
        // 參數不知道給啥
         new FetchCloudWatch().fetchData("AWS/EC2");
    }
}