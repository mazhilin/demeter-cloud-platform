package com.demeter.cloud.core.storage.configuration;

import com.demeter.cloud.core.constant.StorageType;
import com.demeter.cloud.core.storage.*;
import com.demeter.cloud.model.exception.BusinessException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author marklin
 */
@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class StorageAutoConfiguration {

	private final StorageProperties properties;

	public StorageAutoConfiguration(StorageProperties properties) {
		this.properties = properties;
	}

	@Bean
	public StorageServerService storageService() {
		StorageServerService storageServerService = new StorageServerService();
		String active = this.properties.getActive();
		storageServerService.setActive(active);
		StorageType storageType = StorageType.getInstance(active);
		switch (storageType) {
			case LOCAL:
				storageServerService.setStorage(localStorage());
				break;
			case MINIO:
				storageServerService.setStorage(minioStorage());
				break;
			case QINIU:
				storageServerService.setStorage(qiniuStorage());
				break;
			case ALIYUN:
				storageServerService.setStorage(aliyunStorage());
				break;
			case TENCENT:
				storageServerService.setStorage(tencentStorage());
				break;
			default:
				throw new BusinessException("当前存储模式 " + active + " 不支持");
		}
		return storageServerService;
	}

	/**
	 * 本地云存储
	 *
	 * @return 返回结果
	 */
	@Bean
	public LocalStorage localStorage() {
		LocalStorage localStorage = new LocalStorage();
		StorageProperties.Local local = this.properties.getLocal();
		localStorage.setAddress(local.getAddress());
		localStorage.setStoragePath(local.getStoragePath());
		return localStorage;
	}

	/**
	 * 阿里云存储
	 *
	 * @return 返回结果
	 */
	@Bean
	public AliyunStorage aliyunStorage() {
		AliyunStorage aliyunStorage = new AliyunStorage();
		StorageProperties.Aliyun aliyun = this.properties.getAliyun();
		aliyunStorage.setAccessKeyId(aliyun.getAccessKeyId());
		aliyunStorage.setAccessKeySecret(aliyun.getAccessKeySecret());
		aliyunStorage.setBucketName(aliyun.getBucketName());
		aliyunStorage.setEndpoint(aliyun.getEndpoint());
		return aliyunStorage;
	}

	/**
	 * 腾讯云存储
	 *
	 * @return 返回结果
	 */
	@Bean
	public TencentStorage tencentStorage() {
		TencentStorage tencentStorage = new TencentStorage();
		StorageProperties.Tencent tencent = this.properties.getTencent();
		tencentStorage.setSecretId(tencent.getAccessKeyId());
		tencentStorage.setSecretKey(tencent.getAccessKeySecret());
		tencentStorage.setBucketName(tencent.getBucketName());
		tencentStorage.setRegion(tencent.getEndpoint());
		return tencentStorage;
	}

	/**
	 * 七牛云存储
	 *
	 * @return 返回结果
	 */
	@Bean
	public QiniuStorage qiniuStorage() {
		QiniuStorage qiniuStorage = new QiniuStorage();
		StorageProperties.Qiniu qiniu = this.properties.getQiniu();
		qiniuStorage.setAccessKey(qiniu.getAccessKeyId());
		qiniuStorage.setSecretKey(qiniu.getAccessKeySecret());
		qiniuStorage.setBucketName(qiniu.getBucketName());
		qiniuStorage.setEndpoint(qiniu.getEndpoint());
		return qiniuStorage;
	}

	/**
	 * 私有化云存储
	 *
	 * @return 返回结果
	 */
	@Bean
	public MinioStorage minioStorage() {
		MinioStorage minioStorage = new MinioStorage();
		StorageProperties.Minio minio = this.properties.getMinio();
		minioStorage.setAccessKeyId(minio.getAccessKeyId());
		minioStorage.setAccessKeySecret(minio.getAccessKeySecret());
		minioStorage.setBucketName(minio.getBucketName());
		minioStorage.setEndpoint(minio.getEndpoint());
		return minioStorage;
	}
}
