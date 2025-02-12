package com.deskover.service;

import org.springframework.web.multipart.MultipartFile;

import com.deskover.model.entity.dto.UploadFile;

public interface UploadFileService {
    UploadFile uploadFileToTempFolder(MultipartFile file);
    UploadFile uploadFileToFolder(MultipartFile file,String folderPath);
    void removeTempFolder();

}
