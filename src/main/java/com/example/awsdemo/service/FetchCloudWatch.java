package com.example.awsdemo.service;

import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult;
import com.example.awsdemo.dto.FetchResponse;
import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.stereotype.Service;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class FetchCloudWatch {

    private static final String[] METRICS = {
            "NetworkIn", "NetworkOut",
//                             "DiskReadBytes", "DiskWriteBytes", "DiskReadOps", "DiskWriteOps",
//                             "StatusCheckFailed", "StatusCheckFailed_Instance", "StatusCheckFailed_System",
            "CPUCreditUsage", "CPUCreditBalance",
//                "CPUSurplusCreditBalance", "CPUSurplusCreditsCharged"
    };

    private static final Map<String, Double> THRESHOLD = new ImmutableMap.Builder<String, Double>()
            .put("NetworkIn", 2000.0)
            .put("NetworkOut", 2000.0)
            .put("CPUCreditUsage", 10.0)
            .put("CPUCreditBalance", 10.0)
            .build();

    public List<FetchResponse> fetchData(String namespace) {

        List<Dimension> dimensions = new ArrayList<Dimension>();
        Dimension dimension = new Dimension();
        dimension.setName("InstanceId");
        dimension.setValue("i-0cef3ed85e5e905a3");
        dimensions.add(dimension);

        Map<String, List<Datapoint>> metricsMap = new HashMap<>();

        StringBuffer sb = new StringBuffer();
        for (String name : METRICS) {
            GetMetricStatisticsRequest getMetricStatisticsRequest = new GetMetricStatisticsRequest()
                    .withStartTime(DateTime.now(DateTimeZone.UTC).minusMinutes(10).toDate())
                    .withNamespace(namespace)
                    .withDimensions(dimensions)
                    .withPeriod(60)
                    .withMetricName(name)
                    .withStatistics("Average")
                    .withEndTime(DateTime.now(DateTimeZone.UTC).minusMinutes(1).toDate());

            GetMetricStatisticsResult getMetricStatisticsResult =
                    AmazonCloudWatchClient.builder().build()
                            .getMetricStatistics(getMetricStatisticsRequest);

            List<Datapoint> datapoints = getMetricStatisticsResult.getDatapoints();
            metricsMap.put(name, datapoints);

            sb.append(name)
                    .append(":")
                    .append(datapoints)
                    .append("\n");

        }
        sb.deleteCharAt(sb.length() - 1);
        log.info("###############RESULT START###############");
        log.info(sb.toString());
        log.info("###############RESULT END###############");

        return isOverThreshHold(metricsMap);
    }

    private List<FetchResponse> isOverThreshHold(Map<String, List<Datapoint>> metricsMap) {
        List<FetchResponse> result = new ArrayList<>();
        for (String key : METRICS) {
            boolean isOverThreshHold = metricsMap.get(key)
                    .stream()
                    .map(d -> d.getAverage())
                    .anyMatch(avg -> avg > THRESHOLD.get(key).doubleValue());

            result.add(FetchResponse.builder()
                    .name(key)
                    .value(isOverThreshHold)
                    .build()
            );
        }
        return result;
    }
}
