package kr.ac.jejunu.harry.controller;

import kr.ac.jejunu.harry.model.User;
import kr.ac.jejunu.harry.repository.UserRepository;
import kr.ac.jejunu.harry.response.ResponseBuilder;
import kr.ac.jejunu.harry.response.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by jhkang on 2016-06-15.
 */
@Controller
@RequestMapping(path = "/auth")
public class AuthorizationController {
    private final static Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

    @Autowired
    UserRepository userRepository;

    @RequestMapping(path = "/signin", method = RequestMethod.GET)
    public void signin(@RequestParam String id, @RequestParam String password,
                       HttpServletRequest request, HttpSession session,
                       Model model) {
        User user = userRepository.findByIdAndPassword(id, password);
        ResponseBuilder builder = new ResponseBuilder(request);
        if(user != null) {
            logger.info("Start Session");
            session.setAttribute("isSignin", true);
            session.setAttribute("uid", user.getUid());
            builder.addAttribute(user);
        } else {
            builder.setStatusCode(ResponseCode.CONFLICT, "Account Not Found");
        }
        builder.buildWithModel(model);
    }

    @RequestMapping(path = "/signout", method = RequestMethod.GET)
    public void signout(HttpServletRequest request, HttpSession session,
                      Model model) {
        Object isSignin = session.getAttribute("isSignin");
        if(isSignin != null && isSignin instanceof Boolean) {
            logger.info("End Session");
            session.removeAttribute("isSignin");
            session.removeAttribute("uid");
        }
        ResponseBuilder builder = new ResponseBuilder(request);
    }

    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public void signup(@RequestParam String id,
                       @RequestParam String password,
                       HttpServletRequest request,
                       Model model) {
    }
}
