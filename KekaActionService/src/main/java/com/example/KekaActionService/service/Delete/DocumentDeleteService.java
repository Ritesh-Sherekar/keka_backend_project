package com.example.KekaActionService.service.Delete;

import com.example.KekaActionService.entity.Document;
import com.example.KekaActionService.repository.DocumentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DocumentDeleteService {
    @Autowired
    private DocumentRepo documentRepo;

    // Delete Document By docID
    public Document deleteDocument(long docID){
        Optional<Document> byDocId = documentRepo.findByDocId(docID);

        byDocId.ifPresent(document -> documentRepo.delete(document));

        return byDocId.get();
    }
}
