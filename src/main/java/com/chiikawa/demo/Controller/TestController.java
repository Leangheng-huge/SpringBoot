package com.chiikawa.demo.Controller;

import com.chiikawa.demo.DTO.base.Response;
import com.chiikawa.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mock")
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping
    public ResponseEntity<Object> test(){
        Object result = testService.testSynApi();
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200","success","test success",result));
    }
}
