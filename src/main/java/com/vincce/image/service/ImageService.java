package com.vincce.image.service;

import com.vincce.image.domain.Image;
import com.vincce.image.mapper.ImageMapper;
import com.vincce.image.util.ImageUtil;
import com.vincce.image.vo.ImageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
  Created By BaoNing On 2019年1月22日
*/
@Service
public class ImageService {

    @Autowired
    private ImageMapper imageMapper;

    /**
     * 新增图片
     *
     * @param file
     * @return
     */
    @Transactional
    public int insertImage(MultipartFile file) {
        //判空
        if (file.isEmpty()) {
            return 0;
        }
        //图片上传七牛云后，并返回
        Image image = ImageUtil.uploadImage(file, file.getOriginalFilename());
        ImageVo imageVo = new ImageVo();
        imageVo.setName(image.getName());
        imageVo.setUrl(image.getUrl());
        imageMapper.insert(imageVo);
        return imageVo.getId();
    }


}
