package com.sie.usergl.controller.sys;

import com.sie.usergl.util.PageResult;
import com.sie.usergl.util.Result;
import com.sie.usergl.service.HelpService;
import com.sie.usergl.service.RoleService;
import com.sie.usergl.vo.SieRole;


import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
@ApiOperation("角色模块接口")
public class Rolecontroller {
    @Autowired
    private RoleService roleService;
    @Autowired
private HelpService helpService;

    @RequestMapping("/findall")
    @PreAuthorize("hasAuthority('role:select') and hasAuthority('/sys/role')")
    @ApiOperation("角色查询")
    public PageResult<SieRole> findallrole(@RequestBody Map<String, String> map) {
        return roleService.findall(map);
    }

    @RequestMapping("/addrole")
    @PreAuthorize("hasAuthority('role:insert') and hasAuthority('/sys/role')")
    @ApiOperation("添加角色")
    public Result addurole(@RequestBody SieRole role) {

        return Result.success("添加成功", roleService.addrole(role));

    }

    @RequestMapping("/updaterole")
    @PreAuthorize("hasAuthority('role:update') and hasAuthority('/sys/role')")
    @ApiOperation("修改角色")
    public Result updaterole(@RequestBody SieRole role) {
        return Result.success("修改成功", roleService.updaterole(role));
    }

    @RequestMapping("/delerole")
    @PreAuthorize("hasAuthority('role:delete') and hasAuthority('/sys/role')")
    @ApiOperation("删除角色")
    public Result delerole(@RequestBody List<SieRole> list) {
        return Result.success("删除成功", roleService.deleterolelist(list));
    }

    @RequestMapping("/stateon")
    @PreAuthorize("hasAuthority('role:update') and hasAuthority('/sys/role')")
    @ApiOperation("启用角色")
    public Result stateon(@RequestBody List<SieRole> list) {
        return Result.success("启用成功", roleService.stateonuserlist(list));
    }

    @RequestMapping("/stateoff")
    @PreAuthorize("hasAuthority('role:update') and hasAuthority('/sys/role')")
    @ApiOperation("禁用角色")
    public Result stateoff(@RequestBody List<SieRole> list) {
        return Result.success("禁用成功", roleService.stateoffuserlist(list));
    }
    @RequestMapping("/tomenu")
    @PreAuthorize("hasAuthority('role:updatemenu') and hasAuthority('/sys/role')")
    @ApiOperation("查询角色的目录")
    public Result findMenu(@RequestBody SieRole role) {
        return Result.success("查询成功", helpService.relatedQuery(role));
    }

    @RequestMapping("/upmenu")
    @PreAuthorize("hasAuthority('role:updatemenu') and hasAuthority('/sys/role')")
    @ApiOperation("修改角色目录")
    public Result upMenu(@RequestBody Map<String, List<Long>> map) {
        return Result.success("设置成功", helpService.criticalProcessing(map,true));
    }

}
