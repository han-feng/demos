package org.demos.sec;

import java.util.Map;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/test")
public class WebConfigDetector {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ServletContext servletContext;

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @GetMapping("/filters")
    public String getConfig() {
        Map<String, ? extends FilterRegistration> filters = servletContext
                .getFilterRegistrations();
        return toJSON(filters);
    }

    private static String toJSON(Object object) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

}
