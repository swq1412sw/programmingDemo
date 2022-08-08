package com.sie.usergl.service;

import com.sie.usergl.util.PageResult;
import com.sie.usergl.util.Result;
import com.sie.usergl.vo.SieUser;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<String>permission(String name);
    List<String>permissionMenu(Map<String,String>map);
    public SieUser founndByUsername(String name);
    public Result login(SieUser user);
    //判断是否合格
    public void checkuser(SieUser user,boolean flag2);
    //去除为NULL的数据
    public void noempty(SieUser user);
    //添加数据
    public int adduser(SieUser user);
    public  int deleteuserlist(List<SieUser>list);
    public  int stateonuserlist(List<SieUser>list);
    public  int stateoffuserlist(List<SieUser>list);
    public PageResult<SieUser> findalluser(Map<String,String> map);
    public void cm(SieUser foundbyusername);
    public int addallusers(List<SieUser>users);

    public int updateuser(SieUser user);
}
