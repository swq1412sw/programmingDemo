package com.sie.usergl.controller.sys;


import com.sie.usergl.util.Result;
import com.sie.usergl.service.MenuService;

import com.sie.usergl.vo.SieMenu;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
@ApiOperation("角色模块接口")
public class Menucontroller {
    @Autowired
    private MenuService menuService;

    @RequestMapping("/findall")
    @PreAuthorize("hasAuthority('menu:select') and hasAuthority('/sys/menus')")
    @ApiOperation("菜单查询")
    public Result findAll(@RequestBody Map<String, String> map) {
        return menuService.findAll(map);
    }

    @RequestMapping("/addmenu")
    @PreAuthorize("hasAuthority('menu:insert') and hasAuthority('/sys/menus')")
    @ApiOperation("添加菜单")
    public Result addMenu(@RequestBody SieMenu menu) {

        return Result.success("添加成功", menuService.addMenu(menu));

    }

    @RequestMapping("/updatemenu")
    @PreAuthorize("hasAuthority('menu:update') and hasAuthority('/sys/menus')")
    @ApiOperation("修改菜单")
    public Result updateMenu(@RequestBody SieMenu menu) {
        return Result.success("修改成功", menuService.updateMenu(menu));
    }

    @RequestMapping("/delemenu")
    @PreAuthorize("hasAuthority('menu:delete') and hasAuthority('/sys/menus')")
    @ApiOperation("删除菜单")
    public Result deleMenu(@RequestBody List<SieMenu> list) {
        return Result.success("删除成功", menuService.deleteMenu(list));
    }

    @RequestMapping("/stateon")
    @PreAuthorize("hasAuthority('menu:update') and hasAuthority('/sys/menus')")
    @ApiOperation("启用菜单")
    public Result stateOn(@RequestBody List<SieMenu> list) {
        return Result.success("启用成功", menuService.stateOnMenu(list));
    }

    @RequestMapping("/stateoff")
    @PreAuthorize("hasAuthority('menu:update') and hasAuthority('/sys/menus')")
    @ApiOperation("禁用菜单")
    public Result stateOff(@RequestBody List<SieMenu> list) {
        return Result.success("禁用成功", menuService.stateOffMenu(list));
    }
}
