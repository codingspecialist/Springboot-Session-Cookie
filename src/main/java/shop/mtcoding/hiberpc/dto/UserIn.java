package shop.mtcoding.hiberpc.dto;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.hiberpc.model.User;

public class UserIn {

    @Getter @Setter
    public static class JoinInDto{
        private String username;
        private String password;
        private String email;

        public User toEntity(){
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .build();
        }
    }

    @Getter @Setter
    public static class LoginInDto{
        private String username;
        private String password;
    }
}
