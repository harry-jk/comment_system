package kr.ac.jejunu.harry.response;

import org.springframework.core.Conventions;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by jhkang on 2016-06-15.
 */
public class ResponseBuilder {
    private ModelMap model;

    private int status;
    private String request;
    private Map<String, Object> result;

    public ResponseBuilder(HttpServletRequest request) {
        this.model = new ModelMap();
        this.status = ResponseCode.OK;
        this.request = request.getServletPath();
        result = new LinkedHashMap<String, Object>();
    }

    public ResponseBuilder setStatusCode(int code, String reason) {
        this.status = code;
        result.put("reason", reason);
        return this;
    }

    public ResponseBuilder addAttribute(Object value) {
        return addAttribute(Conventions.getVariableName(value), value);
    }

    public ResponseBuilder addAttribute(String key, Object value) {
        if(value instanceof Collection && ((Collection<?>) value).isEmpty()) {
            return this;
        }
        result.put(key, value);
        return this;
    }

    public ModelMap buildToModelMap() {
        model.addAttribute("status", status);
        model.addAttribute("request", request);
        for(String key : result.keySet()) {
            model.addAttribute(key, result.get(key));
        }
        return model;
    }

    public Model buildWithModel(Model model) {
        ModelMap map = buildToModelMap();
        model.asMap().clear();
        model.addAllAttributes(map);
        return model;
    }

    public ModelAndView buildToModelAndView() {
        ModelMap map = buildToModelMap();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addAllObjects(map);
        return modelAndView;
    }
}
