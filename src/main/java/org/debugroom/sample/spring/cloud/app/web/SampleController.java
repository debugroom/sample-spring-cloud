package org.debugroom.sample.spring.cloud.app.web;

import org.debugroom.sample.spring.cloud.app.web.helper.S3FileUploadHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.debugroom.sample.spring.cloud.app.model.FileUploadModel;

import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;

@Slf4j
@Controller
public class SampleController {

    @Inject
    S3FileUploadHelper s3FileUploadHelper;

    @RequestMapping(method = RequestMethod.POST, value="/s3/upload")
    public String upload(FileUploadModel fileUploadModel){
        s3FileUploadHelper.saveFile(fileUploadModel.getUploadFile());
        return "redirect:/uploadResult.html";
    }

}
