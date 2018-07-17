package org.debugroom.sample.spring.cloud.config;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("production")
@Configuration
public class ProductionConfig {

    @Bean
    public AmazonS3 amazonS3Cilent(){
        return AmazonS3ClientBuilder.standard().withCredentials(
                InstanceProfileCredentialsProvider.getInstance()).build();
    }

}
