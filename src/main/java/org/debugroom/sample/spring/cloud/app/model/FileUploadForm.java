package org.debugroom.sample.spring.cloud.app.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileUploadForm {

    private MultipartFile uploadFile;

}
