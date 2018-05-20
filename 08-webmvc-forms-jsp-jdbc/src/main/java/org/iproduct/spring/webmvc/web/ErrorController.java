package org.iproduct.spring.webmvc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ErrorController {

    @RequestMapping(path = "/error")
    public ModelAndView handle(HttpServletRequest request) {
        ModelAndView result = new ModelAndView("error");
//        Enumeration<String> anames = request.getAttributeNames();
//        while(anames.hasMoreElements()){
//            String aname = anames.nextElement();
//            map.put(aname, request.getAttribute(aname).toString());
//        }
        result.addObject("status", request.getAttribute("javax.servlet.error.status_code"));
        result.addObject("error", request.getAttribute("javax.servlet.error.message"));
        return result;
    }}
