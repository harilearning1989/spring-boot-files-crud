package com.web.demo.controls;

import com.web.demo.response.Response;
import com.web.demo.services.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloRestController {

    @Autowired
    HelloService helloService;

    @GetMapping("/{message}")
    @ResponseBody
    public ResponseEntity<Response> reverse(@PathVariable("message") String message) {
        Assert.notNull(message, "The message should not be null!");
        Response response= helloService.reverse(message);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

}
