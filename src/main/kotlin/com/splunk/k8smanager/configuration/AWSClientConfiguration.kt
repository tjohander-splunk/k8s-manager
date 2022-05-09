package com.splunk.k8smanager.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.services.s3.S3Client

@Configuration
class AWSClientConfiguration(
    @Value("\${AWS_ACCESS_KEY_ID}") private val awsAccessKey: String,
    @Value("\${AWS_SECRET_ACCESS_KEY}") private val awsAccessKeyId: String
) {

    private val credentials: AwsBasicCredentials =
        AwsBasicCredentials.create(awsAccessKey, awsAccessKey)

    @Bean
    fun s3Client(): S3Client =
        S3Client
            .builder()
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build()


}