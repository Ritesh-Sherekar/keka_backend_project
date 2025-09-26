package com.example.KekaActionService.controller.Update;

import com.example.KekaActionService.entity.Department;
import com.example.KekaActionService.service.Update.DepartmentUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ActionUpdateDeptService")
public class DepartmentUpdateController {
    @Autowired
    private DepartmentUpdateService departmentUpdateService;

    // Update Manager ID By Dept ID
    @PutMapping("/updateManagerIdByDeptId")
    public ResponseEntity<Department> updateManagerIdByDeptId(@RequestParam int deptId,
                                                              @RequestParam int managerId){
        Department department = departmentUpdateService.updateManagerByDeptId(deptId, managerId);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }
}
