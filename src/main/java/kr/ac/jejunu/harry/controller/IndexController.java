package kr.ac.jejunu.harry.controller;

import kr.ac.jejunu.harry.model.User;
import kr.ac.jejunu.harry.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by jhkang on 2016-06-13.
 */
@Controller
public class IndexController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping(path = {"", "/", "/index"}, produces = "text/html")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        HttpSession session = request.getSession();
        if(session != null || session.getAttribute("uid") != null) {
            Integer uid = (Integer) session.getAttribute("uid");
            if(uid != null && uid >= 0) {
                User user = userRepository.findOne(uid);
                if(user != null || user.getUid() >= 0) {
                    model.addAttribute(user);
                    response.addCookie(new Cookie("uid", String.valueOf(user.getUid())));
                }
            }
        }
        return "index";
    }
}
