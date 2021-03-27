package com.lombre.urlShortner.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@SuppressWarnings("deprecation")
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    public String getErrorPath() {
        return PATH;
    }

    /**
     * error 발생 시 error 페이지로 리다이렉트
     * @param req
     * @return
     */
    @RequestMapping(value = PATH)
    public String redirectErrorPage(HttpServletRequest req, Model model) {
        Object status = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));

        model.addAttribute("code", status.toString());
        model.addAttribute("msg", httpStatus.getReasonPhrase());
        model.addAttribute("timestamp", new Date());

        return "errors/error";
    }

}
