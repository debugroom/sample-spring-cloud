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
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Component
public class S3FileUploadHelper implements InitializingBean {

    private static final String S3_BUCKET_PREFIX = "s3://";
    private static final String DIRECTORY_DELIMITER = "/";

    @Value("${bucket.name}")
    private String bucketName;
    @Value("${s3.upload.role.name}")
    private String roleName;
    private String roleArn;

    @Inject
    ResourceLoader resourceLoader;

    public String saveFile(MultipartFile multipartFile){
        String objectKey = new StringBuilder()
                .append(S3_BUCKET_PREFIX)
                .append(bucketName)
                .append(DIRECTORY_DELIMITER)
                .append(multipartFile.getOriginalFilename())
                .toString();
        WritableResource writableResource = (WritableResource) resourceLoader.getResource(objectKey);
        try(InputStream inputStream = multipartFile.getInputStream();
            OutputStream outputStream = writableResource.getOutputStream()){
            IOUtils.copy(inputStream, outputStream);
        }catch (IOException e){
            e.printStackTrace();
        }
        return objectKey;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        GetRoleRequest getRoleRequest = new GetRoleRequest().withRoleName(roleName);
        roleArn = AmazonIdentityManagementClientBuilder.defaultClient()
                .getRole(getRoleRequest).getRole().getArn();
    }

}
