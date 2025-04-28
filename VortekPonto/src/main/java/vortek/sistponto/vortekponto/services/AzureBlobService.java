package vortek.sistponto.vortekponto.services;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobStorageException;

@Service
public class AzureBlobService {

    @Value("${azure.storage.connection-string}")
    private String connectionString;

    public String salvarFoto(MultipartFile file, String containerName) throws IOException {
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

        BlobContainerClient containerClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient()
                .getBlobContainerClient(containerName);

        if (!containerClient.exists()) {
            containerClient.create();
        }

        BlobClient blobClient = containerClient.getBlobClient(fileName);
        blobClient.upload(file.getInputStream(), file.getSize(), true);

        return blobClient.getBlobUrl();
    }

    public byte[] baixarFoto(String fileName, String containerName) {
        try {
            BlobContainerClient containerClient = new BlobServiceClientBuilder()
                    .connectionString(connectionString)
                    .buildClient()
                    .getBlobContainerClient(containerName);
    
            BlobClient blobClient = containerClient.getBlobClient(fileName);
    
            if (!blobClient.exists()) {
                throw new RuntimeException("Arquivo não encontrado no Blob Storage");
            }
            
            BinaryData content = blobClient.downloadContent();
    
            return content.toBytes();
        } catch (BlobStorageException e) {
            throw new RuntimeException("Erro ao baixar arquivo: " + e.getMessage(), e);
        }
    }
}
