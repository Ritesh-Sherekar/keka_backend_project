package com.example.KekaActionService.controller.Delete;

import com.example.KekaActionService.dto.documentDto.DocumentsSubmitResponseDto;
import com.example.KekaActionService.entity.Document;
import com.example.KekaActionService.service.Delete.DocumentDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ActionDeleteDocService")
public class DocumentDeleteController {
    @Autowired
    private DocumentDeleteService documentDeleteService;

    // Delete Document By Doc ID
    @DeleteMapping("/deleteDoc")
    public ResponseEntity<DocumentsSubmitResponseDto> deleteDocument(@RequestParam long docID){
        Document document = documentDeleteService.deleteDocument(docID);

        DocumentsSubmitResponseDto responseDto = new DocumentsSubmitResponseDto();
        responseDto.setEmployeeID(document.getEmployeeID().getEmployeeID());
        responseDto.setDocId(document.getDocId());
        responseDto.setMessage("Document Deleted Successfully");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
