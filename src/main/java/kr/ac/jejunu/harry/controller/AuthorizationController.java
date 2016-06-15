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
import org.springframework.web.bind.annotation.*;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

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

    @RequestMapping(path = "/signin", method = RequestMethod.POST)
    public void signin(@RequestParam String id, @RequestParam String password,
                       HttpServletRequest request, HttpSession session,
                       Model model) {
        User user = userRepository.findByIdAndPassword(id, password);
        ResponseBuilder builder = new ResponseBuilder(request);
        if(user != null) {
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
            session.invalidate();
        }
        ResponseBuilder builder = new ResponseBuilder(request);
        builder.buildWithModel(model);
    }

    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public void signup(@RequestPayload User user,
                       HttpServletRequest request,
                       Model model) {
        ResponseBuilder builder = new ResponseBuilder(request);
        String id = user.getId();
        String password = user.getPassword();
        String name = user.getName();
        if(id == null || id.getBytes().length > 20 || id.getBytes().length < 3) {
            builder.setStatusCode(ResponseCode.BAD_REQUEST, "ID length is must be more then 3, less then 20");
        } else if(password == null || password.getBytes().length > 20 || password.getBytes().length < 3) {
            builder.setStatusCode(ResponseCode.BAD_REQUEST, "Password length is must be more then 3, less then 20");
        } else if(name == null || name.getBytes().length > 20 || name.getBytes().length < 1) {
            builder.setStatusCode(ResponseCode.BAD_REQUEST, "Name length is must be more then 1, less then 20");
        } else {
            User savedUser = userRepository.save(user);
            builder.addAttribute(savedUser);
        }
        builder.buildWithModel(model);
    }
}
