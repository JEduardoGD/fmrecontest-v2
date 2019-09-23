package mx.fmre.rttycontest.recibir.services;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

@Service
public class AWSS3Service {
	private AmazonS3 s3client;

	@Value("${amazon.creds.acceskey}")
	private String amazonAccesskey;

	@Value("${amazon.creds.secretkey}")
	private String amazonSecretkey;

	@Value("${amazon.s3.region}")
	private String s3Region;

	@PostConstruct
	public void postConstruct() {
		BasicAWSCredentials creds = new BasicAWSCredentials(amazonAccesskey, amazonSecretkey);
		AWSStaticCredentialsProvider x = new AWSStaticCredentialsProvider(creds);
		this.s3client = AmazonS3ClientBuilder
				.standard()
				.withChunkedEncodingDisabled(true)
				.withCredentials(x)
				.withRegion(s3Region)
				.build();
	}

	// does bucket exist?
	public boolean doesBucketExist(String bucketName) {
		return this.s3client.doesBucketExistV2(bucketName);
	}

	// create a bucket
	public Bucket createBucket(String bucketName) {
		return s3client.createBucket(bucketName);
	}

	// list all buckets
	public List<Bucket> listBuckets() {
		return s3client.listBuckets();
	}

	// delete a bucket
	public void deleteBucket(String bucketName) {
		s3client.deleteBucket(bucketName);
	}

	// uploading object
	public PutObjectResult putObject(String bucketName, String key, File file) {
		return s3client.putObject(bucketName, key, file);
	}

	// uploading object
	public PutObjectResult putObject(String bucketName, String key, InputStream input, ObjectMetadata metadata) {
		return s3client.putObject(bucketName, key, input, metadata);
	}

	// listing objects
	public ObjectListing listObjects(String bucketName) {
		return s3client.listObjects(bucketName);
	}

	// get an object
	public S3Object getObject(String bucketName, String objectKey) {
		return s3client.getObject(bucketName, objectKey);
	}

	// copying an object
	public CopyObjectResult copyObject(String sourceBucketName, String sourceKey, String destinationBucketName,
			String destinationKey) {
		return s3client.copyObject(sourceBucketName, sourceKey, destinationBucketName, destinationKey);
	}

	// deleting an object
	public void deleteObject(String bucketName, String objectKey) {
		s3client.deleteObject(bucketName, objectKey);
	}

	// deleting multiple Objects
	public DeleteObjectsResult deleteObjects(DeleteObjectsRequest delObjReq) {
		return s3client.deleteObjects(delObjReq);
	}
}