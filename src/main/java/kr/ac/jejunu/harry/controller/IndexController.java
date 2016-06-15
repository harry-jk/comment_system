package kr.ac.jejunu.harry.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jhkang on 2016-06-13.
 */
@Controller
public class IndexController {
    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(path = {"", "/", "/index"}, produces = "text/html")
    public String index() {
        logger.info("index");
        return "index";
    }
}
