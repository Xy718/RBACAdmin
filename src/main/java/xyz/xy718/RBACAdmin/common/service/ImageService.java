package cloud.catisland.ivory.common.service;

import java.io.File;
import java.util.Optional;

import org.springframework.stereotype.Service;

/**
 * 图片服务，用来上传图片到指定的OSS
 * 
 * @Author: Xy718
 * @Date: 2020-07-06 14:32:01
 * @LastEditors: Xy718
 * @LastEditTime: 2020-07-19 01:59:17
 */
// @Service
public interface ImageService {
    
    public Optional<String> upImage(File imgFile);
    public Optional<Boolean> deleteImage();
    public Optional<String> copy();
}