package com.huizhi.oauth.service;

import com.huizhi.oauth.modal.OauthUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LDZ
 * @date 2020-03-18 15:40
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {


    private static UserInfo HUI = new UserInfo("huihui", "hhhh", new String[]{"admin", "user"});
    private static UserInfo ZHI = new UserInfo("zhizhi", "zzzz", new String[]{"admin"});
    private static UserInfo JI = new UserInfo("jiji", "jjjj", new String[]{"user"});

    private Map<String, UserInfo> userInfoMap = new HashMap<String, UserInfo>() {{
        put(HUI.username, HUI);
        put(ZHI.username, ZHI);
        put(JI.username, JI);
    }};

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        UserInfo userInfo = userInfoMap.get(userName);

        User user = (User) User.withUsername(userName).
                password(userInfo.password)
                .roles(userInfo.getRoles())
                .build();

        return new OauthUser(user);

    }


    @Data
    @AllArgsConstructor
    static
    class UserInfo {
        String username;

        String password;

        String[] roles;
    }

}
