package org.iproduct.spring.webmvc.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ErrorController {

    @RequestMapping(path = "/error")
    public Map<String, String> handle(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> anames = request.getAttributeNames();
        while(anames.hasMoreElements()){
            String aname = anames.nextElement();
            map.put(aname, request.getAttribute(aname).toString());
        }
//        map.put("status",
//                request.getAttribute("javax.servlet.error.status_code"));
//        map.put("reason",
//                request.getAttribute("javax.servlet.error.message"));
        return map;
    }}
