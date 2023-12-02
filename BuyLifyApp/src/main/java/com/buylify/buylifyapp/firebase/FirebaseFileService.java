package com.buylify.buylifyapp.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FirebaseFileService {

    private Storage storage;

    @EventListener
    public void init(ApplicationReadyEvent event) {
        try {
            //FileInputStream serviceAccount = new FileInputStream("projekt-zespolowy-5362c-firebase-adminsdk-1jnm7-4fecdbc10e.json");
            ClassPathResource serviceAccount = new ClassPathResource("projekt-zespolowy-5362c-firebase-adminsdk-1jnm7-4fecdbc10e.json");
            storage = StorageOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                    .build().getService();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String saveTest(MultipartFile file) throws IOException {
        String imageName = generateFileName(file.getOriginalFilename());
        Map<String, String> map = new HashMap<>();
        map.put("firebaseStorageDownloadTokens", imageName);
        BlobId blobId = BlobId.of("projekt-zespolowy-5362c.appspot.com", imageName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setMetadata(map).setContentType(file.getContentType()).build();
        storage.create(blobInfo, file.getInputStream());
        return imageName;
    }

    public byte[] getFile(String fileName) throws IOException {
        BlobId blobId = BlobId.of("projekt-zespolowy-5362c.appspot.com", fileName);
        return storage.get(blobId).getContent();
    }

    private String generateFileName(String originalFileName) {
        return UUID.randomUUID() + "." + getExtension(originalFileName);
    }

    private String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }
}
