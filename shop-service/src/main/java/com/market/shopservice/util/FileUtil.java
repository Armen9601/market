package com.market.shopservice.util;


import com.market.userservice.exception.FileTransferException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class FileUtil {

    @Value("${file.upload.dir}")
    private String FILES_PATH;

    public String fileUpload(MultipartFile file) {

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File filePath = new File(FILES_PATH + File.separator + fileName);
        try {
            file.transferTo(filePath);
        } catch (IOException e) {
            throw new FileTransferException("file transfer to " + FILES_PATH + "is fail");
        }
        return fileName;
    }
}
