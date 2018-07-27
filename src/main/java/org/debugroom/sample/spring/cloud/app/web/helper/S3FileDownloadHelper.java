package org.debugroom.sample.spring.cloud.app.web.helper;

import com.amazonaws.auth.STSAssumeRoleSessionCredentialsProvider;
import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.actions.S3Actions;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.GetRoleRequest;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Date;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

@Component
public class S3FileDownloadHelper implements InitializingBean {

    private static final String S3_RESOURCE_ARN_PREFIX = "arn:aws:s3:::";
    private static final String DIRECTORY_DELIMITER = "/";
    public static final int STS_MIN_DURATION_MINUTES = 15;

    @Value("${bucket.name}")
    private String bucketName;
    @Value("${s3.download.role.name}")
    private String roleName;
    @Value("${s3.download.role.session.name}")
    private String roleSessionName;
    @Value("${s3.download.durationseconds}")
    private int dulationSecond;
    String roleArn;

    public URL getPresignedUrl(String objectKey){
        AmazonS3 amazonS3 = getS3ClientWithDownloadPolicy(objectKey);
        Date expiration = Date.from(ZonedDateTime.now().plusSeconds(dulationSecond).toInstant());
        return amazonS3.generatePresignedUrl(bucketName, objectKey, expiration);
    }

    private AmazonS3 getS3ClientWithDownloadPolicy(String objectKey){
        String resourceArn = new StringBuilder()
                                    .append(S3_RESOURCE_ARN_PREFIX)
                                    .append(bucketName)
                                    .append(DIRECTORY_DELIMITER)
                                    .append(objectKey)
                                    .toString();

        Statement statement = new Statement(Statement.Effect.Allow)
                .withActions(S3Actions.GetObject)
                .withResources(new Resource(resourceArn));
        String iamPolicy = new Policy().withStatements(statement).toJson();

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new STSAssumeRoleSessionCredentialsProvider
                                        .Builder(roleArn, roleSessionName)
                                        .withRoleSessionDurationSeconds(
                                                (int) TimeUnit.MINUTES.toSeconds(STS_MIN_DURATION_MINUTES))
                                        .withScopeDownPolicy(iamPolicy)
                                        .build())
                .build();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        GetRoleRequest getRoleRequest = new GetRoleRequest().withRoleName(roleName);
        roleArn = AmazonIdentityManagementClientBuilder.defaultClient()
                .getRole(getRoleRequest).getRole().getArn();
    }

}
