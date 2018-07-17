package org.debugroom.sample.spring.cloud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Profile("dev")
@PropertySource(value = {
        "file:/Users/kawabatakouhei/Documents/work/apps/info/aws_sample_app_access_key.properties"
})
@Configuration
public class DevConfig {

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public BasicAWSCredentials basicAWSCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    @Bean
    public AmazonS3 amazonS3Client() {
        return AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region)).build();
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDBClient(){
        return AmazonDynamoDBClientBuilder.standard().withRegion(Regions.fromName(region)).build();
    }

    @Bean
    public DynamoDB dynamoDB(){
        return new DynamoDB(amazonDynamoDBClient());
    }

}
