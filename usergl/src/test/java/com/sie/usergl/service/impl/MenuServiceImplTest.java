package com.sie.usergl.service.impl;

import com.sie.usergl.exception.SieException;
import com.sie.usergl.mapper.MenuMapper;
import com.sie.usergl.vo.SieMenu;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.mockito.Incubating;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class
MenuServiceImplTest {

    @InjectMocks
    private MenuServiceImpl menuService;

    @Mock
    private MenuMapper menuMapper;
    @Test
    void addMenu() {
        SieMenu menu = new SieMenu();
        menu.setId(0L);
        menu.setCreatdate(new Date());
        menu.setUpdatetime(new Date());
        menu.setLevel(0);
        menu.setState("0");
        menu.setPath("d");
        menu.setMenutype("1");
        menu.setPid(-1L);
        menu.setName("ddd");
        menu.setLogo("dd");
        menu.setIs_delete("0");
        menu.setOrderno(0);
        this.addmenu(menu);
        this.addmenu(menu);
        this.addmenu(menu);
        menu.setMenutype(null);
        this.addmenu(menu);
        menu.setMenutype("");
        this.addmenu(menu);
        menu.setMenutype("1");
        menu.setPid(-1L);
        this.addmenu(menu);
        menu.setName(null);
        this.addmenu(menu);
        menu.setName("");
        this.addmenu(menu);
        menu.setName("dd");
        menu.setMenutype("1");
        menu.setPath(null);
        this.addmenu(menu);
        menu.setPath("");
        this.addmenu(menu);
        menu.setPath("dd");
        menu.setPid(null);
        this.addmenu(menu);
        menu.setPid(-1L);
        this.addmenu(menu);
        menu.setMenutype("2");
        menu.setLogo(null);
        this.addmenu(menu);
        menu.setLogo("");
        this.addmenu(menu);
        menu.setLogo("dd");
        menu.setPid(null);
        this.addmenu(menu);
        menu.setPid(-1L);

        this.addmenu(menu);
        this.addmenu(menu);
        this.addmenu(menu);
        menu.setCreatdate(null);
        menu.setUpdatetime(null);
        this.addmenu(menu);
        menu.setState("1");
        menu.setMenutype("0");
        this.addmenu(menu);
    }
 private void addmenu(SieMenu menu){
        SieMenu menu1=new SieMenu();
        menu1.setMenutype("0");
        SieMenu menu2=new SieMenu();
        menu1.setMenutype("1");
        SieMenu menu3=new SieMenu();
        menu1.setMenutype("2");
        Mockito.when(menuMapper.addMenu(Mockito.any())).thenReturn(0);
        Mockito.when(menuMapper.updateMenu(Mockito.any())).thenReturn(0);
       Mockito.when(menuMapper.findById(Mockito.anyInt())).thenReturn(menu3).thenReturn(menu2).thenReturn(menu1);
        Mockito.when(menuMapper.findByLogo(Mockito.any())).thenReturn(new SieMenu());
        Mockito.when(menuMapper.findByLogo2(Mockito.any())).thenReturn(new SieMenu());
        Mockito.when(menuMapper.findByPath(Mockito.any())).thenReturn(new SieMenu());
        Mockito.when(menuMapper.findByPath2(Mockito.any())).thenReturn(new SieMenu());
       Mockito.when(menuMapper.orderNo(Mockito.anyInt())).thenReturn(0);

this.addmenu2(menu);
        try {
         menuService.addMenu(menu);
     }catch (SieException e) {
        
     }
     try {
         menuService.updateMenu(menu);
     }catch (SieException e) {
        
     }
 }

    private void addmenu2(SieMenu menu){
        SieMenu menu1=new SieMenu();
        menu1.setMenutype("1");
        SieMenu menu2=new SieMenu();
        menu1.setMenutype("0");
        SieMenu menu3=new SieMenu();
        menu1.setMenutype("2");
        Mockito.when(menuMapper.addMenu(Mockito.any())).thenReturn(0);
        Mockito.when(menuMapper.updateMenu(Mockito.any())).thenReturn(0);
        Mockito.when(menuMapper.findById(Mockito.anyInt())).thenReturn(menu1).thenReturn(menu2).thenReturn(menu3);
        Mockito.when(menuMapper.findByLogo(Mockito.any())).thenReturn(null);
        Mockito.when(menuMapper.findByLogo2(Mockito.any())).thenReturn(null);
        Mockito.when(menuMapper.findByPath(Mockito.any())).thenReturn(null);
        Mockito.when(menuMapper.findByPath2(Mockito.any())).thenReturn(null);
        Mockito.when(menuMapper.orderNo(Mockito.anyInt())).thenReturn(0);


        try {
            menuService.addMenu(menu);
        } catch (SieException e) {
           
        }
        try {
            menuService.updateMenu(menu);
        } catch (SieException e) {
          
        }
    }
    @Test
    void checkMenu() {
    }

    @Test
    void cfMenu() {
    }

    @Test
    void menuType2Check() {
    }

    @Test
    void setMenu() {
    }

    @Test
    void deleteMenu() {
        List<SieMenu> lists=new ArrayList<>();
        List<SieMenu> lists1=new ArrayList<>();
        SieMenu menu= new SieMenu();
        menu.setId(0L);
        lists.add(menu);
        lists1.add(menu);
        Mockito.when(menuMapper.findIsDelete(Mockito.anyList())).thenReturn(0).thenReturn(1);
//        Mockito.when(menuMapper.findIsDelete(Mockito.mock(List.class,"lists1"))).thenReturn(1);
        Mockito.when(menuMapper.deleteMenu(Mockito.anyList())).thenReturn(0);
        try {
            menuService.deleteMenu(lists);
        }  catch (SieException e) {
           
        }
        try {
            menuService.deleteMenu(new ArrayList<SieMenu>());
        }catch (SieException e) {
          
        }
        try {
            menuService.deleteMenu(lists1);
        } catch (SieException e) {
           
        }
        menuService.deleteMenu(null);

    }

    @Test
    void updateMenu() {
    }

    @Test
    void stateOnMenu() {
        List<SieMenu> sieMenus=new ArrayList<>();
        SieMenu menu= new SieMenu();
        menu.setId(0L);
        sieMenus.add(menu);
        Mockito.when(menuMapper.stateOn(Mockito.anyList(),Mockito.any(Date.class))).thenReturn(0);
        menuService.stateOnMenu(sieMenus);
        menuService.stateOnMenu(new ArrayList<SieMenu>());
    }

    @Test
    void stateOffMenu() {
        List<SieMenu> sieMenus=new ArrayList<>();
        SieMenu menu= new SieMenu();
        menu.setId(0L);
        sieMenus.add(menu);
        Mockito.when(menuMapper.stateOff(Mockito.anyList(),Mockito.any(Date.class))).thenReturn(0);
        menuService.stateOffMenu(sieMenus);
        menuService.stateOffMenu(new ArrayList<SieMenu>());
    }

    @Test
    void findAll() {
        Map<String,String> map =new HashMap<>();
        map.put("name","新增用户");
        map.put("state","0");
        map.put("menutype","2");
        List<SieMenu> find = new ArrayList<>();
        SieMenu sieMenu = new SieMenu();
        sieMenu.setLevel(1);
        sieMenu.setPid(-1L);
        sieMenu.setId(1L);
        find.add(sieMenu);
        Mockito.when(menuMapper.findAll(Mockito.anyMap())).thenReturn(find);
        Mockito.when(menuMapper.allFind()).thenReturn(find);
        menuService.findAll(map);
        map.put("name",null);
        map.put("state",null);
        map.put("menutype",null);
        menuService.findAll(map);
        menuService.findAll(null);
    }
}