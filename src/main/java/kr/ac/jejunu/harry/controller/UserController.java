package kr.ac.jejunu.harry.controller;

import kr.ac.jejunu.harry.annotation.auth.AuthorizationRequired;
import kr.ac.jejunu.harry.exception.BadRequestException;
import kr.ac.jejunu.harry.exception.SessionAccountNotFoundException;
import kr.ac.jejunu.harry.model.User;
import kr.ac.jejunu.harry.repository.UserRepository;
import kr.ac.jejunu.harry.response.ResponseBuilder;
import kr.ac.jejunu.harry.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jhkang on 2016-06-12.
 */
@Controller
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping(path = "/{id:[0-9]+}", method = RequestMethod.GET)
    public void get(@PathVariable Integer id, HttpServletRequest request, Model model) {
        ResponseBuilder builder = new ResponseBuilder(request);
        User user = userRepository.findOne(id);
        if(user == null) {
            builder.setStatusCode(ResponseCode.CONFLICT, "User Not Found");
        } else {
            builder.addAttribute(user);
        }
        builder.buildWithModel(model);
    }

    @RequestMapping(method = RequestMethod.GET)
    public void list(@RequestParam(required = false, defaultValue = "1") Integer page,
                     @RequestParam(required = false, defaultValue = "15") Integer size,
                     HttpServletRequest request, Model model) {
        ResponseBuilder builder = new ResponseBuilder(request);
        Page<User> users = userRepository.findAll(new PageRequest(page - 1, size));
        builder.addAttribute("totalPage", users.getTotalPages());
        builder.addAttribute("size", users.getSize());
        builder.addAttribute("page", users.getNumber() + 1);
        builder.addAttribute("first", users.isFirst());
        builder.addAttribute("last", users.isLast());
        builder.addAttribute("users", users.getContent());
        builder.buildWithModel(model);
    }

    @RequestMapping(method = RequestMethod.POST)
    @AuthorizationRequired
    public void edit(@RequestPayload User user, HttpServletRequest request, Model model)
            throws MissingServletRequestParameterException {
        if(user == null) {
            throw new MissingServletRequestParameterException("user", User.class.getTypeName());
        }
        Integer uid = (Integer) request.getSession().getAttribute("uid");
        User sessionUser = userRepository.findOne(uid);
        if(sessionUser == null || !user.getUid().equals(sessionUser.getUid())) {
            throw new SessionAccountNotFoundException();
        }
        if(!sessionUser.getId().equals(user.getId())) {
            throw new BadRequestException("Can not change ID");
        }
        String password = user.getPassword();
        String name = user.getName();
        if(password == null || password.getBytes().length > 20 || password.getBytes().length < 3) {
            throw new BadRequestException("Password length is must be more then 3, less then 20");
        } else if(name == null || name.getBytes().length > 20 || name.getBytes().length < 1) {
            throw new BadRequestException("Name length is must be more then 1, less then 20");
        }

        User editedUser = userRepository.save(user);
        ResponseBuilder builder = new ResponseBuilder(request);
        builder.addAttribute(editedUser);
        builder.buildWithModel(model);
    }
}
