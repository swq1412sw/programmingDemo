package com.sie.usergl.service;

import com.sie.usergl.util.PageResult;
import com.sie.usergl.vo.SieMenu;

import java.util.List;
import java.util.Map;

public interface MenuService {
    int addMenu(SieMenu menu);
    int deleteMenu(List<SieMenu> menuList);
    int updateMenu(SieMenu menu);
    int stateOnMenu(List<SieMenu> menuList);
    int stateOffMenu(List<SieMenu> menuList);
    PageResult<SieMenu> findAll(Map<String,String>map);
    SieMenu checkMenu(SieMenu menu,boolean flag);
    void cfMenu(SieMenu menu, boolean flag);
    void MenuType2Check(SieMenu menu, boolean flag);
    void MenuType2Check2(SieMenu menu, boolean flag);
    SieMenu setMenu(SieMenu menu,boolean flag);
}
