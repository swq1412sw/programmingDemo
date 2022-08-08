package com.sie.usergl.service;

import com.sie.usergl.vo.SieMenu;
import com.sie.usergl.vo.SieRole;
import com.sie.usergl.vo.SieUser;

import java.util.List;
import java.util.Map;


public interface HelpService {
     List<SieMenu> getusermenu(String name);


     Map<String,Object> relatedQuery(SieUser user);
     Map<String, Object> relatedQuery(SieRole role);
     int criticalProcessing(Map<String,List<Long>> map,boolean flag);
     List<SieMenu>userCheckMenus(Map<String,String> map);
     List<SieRole>userCheckRoles(Map<String,String> map);
}
