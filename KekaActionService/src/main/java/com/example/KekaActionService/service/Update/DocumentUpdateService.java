package com.example.KekaActionService.service.Update;

import com.example.KekaActionService.entity.Document;
import com.example.KekaActionService.exception.DocumentIDNotFoundException;
import com.example.KekaActionService.repository.DocumentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DocumentUpdateService {
    @Autowired
    private DocumentRepo documentRepo;

    // Update Document By docID
    public Document updateDocument(long docId, MultipartFile updatedFile) throws IOException {
        Optional<Document> byDocID = documentRepo.findByDocId(docId);

        if (byDocID.isPresent()){
           Document document = byDocID.get();
           document.setUpdatedAt(LocalDateTime.now());
           document.setData(updatedFile.getBytes());
           document.setFileName(updatedFile.getOriginalFilename());
           document.setContentType(updatedFile.getContentType());
           return documentRepo.save(document);
        }else {
            throw new DocumentIDNotFoundException("Document ID Not Found!");
        }
    }
}
