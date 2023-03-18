package shop.mtcoding.hiberpc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import shop.mtcoding.hiberpc.model.UserRepository;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserRepository userRepository;
}
