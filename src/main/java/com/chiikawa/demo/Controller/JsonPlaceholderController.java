package com.chiikawa.demo.Controller;

import com.chiikawa.demo.DTO.base.Response;
import com.chiikawa.demo.DTO.external.JsonPlaceholderCommentDto;
import com.chiikawa.demo.DTO.external.JsonPlaceholderPostDto;
import com.chiikawa.demo.service.JsonPlaceholderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/json-placeholder")
public class JsonPlaceholderController {
    @Autowired
    private JsonPlaceholderService jsonPlaceholderService;

    @PostMapping("/posts")
    public ResponseEntity<Response> createPost(@RequestBody JsonPlaceholderPostDto payload) {
        JsonPlaceholderPostDto post = jsonPlaceholderService.createPost(payload);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.success("200","success","success",post));
    }

    @GetMapping("/posts")
    public ResponseEntity<Response> getPosts() {
        List<JsonPlaceholderPostDto> posts = jsonPlaceholderService.getPosts();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.success("200","success","success",posts));
    }

    @GetMapping("/comments")
    public ResponseEntity<Response> getComments() {
        List<JsonPlaceholderCommentDto> comments = jsonPlaceholderService.getComments();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.success("200","success","success",comments));
    }
}