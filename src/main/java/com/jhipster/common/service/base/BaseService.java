package com.jhipster.common.service.base;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.jhipster.common.client.AuthorizedFeignClient;
import com.jhipster.common.client.OAuth2FeignClientConfiguration;
import com.jhipster.common.domain.base.FileMeta;

import feign.Response;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

/**
 * 模块接口示例
 * 
 * @author yonker
 * @date 2020/1/12 21:20
 */
@AuthorizedFeignClient(name = "base", configuration = {OAuth2FeignClientConfiguration.class,
        BaseService.MultipartSupportConfig.class})
public interface BaseService {

    /**
     * 上传文件
     *
     * @param file
     *            上传的文件
     * @param name
     *            指定文件名（可覆盖默认文件名）
     * @return FileMeta
     */
    @PostMapping(value = "api/inner/files/upload", produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    FileMeta uploadFile(@RequestPart(value = "file") MultipartFile file,
            @RequestParam(value = "name", required = false) String name);

    /**
     * 下载文件
     *
     * @param fileUrl
     *            文件路径
     * @return feign.Response
     */
    @GetMapping(value = "api/inner/files/download")
    Response downloadFile(@RequestParam(value = "fileUrl") String fileUrl);

    /**
     * 配置类MultipartSupportConfig（*文件上传接口所需）
     */
    class MultipartSupportConfig {

        @Autowired
        private ObjectFactory<HttpMessageConverters> messageConverters;

        @Bean
        public Encoder feignFormEncoder() {
            return new SpringFormEncoder(new SpringEncoder(messageConverters));
        }

    }

}
