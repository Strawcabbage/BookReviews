package com.bookreviews.security;

import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.auth.signer.Aws4Signer;
import software.amazon.awssdk.services.opensearch.OpenSearchClient;

import java.net.URISyntaxException;

@Configuration
public class OpenSearchConfig {

    @Value("${opensearch.host}")
    private String host;

    @Value("${opensearch.region}")
    private String region;

    @Bean
    public RestHighLevelClient openSearchClient() {

        Aws4Signer signer = Aws4Signer.create();
        DefaultCredentialsProvider credentialsProvider =
                DefaultCredentialsProvider.create();

        RestClient restClient = RestClient.builder(HttpHost.create(host))
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.addRequestInterceptorLast(
                                new AwsRequestSigningApacheV5Interceptor(
                                        "es",
                                        signer,
                                        credentialsProvider,
                                        Region.of(region)
                                )
                        )
                )
                .build();

        return new RestHighLevelClient(restClient);
    }
}
