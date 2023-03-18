package shop.mtcoding.hiberpc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import shop.mtcoding.hiberpc.model.UserRepository;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserRepository userRepository;
    
    public void join(){}
    public void login(){}
    public void userDetail(){}
    public void userList(){}
    public void like(){} // 좋아하는 숫자로 쿠키 학습
}
