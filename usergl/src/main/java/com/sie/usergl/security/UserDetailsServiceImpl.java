package com.sie.usergl.security;




import com.sie.usergl.service.UserService;
import com.sie.usergl.vo.SieUser;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {



    private UserService userService;


    public UserDetailsServiceImpl(@Lazy UserService userService) {

        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SieUser user= userService.founndByUsername(username);
        if (user==null){
            throw  new UsernameNotFoundException("用户名或密码错误");
        }
        user.setAuthors(userService.permission(username));


        return user;


    }


}
