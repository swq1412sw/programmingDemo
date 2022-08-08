package com.sie.usergl.controller;

import com.sie.usergl.util.Result;
import com.sie.usergl.util.SecurityUtil;
import com.sie.usergl.util.TokenUtil;
import com.sie.usergl.service.HelpService;
import com.sie.usergl.service.impl.UserServiceImpl;
import com.sie.usergl.vo.SieMenu;
import com.sie.usergl.vo.SieUser;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/log")
@ApiOperation("系统登录接口")
public class Logincontroller {
    @Autowired
    private UserServiceImpl userService;

    @Value("${jwt.tokenhead}")
    private String tokenhead;
    @Autowired
    private HelpService helpService;


    @RequestMapping("/login")
    @ApiOperation("登录接口")
    public Result login(@RequestBody SieUser user) {
        return userService.login(user);
    }

    @RequestMapping("/info")
    @ApiOperation("用户信息获取接口")
    public Result getUserInfo(Principal principal) {
        if (principal == null) {
            return Result.fail("你还没登录呢");
        }
        Map<String, Object> userInfo = SecurityUtil.getUserInfo();
        userInfo.put("token", TokenUtil.refreshToken(TokenUtil.tokenMap.get(SecurityUtil.getUsername())));
        userInfo.put("tokenhead", tokenhead);
        List<SieMenu> getusermenu = helpService.getusermenu(SecurityUtil.getUsername());
        userInfo.put("list", getusermenu);
        return Result.success("获取用户消息成功", userInfo);
    }

    @ApiOperation(value = "用户退出")
    @RequestMapping("/logout")
    public Result logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return Result.success("退出成功", "期待您下次登录");
    }
}
