package shop.mtcoding.hiberpc.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.hiberpc.dto.UserIn;
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
    public ResponseEntity<?> join(@RequestBody UserIn.JoinInDto joinInDto){
        User userPS = userRepository.save(joinInDto.toEntity());
        return new ResponseEntity<>(userPS, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserIn.LoginInDto loginInDto){
        User userPS = userRepository.findByUsername(loginInDto.getUsername(), loginInDto.getPassword()).orElseThrow(
                ()-> new MyException("해당 유저를 찾을 수 없습니다")
        );
        session.setAttribute("loginUser", userPS);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<?> userDetail(@PathVariable Integer id){
        User userPS = userRepository.findById(id).orElseThrow(
                ()-> new MyException("해당 유저를 찾을 수 없습니다")
        );
        return new ResponseEntity<>(userPS, HttpStatus.OK);
    }
    @GetMapping("/user")
    public ResponseEntity<?> userList(){
        List<User> userListPS = userRepository.findAll();
        return new ResponseEntity<>(userListPS, HttpStatus.OK);
    }
    
    @GetMapping("/product/cart/{id}") // 주소에 동사가 나오는 예외가 생길 수 있다
    public ResponseEntity<?> like(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response){
        String productNames = "";
        Cookie[] cookies = request.getCookies();

        // 장바구니가 존재할 때
        boolean checkCookie = false;
        for (Cookie c: cookies) {
            if(c.getName().equals("cart")){
                productNames += c.getValue()+"/"+id.toString();
                checkCookie = true;
            }
        }

        // 장바구니가 존재하지 않을 때
        if(checkCookie == false){
            productNames = id.toString();
        }

        Cookie cookie = new Cookie("cart", productNames);
        cookie.setPath("/");
        cookie.setMaxAge(1000*60*60); // 1시간
        cookie.setHttpOnly(false); // document.cookie
        response.addCookie(cookie);
        return new ResponseEntity<>("쿠키등록됨", HttpStatus.OK);
    } // 좋아하는 숫자로 쿠키 학습
}
