package com.example.opentalk.controller;

import com.example.opentalk.dto.EmployeeDTO;
import com.example.opentalk.entity.Employee;
import com.example.opentalk.model.EmployeeInterface;
import com.example.opentalk.validate.LoginValidate;
import com.google.gson.Gson;
import com.example.opentalk.dto.CreateEmployeeRequest;
import com.example.opentalk.model.Status;
import com.example.opentalk.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.owasp.encoder.Encode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/admin/manage/employee")
@AllArgsConstructor
public class ManageEmployeeController {

    private final EmployeeService manageEmployeeService;

    @GetMapping("/search")
    public List<EmployeeDTO> getUserByParam(@PathParam("page") int page,
                                            @PathParam("limit") int limit,
                                            @PathParam("enable") boolean enable,
                                            @PathParam("company") String company,
                                            @PathParam("name") String name) {
//        company = Encode.forHtml(company);
//        name = Encode.forHtml(name);
        return manageEmployeeService.filterEmployee(enable, company, name, page, limit);
    }

    @GetMapping("/interface")
    public List<EmployeeInterface> getUserAndMapToInterface() {
        return manageEmployeeService.find();
    }

    @GetMapping("/soft")
    public List<EmployeeDTO> getUserAndSoft() {
        return manageEmployeeService.findAndSoft();
    }

}
