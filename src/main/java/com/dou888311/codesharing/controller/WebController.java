package com.dou888311.codesharing.controller;

import com.dou888311.codesharing.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {

    @Autowired
    WebService webService;

    @GetMapping("/code/{id}")
    public String getCodeHtml(@PathVariable String id, Model model) {
        return webService.getCode(id, model);
    }

    @GetMapping("/code/latest")
    public String getLatestHtml(Model model){
        return webService.getLatest(model);
    }

    @GetMapping("/code/new")
    public ModelAndView getNew() {
        return webService.getNew();
    }
}
