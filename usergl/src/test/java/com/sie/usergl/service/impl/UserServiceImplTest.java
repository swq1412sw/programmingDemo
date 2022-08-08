package com.sie.usergl.service.impl;

import com.sie.usergl.util.MD5Util;
import com.sie.usergl.util.SecretUtil;
import com.sie.usergl.exception.SieException;
import com.sie.usergl.mapper.UserMapper;
import com.sie.usergl.security.UserDetailsServiceImpl;
import com.sie.usergl.vo.SieUser;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@SpringBootTest
class UserServiceImplTest {
@InjectMocks
    private UserServiceImpl userService;
@Mock
private UserDetailsServiceImpl userDetailsService;
@Mock
private PasswordEncoder passwordEncoder;

@Mock
private UserMapper userMapper;
    @Test
    void permission() {
        Mockito.when(userMapper.permission(Mockito.anyString())).thenReturn(new ArrayList<>());
        userService.permission("");
    }

    @Test
    void permissionMenu() {
        Mockito.when(userMapper.permissionMenu(Mockito.anyString(),Mockito.anyString())).thenReturn(new ArrayList<>());
        HashMap<String, String> map = new HashMap<>();
        userService.permissionMenu(map);userService.permissionMenu(null);
        map.put("name","");
        map.put("path","");
        userService.permissionMenu(map);

    }

    @Test
    void founndByUsername() {
    }

    @Test
    void login() {
        SieUser sieUser = new SieUser();
        sieUser.setUsername("sieadmin");
        sieUser.setPassword(MD5Util.encode("123456"));

        SieUser sieUser4 = new SieUser();
        sieUser4.setUsername("sieadminm");
        sieUser4.setPassword(MD5Util.encode("1234567"));

        SieUser sieUser1 = new SieUser();
        sieUser1.setUsername("sieadmin");
        sieUser1.setPassword(SecretUtil.encrypt("123456",SecretUtil.KEY,SecretUtil.IV));

        SieUser sieUser3 = new SieUser();
        sieUser3.setUsername("sieadminm");
        sieUser3.setPassword(SecretUtil.encrypt("123456",SecretUtil.KEY,SecretUtil.IV));

        SieUser sieUser5 = new SieUser();
        sieUser5.setUsername("sieadminn");
        sieUser5.setPassword(SecretUtil.encrypt("123456",SecretUtil.KEY,SecretUtil.IV));
        Mockito.when(userDetailsService.loadUserByUsername("sieadmin")).thenReturn(sieUser);

        Mockito.when(userDetailsService.loadUserByUsername("sieadminn")).thenReturn(new SieUser());
        Mockito.when(userDetailsService.loadUserByUsername("sieadminm")).thenReturn(sieUser4);
        Mockito.when(userMapper.foundbyusername("sieadminn")).thenThrow(new SieException("没有账号密码"));
        Mockito.when(userMapper.foundbyusername("sieadmin")).thenReturn(sieUser);
        Mockito.when(userMapper.foundbyusername("sieadminm")).thenReturn(sieUser4);
        Mockito.when(userMapper.permission(Mockito.anyString())).thenReturn(new ArrayList<>());
        Mockito.when(passwordEncoder.matches("123456",MD5Util.encode("123456"))).thenReturn(true);
        Mockito.when(passwordEncoder.matches("1234567",MD5Util.encode("1234567"))).thenReturn(false);
        try {
            userService.login(sieUser1);
        }  catch (SieException e) {

        }
        try {
            userService.login(sieUser3);
        }catch (SieException e) {

        }try {
            userService.login(sieUser5);
        }  catch (SieException e) {

        }
    }

    @Test
    void checkuser() {
    }

    @Test
    void noempty() {
        SieUser sieUser = new SieUser();
        userService.noempty(sieUser);
        sieUser.setState("0");
        sieUser.setGender("0");
        sieUser.setIs_delete("0");
        sieUser.setPhone("");
        sieUser.setEmail("");
        sieUser.setNote("");
        userService.noempty(sieUser);
    }

    @Test
    void adduser() {
    }

    @Test
    void cm() {
    }

    @Test
    void addallusers() {
        Mockito.when(userMapper.adduserList(Mockito.anyList())).thenReturn(0);
        List<SieUser>users=new ArrayList<>();
        SieUser sieUser = new SieUser();
        sieUser.setUsername("username");
        sieUser.setNickname("NICKNAME");
        sieUser.setPassword(SecretUtil.encrypt("123456",SecretUtil.KEY,SecretUtil.IV));

        SieUser sieUser1 = new SieUser();
        sieUser1.setUsername("username");
        sieUser1.setNickname("NICKNAME");
        sieUser1.setPassword(SecretUtil.encrypt("123456",SecretUtil.KEY,SecretUtil.IV));

        try {
            userService.addallusers(null);
        }catch (SieException e) {

        }
        try {
            userService.addallusers(users);
        }  catch (SieException e) {

        }
        users.add(sieUser);
        try {
            userService.addallusers(users);
        } catch (SieException e) {

        }
        users.add(sieUser1);
        try {
            userService.addallusers(users);
        } catch (SieException e) {

        }
        users.add(new SieUser());
        try {
            userService.addallusers(users);
        }  catch (SieException e) {

        }

        try {
            userService.updateuser(sieUser);
        }  catch (SieException e) {

        }try {
            userService.updateuser(sieUser1);
        }  catch (SieException e) {

        }
    }

    @Test
    void updateuser() {
    }

    @Test
    void deleteuserlist() {
        Mockito.when(userMapper.deleteuser(Mockito.anyList())).thenReturn(0);
        List<SieUser> list=new ArrayList<>();
        SieUser sieUser = new SieUser();
        list.add(sieUser);
        userService.deleteuserlist(null);
        userService.deleteuserlist(new ArrayList<>());
        userService.deleteuserlist(list);
    }

    @Test
    void stateonuserlist() {
        Mockito.when(userMapper.stateon(Mockito.anyList(),Mockito.any(Date.class))).thenReturn(0);
        List<SieUser> list=new ArrayList<>();
        SieUser sieUser = new SieUser();
        list.add(sieUser);
        userService.stateonuserlist(null);
        userService.stateonuserlist(new ArrayList<>());
        userService.stateonuserlist(list);
    }

    @Test
    void stateoffuserlist() {
        Mockito.when(userMapper.stateoff(Mockito.anyList(),Mockito.any(Date.class))).thenReturn(0);
        List<SieUser> list=new ArrayList<>();
        SieUser sieUser = new SieUser();
        list.add(sieUser);
        userService.stateoffuserlist(null);
        userService.stateoffuserlist(new ArrayList<>());
        userService.stateoffuserlist(list);
    }

    @Test
    void findalluser() {
        Mockito.when(userMapper.sum(Mockito.anyMap())).thenReturn(10);
        Mockito.when(userMapper.userlist(Mockito.anyMap())).thenReturn(new ArrayList<>());
        Map<String, String> map = new HashMap<>();
        map.put("username","dd");
        map.put("nickname","dd");
        map.put("gender","dd");
        map.put("state","0");
        map.put("start","dd");
        map.put("end","dd");
        map.put("page","5");
        map.put("pagetotal","20");
        userService.findalluser(map);
        userService.findalluser(new HashMap<>());
        userService.findalluser(null);
    }
}