package com.sie.usergl.service.impl;

import com.sie.usergl.mapper.HelpMapper;
import com.sie.usergl.vo.SieMenu;
import com.sie.usergl.vo.SieRole;
import com.sie.usergl.vo.SieUser;
import mockit.*;
import org.junit.Assert;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class HelpServiceImplTest {

//@InjectMocks
//    private HelpServiceImpl helpService;
//@Mock
//private HelpMapper helpMapper;
//    @Test
//    void relatedQuery() {
//        Mockito.when(helpMapper.findAllRole()).thenReturn(new ArrayList<>());
//        Mockito.when(helpMapper.findRoleForUser(Mockito.anyLong())).thenReturn(new ArrayList<>());
//        Mockito.when(helpMapper.findRoleForUser(null)).thenReturn(new ArrayList<>());
//        Mockito.when(helpMapper.allMenu()).thenReturn(new ArrayList<>());
//        Mockito.when(helpMapper.roleOfMenu(Mockito.anyLong())).thenReturn(new ArrayList<>());
//        Mockito.when(helpMapper.roleOfMenu(null)).thenReturn(new ArrayList<>());
//
//        helpService.relatedQuery(new SieRole());
//        helpService.relatedQuery(new SieUser());
//        helpService.relatedQuery((SieRole) null);
//        helpService.relatedQuery((SieUser) null);
//    }
//
//    @Test
//    void testRelatedQuery() {
//    }
//
//    @Test
//    void criticalProcessing() {
//        Mockito.when(helpMapper.deleteUserRole(Mockito.anyList())).thenReturn(0);
//        Mockito.when(helpMapper.deleteMenuForRole(Mockito.anyLong(),Mockito.anyList())).thenReturn(0);
//        Mockito.when(helpMapper.addMenuForRole(Mockito.anyList())).thenReturn(0);
//        helpService.criticalProcessing(new HashMap<>(),false);
//        helpService.criticalProcessing(new HashMap<>(),true);
//        Map<String, List<Integer>> map=new HashMap<>();
//        List<Integer> id=new ArrayList<>();
//        id.add(1);
//        List<Integer> list=new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        List<Integer> newlist=new ArrayList<>();
//        newlist.add(1);
//        newlist.add(2);
//        newlist.add(5);
//
//        map.put("id",id);
//        map.put("list",list);
//        map.put("newlist",newlist);
//        helpService.criticalProcessing(map,false);
//        helpService.criticalProcessing(map,true);
//
//    }
//
//    @Test
//    void getusermenu() {
//        Mockito.when(helpMapper.userPrivilege(Mockito.anyString())).thenReturn(new ArrayList<>());
//        helpService.getusermenu("");
//    }
@Tested
private HelpServiceImpl helpService;
@Test
void relatedQuery(@Injectable HelpMapper helpMapper){
    new Expectations(){
        {
            helpMapper.findAllRole();
            result=new ArrayList<>();
           helpMapper.findRoleForUser(anyLong);
           result=new ArrayList<>();
           helpMapper.allMenu();
           result=new ArrayList<>();
           helpMapper.roleOfMenu(anyLong);
           result=new ArrayList<>();
        }

    };
    Map<String, Object> map = new HashMap<>();
    map.put("rolelist",new ArrayList<>());
    map.put("rolefor",new ArrayList<>());
    Map<String, Object> map1 = new HashMap<>();
    map1.put("menulist",new ArrayList<>());
    map1.put("menufor",new ArrayList<>());

    SieUser sieUser = new SieUser();
    sieUser.setId(1L);
    SieRole sierole = new SieRole();
    sierole.setId(1L);
    Assert.assertEquals(helpService.relatedQuery(sieUser),map);
    Assert.assertEquals(helpService.relatedQuery((SieUser) null),map);
    Assert.assertEquals(helpService.relatedQuery(sierole),map1);
    Assert.assertEquals(helpService.relatedQuery((SieRole) null),map1);


}
@Test
void criticalProcessing(@Injectable HelpMapper helpMapper){

    new Expectations(){
        {
            helpMapper.addRoleForUser((List<Map<String,Object>>)any);
            result=5;
            helpMapper.addMenuForRole((List<Map<String,Object>>)any);
            result=3;
            helpMapper.deleteRoleForUser(anyLong,(List<Long>)any);
            result=1;
            helpMapper.deleteMenuForRole(anyLong,(List<Long>)any);
            result=2;
        }

    };
    Map<String, List<Integer>> map=new HashMap<>();
        List<Integer> id=new ArrayList<>();
        id.add(1);
        List<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        List<Integer> newlist=new ArrayList<>();
        newlist.add(1);
        newlist.add(2);
        newlist.add(5);

        map.put("id",id);
        map.put("list",list);
        map.put("newlist",newlist);
//    Assert.assertEquals(helpService.criticalProcessing(map,false),4);
//    Assert.assertEquals(helpService.criticalProcessing(map,true),1);
//    Assert.assertEquals(helpService.criticalProcessing(new HashMap<>(),false),0);
//    Assert.assertEquals(helpService.criticalProcessing(new HashMap<>(),true),0);

}
@Test
void getusermenu(@Injectable HelpMapper helpMapper){
    new Expectations(){
        {
           helpMapper.userPrivilege(anyString);
           result=new ArrayList<>();
        }
    };
    SieMenu sieMenu = new SieMenu();
    sieMenu.setId(-1L);

    sieMenu.setLevel(-1);
    sieMenu.setPid(-1L);
    sieMenu.setMenutype("0");
    sieMenu.setName("用户权限管理系统");
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
    List<SieMenu>lists=new ArrayList<>();
    lists.add(sieMenu1);
    lists.addAll(new ArrayList<>());
    sieMenu.setChildlist(lists);
    Assert.assertEquals(helpService.getusermenu(null),list);
}





}