package com.sie.usergl.service.impl;

import com.sie.usergl.mapper.MenuMapper;
import com.sie.usergl.util.HelpUtil;
import com.sie.usergl.exception.SieException;
import com.sie.usergl.mapper.HelpMapper;
import com.sie.usergl.service.HelpService;
import com.sie.usergl.util.SecurityUtil;
import com.sie.usergl.vo.SieMenu;
import com.sie.usergl.vo.SieRole;
import com.sie.usergl.vo.SieUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HelpServiceImpl implements HelpService {
    @Resource
    private HelpMapper helpMapper;
    @Resource
    private MenuMapper menuMapper;



    @Override
    public Map<String, Object> relatedQuery(SieUser user) {
        SieUser sieUser = new SieUser();
        sieUser.setId(-1L);
        user=user==null?sieUser:user;
        Map<String, Object> map = new HashMap<>();
        List<Long> findroleforuser = helpMapper.findRoleForUser(user.getId());
        List<Map<String, Object>> findallrole = helpMapper.findAllRole();
        map.put("rolelist", findallrole);
        map.put("rolefor", findroleforuser);
        return map;
    }
    @Override
    public Map<String, Object> relatedQuery(SieRole role) {
        SieRole sieRole = new SieRole();
        sieRole.setId(-1L);
        role= role==null?sieRole:role;
        Map<String, Object> map = new HashMap<>();

        List<Long> findmenuforrole = helpMapper.roleOfMenu(role.getId());
        List<SieMenu> findallmenu = HelpUtil.getmenus(helpMapper.allMenu());
        map.put("menulist", findmenuforrole);
        map.put("menufor", findallmenu);
        return map;
    }

    @Override
    @Transactional
    public int criticalProcessing(Map<String, List<Long>> map,boolean flag) {
        int add=0;
        int dele=0;
        List<Long> csh = new ArrayList<>();
        csh.add(-1L);
        List<Long> ids = map.get("id") == null ? csh : (map.get("id"));
        long id = 0L;
        if (!ids.isEmpty()) {
            id = ids.get(0);
        }
        List<Long> list = map.get("list") == null ? new ArrayList<>() : map.get("list");
        List<Long> newlist = map.get("newlist") == null ? new ArrayList<>() : map.get("newlist");
        List<Long> detelist = list.stream().filter(deteid -> !newlist.contains(deteid)).collect(Collectors.toList());
        List<Long> addlist = newlist.stream().filter(addid -> !list.contains(addid)).collect(Collectors.toList());
        if (!detelist.isEmpty()&&!flag) {
            if(new Long(1L).equals(id)&&detelist.contains(1L)){
                throw new SieException("系统账号必须要有管理员");
            }
        dele=  helpMapper.deleteRoleForUser(id, detelist);
        }
        if (!detelist.isEmpty()&&flag){
            if(new Long(1L).equals(id)){
                throw new SieException("管理员的权限只能增加不能删除");
            }
         dele=  helpMapper.deleteMenuForRole(id,detelist);
        }

        List<Map<String, Object>> mapList = new ArrayList<>();
        String idPara=flag?"roleid":"userid";
        String idPara2=flag?"menuid":"roleid";
        for (Long aLong : addlist) {
            Map<String, Object> addmap = new HashMap<>();
            addmap.put(idPara, id);
            addmap.put(idPara2, aLong);
            addmap.put("creatdate", new Date());
            addmap.put("is_delete", "0");
            mapList.add(addmap);
        }
        if (!mapList.isEmpty()&&!flag) {
          add=  helpMapper.addRoleForUser(mapList);
        }
        if (!mapList.isEmpty()&&flag) {
         add=   helpMapper.addMenuForRole(mapList);
        }

        return add-dele;
    }

    @Override
    public List<SieMenu> userCheckMenus(Map<String, String> map) {
        Map<String, String> stringMap = getMap(map);
        List<SieMenu> find = helpMapper.findUserMenutoTime(stringMap);
        List<SieMenu>all =menuMapper.allFind();
        List<Long> collect = find.stream().map(SieMenu::getId).collect(Collectors.toList());
        List<SieMenu> list = new ArrayList<>(find);
        for (SieMenu sieMenu : find) {
            list = HelpUtil.lists(all, sieMenu, collect, sieMenu, list);
        }
        return list;
    }

    private Map<String, String> getMap(Map<String, String> map) {
        String name = map.get("name") == null ? "%%" : "%" + map.get("name") + "%";
        String menutype = map.get("menutype") == null ? "%%" : "%" + map.get("menutype") + "%";
        String state = map.get("state") == null ? "%%" : "%" + map.get("state") + "%";
        String username = SecurityUtil.getUsername();
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("name", name);
        stringMap.put("menutype", menutype);
        stringMap.put("state", state);
        stringMap.put("username", username);
        return stringMap;
    }

    @Override
    public List<SieRole> userCheckRoles(Map<String, String> map) {
        Map<String, String> map1 = getMap(map);
        List<SieRole> userRoletoTime = helpMapper.findUserRoletoTime(map1);
        return userRoletoTime;
    }


    @Override
    public List<SieMenu> getusermenu(String name) {
        List<SieMenu> userrights = helpMapper.userPrivilege(name);
        List<SieMenu> getmenus = HelpUtil.getmenus(userrights);

        SieMenu sieMenu = new SieMenu();
        sieMenu.setId(-1L);

        sieMenu.setLevel(-1);
        sieMenu.setPid(-1L);
        sieMenu.setMenutype("0");
        sieMenu.setName("用户权限管理系统");
        sieMenu.setIco("fa fa-list-ul");
        sieMenu.setOrderno(0);
        sieMenu.setState("0");
        sieMenu.setIs_delete("0");
        List<SieMenu> list = new ArrayList<>();
        list.add(sieMenu);
        SieMenu sieMenu1 = new SieMenu();
        sieMenu1.setId(-2L);
        sieMenu1.setChildlist(new ArrayList<>());
        sieMenu1.setLevel(0);
        sieMenu1.setPid(-1L);
        sieMenu1.setOrderno(-1);
        sieMenu1.setState("0");
        sieMenu1.setIs_delete("0");
        sieMenu1.setMenutype("1");
        sieMenu1.setPath("/index");
        sieMenu1.setName("首页");
        sieMenu1.setIco("fa fa-sticky-note");
       List<SieMenu>lists=new ArrayList<>();
       lists.add(sieMenu1);
       lists.addAll(getmenus);
        sieMenu.setChildlist(lists);
        return list;
    }
}
