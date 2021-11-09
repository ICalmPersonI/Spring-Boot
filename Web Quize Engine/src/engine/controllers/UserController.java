package engine.controllers;

import engine.authentication.services.UserService;
import engine.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/register")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping
    public void register(HttpServletResponse response, @Valid @RequestBody User user) {
        if (service.saveUser(user)) {
            response.setStatus(200);
        } else {
            response.setStatus(400);
        }
    }
}
