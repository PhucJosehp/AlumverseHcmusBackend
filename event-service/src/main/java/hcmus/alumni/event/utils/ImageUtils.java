package hcmus.alumni.event.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.StorageException;

@Service
public class ImageUtils {
	@Autowired
	private GCPConnectionUtils gcp;
	private final String eventPath = "images/events/";
	private final String noneEvent = "none";
	public static int saltLength = 16;

	// Save image in a local directory
	public String saveImageToStorage(String uploadDirectory, MultipartFile imageFile)
			throws IOException {
		if (imageFile == null) {
			String imageUrl = gcp.getDomainName() + gcp.getBucketName() + "/" + uploadDirectory + noneEvent;
			return imageUrl;
		}

		String newFilename = uploadDirectory;

		// Convert MultipartFile to byte array
		byte[] imageBytes = imageFile.getBytes();

		BlobInfo blobInfo = BlobInfo.newBuilder(gcp.getBucketName(), newFilename)
				.setContentType(imageFile.getContentType()).build();

		// Upload the image from the local file path
		try {
			gcp.getStorage().create(blobInfo, imageBytes);
		} catch (StorageException e) {
			System.err.println("Error uploading image: " + e.getMessage());
		}

		String imageUrl = gcp.getDomainName() + gcp.getBucketName() + "/" + newFilename;
		return imageUrl;
	}

	public void deleteImageFromStorageByUrl(String oldImageUrl) {
		if (oldImageUrl == null) {
			return;
		}
		// Get image name
		String regex = gcp.getDomainName() + gcp.getBucketName() + "/";
		String extractedImageName = oldImageUrl.replaceAll(regex, "");

		try {
			Blob blob = gcp.getStorage().get(gcp.getBucketName(), extractedImageName);
			if (blob == null) {
				System.out.println("null");
				return;
			}
			gcp.getStorage().delete(gcp.getBucketName(), extractedImageName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return;
	}

	public String getEventPath(String id) {
		return eventPath + id + "/";
	}
}