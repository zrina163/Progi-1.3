package hr.fer.progi.ecosentry.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {

    @GetMapping({
        "/login", "/akcije", "/prijava-otpada",
        "/redar", "/udruga"
    })
    public String frontend() {
        return "forward:/index.html";
    }
}