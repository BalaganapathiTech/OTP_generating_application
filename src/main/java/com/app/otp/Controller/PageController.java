package com.app.otp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/otp")
    public String otpPage() {
        return "otp";
    }
}