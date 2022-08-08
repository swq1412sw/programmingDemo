package com.sie.usergl.service;

import com.sie.usergl.util.PageResult;
import com.sie.usergl.vo.SieRole;

import java.util.List;
import java.util.Map;

public interface RoleService {
    public SieRole findbyname(String name);
    public SieRole findbyid(Long id);
    public int addrole(SieRole role);
    public int updaterole(SieRole role);
    public int deleterolelist(List<SieRole> list);
    public  int stateonuserlist(List<SieRole>list);
    public  int stateoffuserlist(List<SieRole>list);
    public PageResult<SieRole> findall(Map<String,String>map);
    public void cfrole(SieRole role,boolean flag);
    public  void checkrole(SieRole role,boolean flag);
}
