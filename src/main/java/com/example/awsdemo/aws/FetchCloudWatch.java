package com.example.awsdemo.aws;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.ListMetricsRequest;
import com.amazonaws.services.cloudwatch.model.ListMetricsResult;
import com.amazonaws.services.cloudwatch.model.Metric;

public class FetchCloudWatch {
    public void fetchData(String name, String namespace) {
        final AmazonCloudWatch cw =
                AmazonCloudWatchClientBuilder.defaultClient();

        ListMetricsRequest request = new ListMetricsRequest()
                .withMetricName(name)
                .withNamespace(namespace);

        boolean done = false;

        while(!done) {
            ListMetricsResult response = cw.listMetrics(request);

            for(Metric metric : response.getMetrics()) {
                System.out.printf(
                        "Retrieved metric %s", metric.getMetricName());
            }

            request.setNextToken(response.getNextToken());

            if(response.getNextToken() == null) {
                done = true;
            }
        }
    }
}
