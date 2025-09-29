package com.example.KekaActionService.service.Post;

import com.example.KekaActionService.dto.documentDto.DocumentsSubmitResponseDto;
import com.example.KekaActionService.entity.Document;
import com.example.KekaActionService.entity.Employee;
import com.example.KekaActionService.exception.ContentTypeNotValidException;
import com.example.KekaActionService.exception.EmployeeIdNotFoundException;
import com.example.KekaActionService.repository.DocumentRepo;
import com.example.KekaActionService.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class DocumentPostService {
    @Autowired
    private DocumentRepo documentRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    // Submit Documents
    public DocumentsSubmitResponseDto submitDocuments(int empID, String typeOfDoc, String subTypeOfDoc, MultipartFile doc) throws IOException {

        Optional<Employee> byEmployeeID = employeeRepo.findByEmployeeID(Math.toIntExact(empID));

        if (byEmployeeID.isEmpty()){
            throw new EmployeeIdNotFoundException("Id Not Found");
        }

        Set<String> contentType = Set.of("application/pdf","image/jpeg","image/png");

        if (!contentType.contains(doc.getContentType())){
            throw new ContentTypeNotValidException("Content Type Not Valid");
        }

        Document document = new Document();
        document.setEmployeeID(byEmployeeID.get());
        document.setTypeOfDoc(typeOfDoc);
        document.setSubTypeDoc(subTypeOfDoc);
        document.setFileName(doc.getOriginalFilename());
        document.setContentType(doc.getContentType());
        document.setUpdatedAt(LocalDateTime.now());
        document.setData(doc.getBytes());
        documentRepo.save(document);

        DocumentsSubmitResponseDto responseDto = new DocumentsSubmitResponseDto();
        responseDto.setEmployeeID(byEmployeeID.get().getEmployeeID());
        responseDto.setMessage("Document Submitted Successfully!");
        responseDto.setDocId(document.getDocId());
        return responseDto;
    }
}
