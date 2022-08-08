package com.sie.usergl.util;

import com.sie.usergl.vo.SieUser;
import java.util.Collections;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;


public class SecurityUtil {

    /**
     * 获取用户名
     *
     * */
    public static String getUsername() {
        try {
            return ((SieUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取用户ID
     *
     */
    public static Long getUserId() {
        try {
            return ((SieUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取用户信息
     *
     */
    public static Map<String,Object> getUserInfo() {
        try {
          SieUser user = (SieUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Map<String,Object> map =new HashMap<>();
            map.put("user",user);

            return map ;
        } catch (Exception e) {

            return Collections.emptyMap();
        }
    }


}
