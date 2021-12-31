package com.web.demo.services;

import com.web.demo.response.Response;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class HelloServiceImpl implements HelloService{

    public Response reverse(String message){
        return Pattern.compile("\\s").splitAsStream(message).reduce((w1, w2) -> w2 + " " + w1).
                map(data->new Response(data)).get();
    }
}
