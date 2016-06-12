package kr.ac.jejunu.harry.controller;

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
@RequestMapping(path = "/comments")
public class CommentController {
    private final static Logger logger = LoggerFactory.getLogger(CommentController.class);

    @RequestMapping(path = "/{id:[0-9]+}", method = RequestMethod.GET)
    public void get(@PathVariable Integer id, Model model) {
        logger.info("get : " + id);
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

    @RequestMapping(path = "/{id:[0-9]+}/like", method = RequestMethod.GET)
    public void like(@PathVariable Integer id, Model model) {
        logger.info("like: " + id);
        model.addAttribute("like: " + id);
    }

    @RequestMapping(path = "/{id:[0-9]+}/dislike", method = RequestMethod.GET)
    public void dislike(@PathVariable Integer id, Model model) {
        logger.info("dislike: " + id);
        model.addAttribute("dislike: " + id);
    }
}
