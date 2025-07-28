package top.rainrem.boot.shared.file.service;

import org.springframework.web.multipart.MultipartFile;
import top.rainrem.boot.shared.file.model.FileInfo;

/**
 * 对象存储服务接口层
 * @author LightRain
 * @since 2025年7月26日21:06:59
 */
public interface FileService {

    /**
     * 上传文件
     * @param file 表单文件对象
     * @return 文件信息
     */
    FileInfo uploadFile(MultipartFile file);

    /**
     * 删除文件
     *
     * @param filePath 文件完整URL
     * @return 删除结果
     */
    boolean deleteFile(String filePath);
}
