package com.erling.controllerJ.user;

import com.google.code.kaptcha.Producer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:5174")
@RequestMapping("/user")
public class CaptchaController {
    private Producer kaptcha;
    private String code;

    @Autowired
    public void setKaptcha(Producer kaptcha) {
        this.kaptcha = kaptcha;
    }
    @GetMapping("/get-captcha")
    public void getCaptcha(HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        this.code = kaptcha.createText();
        BufferedImage image = kaptcha.createImage(code);
        HttpSession session = request.getSession();
        session.setAttribute("CAPTCHA", code);
        session.setMaxInactiveInterval(100); // 验证码有效期100秒

        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        ImageIO.write(image, "png", response.getOutputStream());
        System.out.println("验证码：" + code);
    }

    @PostMapping("/verify-captcha")
    public boolean verifyCaptcha(@RequestParam(value = "captcha") String captcha) {
        return captcha.equalsIgnoreCase(code);
    }
}
