package shop.mtcoding.hiberpc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.hiberpc.config.auth.LoginUser;
import shop.mtcoding.hiberpc.dto.UserRequest;
import shop.mtcoding.hiberpc.handler.ex.MyException;
import shop.mtcoding.hiberpc.model.User;
import shop.mtcoding.hiberpc.model.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserRepository userRepository;
    private final HttpSession session;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDto joinDto) {
        User userPS = userRepository.save(joinDto.toEntity());
        return new ResponseEntity<>(userPS, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginDto loginDto) {
        User userPS = userRepository.findByUsername(loginDto.getUsername(), loginDto.getPassword()).orElseThrow(
                () -> new MyException("해당 유저를 찾을 수 없습니다")
        );
        session.setAttribute("loginUser", userPS);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 인증 체크
    @GetMapping("/users")
    public ResponseEntity<?> userList() {
        List<User> userListPS = userRepository.findAll();
        return new ResponseEntity<>(userListPS, HttpStatus.OK);
    }

    // 인증과 권한 - 세션 찾기
    @GetMapping("/users/{id}/v1")
    public ResponseEntity<?> userDetailV1(@PathVariable Integer id) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (!loginUser.getId().equals(id)) {
            throw new MyException("고객 정보를 볼 수 있는 권한이 없습니다");
        }
        User userPS = userRepository.findById(id).orElseThrow(
                () -> new MyException("해당 유저를 찾을 수 없습니다")
        );
        return new ResponseEntity<>(userPS, HttpStatus.OK);
    }

    // 인증과 권한 - 리졸버
    @GetMapping("/users/{id}/v2")
    public ResponseEntity<?> userDetailV2(@PathVariable Integer id, @LoginUser User loginUser) {
        if (!loginUser.getId().equals(id)) {
            throw new MyException("고객 정보를 볼 수 있는 권한이 없습니다");
        }
        User userPS = userRepository.findById(id).orElseThrow(
                () -> new MyException("해당 유저를 찾을 수 없습니다")
        );
        return new ResponseEntity<>(userPS, HttpStatus.OK);
    }

    @PostMapping("/products/{id}/cart")
    public ResponseEntity<?> addCart(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) {
        String productNames = "";
        Cookie[] cookies = request.getCookies();

        // 장바구니가 존재할 때
        boolean checkCookie = false;
        for (Cookie c : cookies) {
            if (c.getName().equals("cart")) {
                productNames += c.getValue() + "/" + id.toString();
                checkCookie = true;
            }
        }

        // 장바구니가 존재하지 않을 때
        if (checkCookie == false) {
            productNames = id.toString();
        }

        Cookie cookie = new Cookie("cart", productNames);
        cookie.setPath("/");
        cookie.setMaxAge(1000 * 60 * 60); // 1시간
        cookie.setHttpOnly(false); // document.cookie
        response.addCookie(cookie);
        return new ResponseEntity<>("쿠키등록됨", HttpStatus.OK);
    }
}
