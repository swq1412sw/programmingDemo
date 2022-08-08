package com.sie.usergl.service.impl;

import com.sie.usergl.util.HelpUtil;

import com.sie.usergl.util.PageResult;
import com.sie.usergl.exception.SieException;
import com.sie.usergl.mapper.HelpMapper;
import com.sie.usergl.mapper.MenuMapper;
import com.sie.usergl.service.MenuService;
import com.sie.usergl.vo.SieMenu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;
@Resource
    private HelpMapper helpMapper;
    @Override
    public int addMenu(SieMenu menu) {
        menu = checkMenu(menu, false);

        return menuMapper.addMenu(menu);
    }

    @Override
    public SieMenu checkMenu(SieMenu menu, boolean flag) {
        if (menu.getMenutype() == null || menu.getMenutype().length() == 0) {
            throw new SieException("未选择菜单类别");
        }
        if (menu.getName() == null || menu.getName().length() == 0) {
            throw new SieException("菜单名称不能为空");
        }
        cfMenu(menu, flag);
        menu = setMenu(menu, flag);
        return menu;
    }

    @Override
    public void cfMenu(SieMenu menu, boolean flag) {
        if ("1".equals(menu.getMenutype())) {
            MenuType2Check(menu, flag);
        }
        if ("2".equals(menu.getMenutype())) {
            if (menu.getLogo() == null || menu.getLogo().length() == 0) {
                throw new SieException("功能类型的logo不能为空");
            }
            if (menu.getPid() == null || menu.getPid().equals((long) -1)) {
                throw new SieException("功能类型的上级菜单必须存在");
            }
            SieMenu byId = menuMapper.findById(menu.getPid());

            if (byId == null || !"1".equals(byId.getMenutype())) {
                throw new SieException("功能类型的上级菜单必须是存在的菜单类型");
            }
            MenuType2Check2(menu,flag);
        }
        if ("0".equals(menu.getMenutype()) && "1".equals(menu.getState())){
            throw new SieException("目录不可处于禁用状态");
        }
    }

    //*
// 检查菜单类型为菜单的path是否为空，是否重复
//
// */
    @Override
    public void MenuType2Check(SieMenu menu, boolean flag) {
        if (menu.getPath() == null || menu.getPath().length() == 0) {
            throw new SieException("菜单类型的path不能为空");
        }
        SieMenu byPath;
        if (flag) {
            byPath = menuMapper.findByPath2(menu);
        } else {
            byPath = menuMapper.findByPath(menu.getPath());

        }
        if (byPath != null) {
            throw new SieException("菜单的path重复");
        }
    }

    @Override
    public void MenuType2Check2(SieMenu menu, boolean flag) {
        if (menu.getLogo() == null || menu.getLogo().length() == 0) {
            throw new SieException("功能类型的标识不能为空");
        }
        SieMenu byPath;
        if (flag) {

            byPath = menuMapper.findByLogo2(menu);
        } else {
            byPath = menuMapper.findByLogo(menu.getLogo());
        }
        if (byPath != null) {
            throw new SieException("功能的标识重复");
        }
    }

    @Override
    public SieMenu setMenu(SieMenu menu, boolean flag) {
        if (flag) {
            menu.setUpdatetime(new Date());
        } else {
            menu.setCreatdate(new Date());
        }
        if (new Long(-1L).equals(menu.getPid())) {
            menu.setLevel(0);
        } else {
            try {
                menu.setLevel(menuMapper.findById(menu.getPid()).getLevel() + 1);
            } catch (Exception e) {
                throw new SieException("上级目录设置异常");
            }
        }
        menu.setIs_delete("0");
        if (menu.getOrderno() == null) {
            menu.setOrderno(menuMapper.orderNo(menu.getPid()));
        } if (menu.getState()==null||menu.getState().length()==0){
            menu.setState("0");
        }
        return menu;
    }

    @Override
    @Transactional
    public int deleteMenu(List<SieMenu> menuList) {
        if (menuList == null || menuList.isEmpty()) {
            return 0;
        }
        int isDelete = menuMapper.findIsDelete(menuList);
        System.out.println(isDelete);
        if (isDelete!=0){
            throw  new SieException("选择的菜单中，有其子菜单未被选择删除，故无法删除");
        }
        menuMapper.deleteMenu(menuList);
        return helpMapper.deleteRoleMenu(menuList);
    }

    @Override
    public int updateMenu(SieMenu menu) {
        menu = checkMenu(menu, true);

        return menuMapper.updateMenu(menu);
    }

    @Override
    public int stateOnMenu(List<SieMenu> menuList) {
        if (menuList == null || menuList.isEmpty()) {
            return 0;
        }
        Date date = new Date();
        return menuMapper.stateOn(menuList,date);
    }

    @Override
    public int stateOffMenu(List<SieMenu> menuList) {
        if (menuList == null || menuList.isEmpty()) {
            return 0;
        }
        Date date = new Date();
        return menuMapper.stateOff(menuList,date);
    }

    @Override
    public PageResult<SieMenu> findAll(Map<String, String> map) {
        if (map==null){
            map=new HashMap<>();
        }
        String cl1 = "name";
        String cl2 = "type";
        String cl3 = "state";
        String cl4 = "%";
        String cl5 = "%%";
        String name = map.get(cl1) == null ? cl5 : cl4 + map.get(cl1) + cl4;
        String type = map.get(cl2) == null ? cl5 : cl4 + map.get(cl2) + cl4;
        String state = map.get(cl3) == null ? cl5 : cl4 + map.get(cl3) + cl4;
        String end = map.get("end");
        String start = map.get("start");
        String pagetotal = map.get("pagetotal") == null ? "20" : map.get("pagetotal") + "";
        String page = map.get("page") == null ? "1" : map.get("page") + "";
        Map<String, Object> findmap = new HashMap<>();
        findmap.put("name", name);
        findmap.put("type", type);
        findmap.put("state", state);
        HelpUtil.ssk(state, start, end, page, pagetotal, findmap);
        List<SieMenu> find = menuMapper.findAll(findmap);
        List<SieMenu>all =menuMapper.allFind();
        List<Long> collect = find.stream().map(SieMenu::getId).collect(Collectors.toList());
        List<SieMenu> list = new ArrayList<>(find);
        for (SieMenu sieMenu : find) {
           list = HelpUtil.lists(all, sieMenu, collect, sieMenu, list);

        }
        PageResult<SieMenu> sieMenuPageResult = new PageResult<SieMenu>(find.size(),list);
        return  sieMenuPageResult;
    }
}
