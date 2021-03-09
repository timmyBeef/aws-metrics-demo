package com.example.awsdemo.aws;

import com.example.awsdemo.service.FetchCloudWatch;
import org.junit.jupiter.api.Test;

class FetchCloudWatchTest {

    @Test
    void fetchData() {
        // 參數不知道給啥
         new FetchCloudWatch().fetchData("AWS/EC2");
    }
}