package kr.ac.jejunu.harry.controller;

import kr.ac.jejunu.harry.annotation.auth.AuthorizationRequired;
import kr.ac.jejunu.harry.exception.SessionAccountNotFoundException;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by jhkang on 2016-06-17.
 */
@Controller
@RequestMapping(path = "/files")
public class FileController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping(path = "/profile", method = RequestMethod.POST)
    @AuthorizationRequired
    public void upload(@RequestParam("file") MultipartFile file, HttpServletRequest request, Model model) throws IOException, NoSuchAlgorithmException {
        String mimeType= file.getContentType();
        String type = mimeType.split("/")[0];
        ResponseBuilder builder = new ResponseBuilder(request);
        if(!type.equals("image")) {
            builder.setStatusCode(ResponseCode.BAD_REQUEST, "File is must be image");
            builder.buildWithModel(model);
            return;
        }

        String fileName = file.getOriginalFilename();
        int pos = fileName.lastIndexOf( "." );
        String ext = fileName.substring( pos + 1 );

        HttpSession session = request.getSession();
        if(session == null || session.getAttribute("uid") == null) {
            throw new SessionAccountNotFoundException();
        }
        Integer uid = (Integer) session.getAttribute("uid");
        User user = userRepository.findOne(uid);
        if(user == null || user.getUid() < 0) {
            throw new SessionAccountNotFoundException();
        }

        fileName = sha1(user.getUid() + "_" + new Date().getTime() +"_" + fileName) + ext;
        File saveFile = new File("src/main/webapp/WEB-INF/views/resources/profile" + fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
        BufferedOutputStream outputStream = new BufferedOutputStream(fileOutputStream);
        outputStream.write(file.getBytes());
        outputStream.close();

        builder.addAttribute("image", "/resources/profile/" + fileName);
        builder.buildWithModel(model);
    }

    private String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
