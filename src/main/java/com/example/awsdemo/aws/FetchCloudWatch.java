package com.example.awsdemo.aws;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.List;

public class FetchCloudWatch {
    public static void fetchData(String namespace) {
//        final AmazonCloudWatch cw =
//                AmazonCloudWatchClientBuilder.defaultClient();
//
//        ListMetricsRequest request = new ListMetricsRequest()
//                .withMetricName(name)
//                .withNamespace(namespace);
//        System.out.printf("request.toString=%s", request.toString());
//        boolean done = false;
//
//        while(!done) {
//            ListMetricsResult response = cw.listMetrics(request);
//            System.out.printf("done=%s,listMetrics.size=%s",done, response.getMetrics().size());
//            for(Metric metric : response.getMetrics()) {
//                System.out.printf(
//                        "Retrieved metric %s", metric.getMetricName());
//            }
//
//            request.setNextToken(response.getNextToken());
//
//            if(response.getNextToken() == null) {
//                done = true;
//            }
//        }

        List<Dimension> dimensions = new ArrayList<Dimension>();
        Dimension dimension = new Dimension();
        dimension.setName("InstanceId");
        dimension.setValue("i-0cef3ed85e5e905a3");
        dimensions.add(dimension);
        String[] metrics = { "NetworkIn", "NetworkOut",
//                             "DiskReadBytes", "DiskWriteBytes", "DiskReadOps", "DiskWriteOps",
//                             "StatusCheckFailed", "StatusCheckFailed_Instance", "StatusCheckFailed_System",
                             "CPUCreditUsage", "CPUCreditBalance", "CPUSurplusCreditBalance", "CPUSurplusCreditsCharged"};
        StringBuffer sb = new StringBuffer();
        for (String name : metrics) {
            GetMetricStatisticsRequest getMetricStatisticsRequest = new GetMetricStatisticsRequest()
                    .withStartTime(DateTime.now(DateTimeZone.UTC).minusMinutes(10).toDate())
                    .withNamespace(namespace)
                    .withDimensions(dimensions)
                    .withPeriod(60)
                    .withMetricName(name)
                    .withStatistics("Average")
                    .withEndTime(DateTime.now(DateTimeZone.UTC).minusMinutes(1).toDate());
            GetMetricStatisticsResult getMetricStatisticsResult = AmazonCloudWatchClient.builder().build().getMetricStatistics(getMetricStatisticsRequest);
            sb.append(name).append(":").append(getMetricStatisticsResult.getDatapoints()).append("\n");
        }
        sb.deleteCharAt(sb.length()-1);
        System.out.println("###############RESULT START###############");
        System.out.println(sb.toString());
        System.out.println("###############RESULT END###############");
    }

    public static void main(String[] args) {
        fetchData("AWS/EC2");
    }
}
