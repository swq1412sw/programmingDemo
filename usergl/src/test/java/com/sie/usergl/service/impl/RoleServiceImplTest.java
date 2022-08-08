package com.sie.usergl.service.impl;

import com.sie.usergl.exception.SieException;
import com.sie.usergl.mapper.HelpMapper;
import com.sie.usergl.mapper.RoleMapper;

import com.sie.usergl.vo.SieRole;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.*;


@SpringBootTest
class RoleServiceImplTest {
@InjectMocks
    private RoleServiceImpl roleService;
@Mock
private RoleMapper roleMapper;
@Mock
private HelpMapper helpMapper;

    @Test
    void findbyname() {
    }

    @Test
    void findbyid() {
    }

    @Test
    void addrole() {
        this.addRole(null);
        SieRole sieRole = new SieRole();
        this.addRole(sieRole);
        sieRole.setRolename("");
        this.addRole(sieRole);
        sieRole.setRolename("dd");
        sieRole.setRolecode("");
        this.addRole(sieRole);
        sieRole.setRolecode("123");
        this.addRole(sieRole);
        sieRole.setIs_delete("");
        this.addRole(sieRole);
        sieRole.setState("");
        this.addRole(sieRole);
        sieRole.setState("1");
        this.addRole(sieRole);
        sieRole.setState("0");

    }

    @Test
    void updaterole() {
    }
private void  addRole(SieRole role){
        Mockito.when(roleMapper.addrole(Mockito.any())).thenReturn(0);
        Mockito.when(roleMapper.findbyname(Mockito.anyString())).thenReturn(null);
        Mockito.when(roleMapper.findbyname2(Mockito.any())).thenReturn(new SieRole());

    try {
        roleService.addrole(role);
    }catch (SieException e) {

    }
    try {
        roleService.updaterole(role);
    } catch (SieException e) {

    }
}

    @Test
    void deleterolelist() {
        Mockito.when(roleMapper.findrolesbyid(Mockito.anyList())).thenReturn(0).thenReturn(1);
        Mockito.when(helpMapper.deleteUserRole(Mockito.anyList())).thenReturn(0);
        roleService.deleterolelist(null);
        try {
            roleService.deleterolelist(new ArrayList<>());
        } catch (SieException e) {

        }
        List<SieRole>roleList=new ArrayList<>();
        SieRole sieRole = new SieRole();
        roleList.add(sieRole);
        try {
            roleService.deleterolelist(roleList);
        } catch (SieException e) {

        }
        try {
            roleService.deleterolelist(roleList);
        }  catch (SieException e) {

        }

    }

    @Test
    void stateonuserlist() {
        Mockito.when(roleMapper.stateon(Mockito.anyList(),Mockito.any(Date.class))).thenReturn(0);
        SieRole sieRole = new SieRole();
        List<SieRole> roleList=new ArrayList<>();
        roleList.add(sieRole);
        roleService.stateonuserlist(null);
        roleService.stateonuserlist(new ArrayList<>());
        roleService.stateonuserlist(roleList);

    }

    @Test
    void stateoffuserlist() {
        Mockito.when(roleMapper.stateoff(Mockito.anyList(),Mockito.any(Date.class))).thenReturn(0);
        SieRole sieRole = new SieRole();
        List<SieRole> roleList=new ArrayList<>();
        roleList.add(sieRole);
        roleService.stateoffuserlist(null);
        roleService.stateoffuserlist(new ArrayList<>());
        roleService.stateoffuserlist(roleList);
    }

    @Test
    void findall() {
        Mockito.when(roleMapper.sum(Mockito.anyMap())).thenReturn(10);
        Mockito.when(roleMapper.rolelist(Mockito.anyMap())).thenReturn(new ArrayList<>());
        Map<String, String> map = new HashMap<>();
        map.put("rolename","dd");
        map.put("state","0");
        map.put("start","dd");
        map.put("end","dd");
        map.put("page","5");
        map.put("pagetotal","20");
        roleService.findall(map);
        roleService.findall(new HashMap<>());
        roleService.findall(null);
    }

    @Test
    void cfrole() {
    }

    @Test
    void checkrole() {
    }
}