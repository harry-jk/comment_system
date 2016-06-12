package kr.ac.jejunu.harry.controller;

import kr.ac.jejunu.harry.annotation.auth.AuthRequired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by jhkang on 2016-06-12.
 */
@Controller
@RequestMapping(path = "/users")
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(path = "/{id:[0-9]+}", method = RequestMethod.GET)
    public void get(@PathVariable Integer id, Model model) {
        logger.info("get: " + id);
        model.addAttribute("get: " + id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public void list(Model model) {
        logger.info("list");
        model.addAttribute("list");
    }

    @RequestMapping(method = RequestMethod.POST)
    public void save(Model model) {
        logger.info("save");
        model.addAttribute("save");
    }

    @RequestMapping(path = "/{id:[0-9]+}", method = RequestMethod.POST)
    @AuthRequired
    public void edit(@PathVariable Integer id, Model model) {
        logger.info("edit: " + id);
        model.addAttribute("edit: " + id);
    }
}
