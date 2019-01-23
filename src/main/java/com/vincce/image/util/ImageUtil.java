package com.vincce.image.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.vincce.image.constant.QiNiuConstant;
import com.vincce.image.domain.Image;
import com.vincce.image.domain.ImageFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * Created By BaoNing On 2019/1/23
 */
@Slf4j
public class ImageUtil {

    //凭证
    private static final Auth auth = Auth.create(QiNiuConstant.QINIU_ACCESSKEY,QiNiuConstant.QINIU_SECRETKEY);

    //指定机房,Zone.zone0代表华东地区
    private static final Configuration cfg = new Configuration(Zone.zone0());

    /**
     * 图片上传
     * @param file
     * @param imageName
     * @return
     */
    public static Image uploadImage(MultipartFile file, String imageName) {
        try {
            UploadManager uploadManager = new UploadManager(cfg);
            byte[] uploadBytes = file.getBytes();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(uploadBytes);

            String uploadToken = auth.uploadToken(QiNiuConstant.QINIU_BUCKET);

            //默认不指定key的情况下，以文件内容的hash值作为文件名
            String key = null;

            try {
                Response response = uploadManager.put(byteArrayInputStream, imageName, uploadToken, null, null);

                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

                Image image = new Image();
                image.setName(putRet.key);
                image.setUrl(imageUrl(imageName));
                return image;

            }catch (QiniuException ex){
                Response r = ex.response;
                log.info("图片上传失败!", r.toString());
                return null;
            }
        } catch (IOException ex){
            log.info("图片读取失败!");
            return null;
        }
    }

    /**
     * 获取图片地址
     * @param imageName
     * @return
     */
    public static String imageUrl(String imageName) throws UnsupportedEncodingException {
        String encodeImageName = URLEncoder.encode(imageName, "utf-8");
        String url = String.format("%s/%s",QiNiuConstant.QINIU_DOMAINNAME,encodeImageName);
        return url;
    }


}
