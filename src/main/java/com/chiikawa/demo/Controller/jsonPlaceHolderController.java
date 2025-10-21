package com.chiikawa.demo.Controller;

import com.chiikawa.demo.DTO.base.Response;
import com.chiikawa.demo.service.JsonPlaceHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mock")
public class jsonPlaceHolderController {
    @Autowired
    private JsonPlaceHolderService jsonPlaceHolderService;

    @GetMapping("/sync")
    public ResponseEntity<Object> testSyncApi() {
        Object res = jsonPlaceHolderService.testSyncApi();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.success("200","success","success",res));
    }
}