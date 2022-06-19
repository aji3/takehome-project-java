package com.kazuya.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ThymeleafController {

  @GetMapping("/*.html")
  public String getHtml(HttpServletRequest request) {
    return request.getServletPath().substring(1);
  }
}
