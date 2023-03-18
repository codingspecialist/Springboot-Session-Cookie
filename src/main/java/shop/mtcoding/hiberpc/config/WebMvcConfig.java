package shop.mtcoding.hiberpc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shop.mtcoding.hiberpc.handler.MyLoginArgumentHandler;
import shop.mtcoding.hiberpc.config.interceptor.LoginInterceptor;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/s/*");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/*")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOriginPatterns("*") // 모든 IP 주소 허용 (프론트 앤드 주소만 허용하게 설정하는 것이 좋음)
                .allowCredentials(true) // 클라이언트에서 쿠키 요청 허용
                .exposedHeaders("Authorization"); // JWT 사용하려면 허용해주기
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new MyLoginArgumentHandler());
    }
}
