package com.demeter.cloud.core.storage.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author marklin
 */
@ConfigurationProperties(prefix = "demeter.cloud.storage")
@Getter
@Setter
public class StorageProperties {
	/**
	 * 定义启用标识
	 */
	private String active;
	/**
	 * 本地存储
	 */
	private Local local;
	/**
	 * 阿里云-oss
	 */
	private Aliyun aliyun;
	/**
	 * 腾讯云-oss
	 */
	private Tencent tencent;
	/**
	 * 七牛云-oss
	 */
	private Qiniu qiniu;
	/**
	 * 私有云-oss
	 */
	private Minio minio;


	/**
	 * 本地存储
	 */
	@Getter
	@Setter
	public static class Local {
		/**
		 * 访问地址-address
		 */
		private String address;
		/**
		 * 存储路径-storagePath
		 */
		private String storagePath;
	}

	/**
	 * 腾讯云-oss
	 */
	@Getter
	@Setter
	public static class Tencent {
		/**
		 * 腾讯云-oss-accessKeyId
		 */
		private String accessKeyId;
		/**
		 * 腾讯云-oss-accessKeySecret
		 */
		private String accessKeySecret;
		/**
		 * 腾讯云-oss-bucketName
		 */
		private String bucketName;
		/**
		 * 腾讯云-oss-endpoint
		 */
		private String endpoint;
	}

	/**
	 * 阿里云-oss
	 */
	@Getter
	@Setter
	public static class Aliyun {
		/**
		 * 阿里云-oss-accessKeyId
		 */
		private String accessKeyId;
		/**
		 * 阿里云-oss-accessKeySecret
		 */
		private String accessKeySecret;
		/**
		 * 阿里云-oss-bucketName
		 */
		private String bucketName;
		/**
		 * 阿里云-oss-endpoint
		 */
		private String endpoint;
	}

	/**
	 * 七牛云-oss
	 */
	@Getter
	@Setter
	public static class Qiniu {
		/**
		 * 七牛云-oss-accessKeyId
		 */
		private String accessKeyId;
		/**
		 * 七牛云-oss-accessKeySecret
		 */
		private String accessKeySecret;
		/**
		 * 七牛云-oss-bucketName
		 */
		private String bucketName;
		/**
		 * 七牛云-oss-endpoint
		 */
		private String endpoint;
	}

	/**
	 * 私有云-oss
	 */
	@Getter
	@Setter
	public static class Minio {
		/**
		 * 私有云-oss-accessKeyId
		 */
		private String accessKeyId;
		/**
		 * 私有云-oss-accessKeySecret
		 */
		private String accessKeySecret;
		/**
		 * 私有云-oss-bucketName
		 */
		private String bucketName;
		/**
		 * 私有云-oss-endpoint
		 */
		private String endpoint;
	}
}
