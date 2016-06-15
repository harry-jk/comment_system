package kr.ac.jejunu.harry.controller;

import kr.ac.jejunu.harry.exception.BadRequestException;
import kr.ac.jejunu.harry.model.User;
import kr.ac.jejunu.harry.repository.UserRepository;
import kr.ac.jejunu.harry.response.ResponseBuilder;
import kr.ac.jejunu.harry.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by jhkang on 2016-06-15.
 */
@Controller
@RequestMapping(path = "/auth")
public class AuthorizationController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping(path = "/signin", method = RequestMethod.POST)
    public void signin(@RequestParam String id, @RequestParam String password,
                       HttpServletRequest request, Model model) {
        User user = userRepository.findByIdAndPassword(id, password);
        ResponseBuilder builder = new ResponseBuilder(request);
        if(user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("isSignin", true);
            session.setAttribute("uid", user.getUid());
            builder.addAttribute(user);
        } else {
            builder.setStatusCode(ResponseCode.CONFLICT, "Account Not Found");
        }
        builder.buildWithModel(model);
    }

    @RequestMapping(path = "/signout", method = RequestMethod.GET)
    public void signout(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Object isSignin = session.getAttribute("isSignin");
        if(isSignin != null && isSignin instanceof Boolean) {
            session.invalidate();
        }
        ResponseBuilder builder = new ResponseBuilder(request);
        builder.buildWithModel(model);
    }

    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public void signup(@RequestPayload User user, HttpServletRequest request, Model model) {
        String id = user.getId();
        String password = user.getPassword();
        String name = user.getName();
        if(id == null || id.getBytes().length > 20 || id.getBytes().length < 3) {
            throw new BadRequestException("ID length is must be more then 3, less then 20");
        } else if(password == null || password.getBytes().length > 20 || password.getBytes().length < 3) {
            throw new BadRequestException("Password length is must be more then 3, less then 20");
        } else if(name == null || name.getBytes().length > 20 || name.getBytes().length < 1) {
            throw new BadRequestException("Name length is must be more then 1, less then 20");
        }

        ResponseBuilder builder = new ResponseBuilder(request);
        User savedUser = userRepository.save(user);
        builder.addAttribute(savedUser);
        builder.buildWithModel(model);
    }
}
