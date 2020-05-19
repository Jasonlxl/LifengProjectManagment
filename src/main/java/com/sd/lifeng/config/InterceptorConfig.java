package com.sd.lifeng.config;

import com.sd.lifeng.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Classname InterceptorConfig
 * @Description TODO
 * @Date 2020/5/19 8:59 :51
 * @Created by bmr
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有的请求
        registry.addInterceptor(authInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public AuthInterceptor authInterceptor(){
        return new AuthInterceptor();
    }
}
