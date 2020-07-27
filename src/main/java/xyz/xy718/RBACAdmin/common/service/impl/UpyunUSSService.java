package cloud.catisland.ivory.common.service.impl;

import java.io.File;
import java.util.Optional;


import org.springframework.stereotype.Service;

import cloud.catisland.ivory.common.service.ImageService;
import cloud.catisland.ivory.common.util.UpyunUSSUtil;
import cloud.catisland.ivory.common.util.XyUtil;

@Service
public class UpyunUSSService implements ImageService {

    @Override
    public Optional<String> upImage(File imgFile) {
        return UpyunUSSUtil.upFile(imgFile, "/user"+XyUtil.getOSSFilePath());
    }

    @Override
    public Optional<Boolean> deleteImage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<String> copy() {
        // TODO Auto-generated method stub
        return null;
    }
    
}