package com.huizhi.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * 安全配置 咔咔安全
 *
 * @author LDZ
 * @date 2020-03-18 15:08
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;

    /**
     * http 配置安全
     *
     * @param http http 安全对象
     * @throws Exception http安全认证异常信息
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .requestMatcher(new OAuth2RequestedMatcher())
                .authorizeRequests()
//                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .antMatchers("/.well-known/jwks.json").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic().and().csrf().disable();
    }

//    /**
//     * 定义OAuth2请求匹配器
//     */
//    private static class OAuth2RequestedMatcher implements RequestMatcher {
//        @Override
//        public boolean matches(HttpServletRequest request) {
//            String auth = request.getHeader("Authorization");
//            //判断来源请求是否包含oauth2授权信息,这里授权信息来源可能是头部的Authorization值以Bearer开头,或者是请求参数中包含access_token参数,满足其中一个则匹配成功
//            boolean haveOauth2Token = (auth != null) && auth.startsWith("Bearer");
//            boolean haveAccessToken = request.getParameter("access_token") != null;
//            return haveOauth2Token || haveAccessToken;
//        }
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
