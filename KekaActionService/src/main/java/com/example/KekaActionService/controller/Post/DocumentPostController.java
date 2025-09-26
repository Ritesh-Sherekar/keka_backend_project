package com.example.KekaActionService.controller.Post;

import com.example.KekaActionService.dto.documentDto.DocumentsSubmitResponseDto;
import com.example.KekaActionService.service.Post.DocumentPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/ActionPostDocService")
public class DocumentPostController {
    @Autowired
    private DocumentPostService documentPostService;

    // Submit Documents
    @PostMapping(value = "/submitDocuments")
    public ResponseEntity<DocumentsSubmitResponseDto> submitDocument(@RequestParam int empID,
                                                                     @RequestParam MultipartFile doc,
                                                                     @RequestParam String typeOfDoc,
                                                                     @RequestParam String subTypeOfDoc) throws IOException {
        DocumentsSubmitResponseDto responseDto = documentPostService.submitDocuments(empID, typeOfDoc, subTypeOfDoc, doc);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
