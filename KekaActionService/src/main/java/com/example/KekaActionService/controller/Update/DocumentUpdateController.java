package com.example.KekaActionService.controller.Update;

import com.example.KekaActionService.dto.documentDto.DocumentsSubmitResponseDto;
import com.example.KekaActionService.entity.Document;
import com.example.KekaActionService.service.Update.DocumentUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/ActionUpdateDocService")
public class DocumentUpdateController {
    @Autowired
    private DocumentUpdateService documentUpdateService;

    // Update Document By
    @PutMapping("/updateDocument")
    public ResponseEntity<DocumentsSubmitResponseDto> updateDoc(@RequestParam long docID,
                                                                @RequestParam MultipartFile updatedFile) throws IOException {
        Document document = documentUpdateService.updateDocument(docID, updatedFile);

        DocumentsSubmitResponseDto responseDto = new DocumentsSubmitResponseDto();
        responseDto.setDocId(document.getDocId());
        responseDto.setEmployeeID(document.getEmployeeID().getEmployeeID());
        responseDto.setMessage("Document Updated Successfully");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
