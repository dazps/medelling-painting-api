package com.medellinpainting.medellingpaintingapi.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadFile(MultipartFile file, String folder) throws IOException {
        Map<?, ?> result = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "folder", "medellin-painting/" + folder,
                        "resource_type", "auto"
                )
        );
        return (String) result.get("secure_url");
    }

    public void deleteByUrl(String url) throws Exception {
        String publicId = url.replaceAll(".*/upload/(?:v\\d+/)?(.+)\\.\\w+$", "$1");
        String resourceType = url.contains("/video/upload/") ? "video" : "image";
        cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("resource_type", resourceType));
    }

    public Map<?, ?> getUsage() throws Exception {
        return cloudinary.api().usage(ObjectUtils.emptyMap());
    }
}
