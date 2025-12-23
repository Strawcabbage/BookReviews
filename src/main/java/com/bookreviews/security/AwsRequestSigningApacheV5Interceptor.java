package com.bookreviews.security;

import org.apache.hc.core5.http.EntityDetails;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpRequestInterceptor;
import org.apache.hc.core5.http.protocol.HttpContext;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.signer.Aws4Signer;
import software.amazon.awssdk.auth.signer.params.Aws4SignerParams;
import software.amazon.awssdk.http.SdkHttpFullRequest;
import software.amazon.awssdk.regions.Region;

import java.io.IOException;

public class AwsRequestSigningApacheV5Interceptor
        implements HttpRequestInterceptor {

    private final String service;
    private final Aws4Signer signer;
    private final AwsCredentialsProvider credentialsProvider;
    private final Region region;

    public AwsRequestSigningApacheV5Interceptor(
            String service,
            Aws4Signer signer,
            AwsCredentialsProvider credentialsProvider,
            Region region) {
        this.service = service;
        this.signer = signer;
        this.credentialsProvider = credentialsProvider;
        this.region = region;
    }

    @Override
    public void process(
            HttpRequest request,
            EntityDetails entity,
            HttpContext context) throws HttpException, IOException {

        SdkHttpFullRequest sdkRequest =
                ApacheHttpRequestConverter.toSdkHttpFullRequest(request, entity);

        SdkHttpFullRequest signed =
                signer.sign(
                        sdkRequest,
                        Aws4SignerParams.builder()
                                .awsCredentials(credentialsProvider.resolveCredentials())
                                .signingRegion(region)
                                .signingName(service)
                                .build()
                );

        ApacheHttpRequestConverter.applySignedRequest(request, signed);
    }
}

