package kr.ac.jejunu.harry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jhkang on 2016-06-15.
 */
@Controller
@RequestMapping(path = "/auth")
public class AuthController {
    @RequestMapping(path = "/login")
    public void login() {

    }
}
