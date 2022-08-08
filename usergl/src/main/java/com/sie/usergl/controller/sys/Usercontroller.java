package com.sie.usergl.controller.sys;

import com.sie.usergl.util.PageResult;
import com.sie.usergl.util.Result;
import com.sie.usergl.service.HelpService;
import com.sie.usergl.service.UserService;
import com.sie.usergl.vo.SieUser;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@ApiOperation("用户模块接口")
public class Usercontroller {
    @Autowired
    private UserService userService;
    @Autowired
    private HelpService helpService;

    @RequestMapping("/findall")
    @PreAuthorize("hasAuthority('user:select') and hasAuthority('/sys/user')")
    @ApiOperation("用户查询")
    public PageResult<SieUser> findalluser(@RequestBody Map<String, String> map) {

        return userService.findalluser(map);
    }

    @RequestMapping("/adduser")
    @PreAuthorize("hasAuthority('user:insert') and hasAuthority('/sys/user')")
    @ApiOperation("添加用户")
    public Result adduser(@RequestBody List<SieUser> users) {

        return Result.success("添加成功", userService.addallusers(users));

    }

    @RequestMapping("/updateuser")
    @PreAuthorize("hasAuthority('user:update') and hasAuthority('/sys/user')")
    @ApiOperation("修改用户")
    public Result updateuser(@RequestBody SieUser user) {
        return Result.success("修改成功", userService.updateuser(user));
    }

    @RequestMapping("/updateuserself")

    @ApiOperation("修改自己")
    public Result updateuserself(@RequestBody SieUser user) {
        return Result.success("修改成功", userService.updateuser(user));
    }

    @RequestMapping("/deteuser")
    @PreAuthorize("hasAuthority('user:delete') and hasAuthority('/sys/user')")
    @ApiOperation("删除用户")
    public Result deleuser(@RequestBody List<SieUser> list) {
        return Result.success("删除成功", userService.deleteuserlist(list));
    }

    @RequestMapping("/stateon")
    @PreAuthorize("hasAuthority('user:update') and hasAuthority('/sys/user')")
    @ApiOperation("启用用户")
    public Result stateon(@RequestBody List<SieUser> list) {
        return Result.success("启用成功", userService.stateonuserlist(list));
    }

    @RequestMapping("/stateoff")
    @PreAuthorize("hasAuthority('user:update') and hasAuthority('/sys/user')")
    @ApiOperation("禁用用户")
    public Result stateoff(@RequestBody List<SieUser> list) {
        return Result.success("禁用成功", userService.stateoffuserlist(list));
    }

    @RequestMapping("/torole")
    @PreAuthorize("hasAuthority('user:updaterole') and hasAuthority('/sys/user')")
    @ApiOperation("查询用户的角色")
    public Result findrole(@RequestBody SieUser user) {
        return Result.success("查询成功", helpService.relatedQuery(user));
    }

    @RequestMapping("/uprole")
    @PreAuthorize("hasAuthority('user:updaterole') and hasAuthority('/sys/user')")
    @ApiOperation("修改用户角色")
    public Result uprole(@RequestBody Map<String, List<Long>> map) {
        return Result.success("设置成功", helpService.criticalProcessing(map,false));
    }
    @RequestMapping("/preallmenu")
    @ApiOperation("查询角色当前页面的权限")
    public Result perAllMenu(@RequestBody Map<String,String>map){
        return Result.success("成功",userService.permissionMenu(map));
    }
    @RequestMapping("/checkroles")
    public Result findPreforRole(@RequestBody Map map){
        return Result.success("成功",helpService.userCheckRoles(map));
    }
    @RequestMapping("/checkmenus")
    public Result findPreforMenu(@RequestBody Map map){
        return Result.success("成功",helpService.userCheckMenus(map));
    }
}
