package com.sie.usergl.service.impl;

import com.sie.usergl.util.HelpUtil;
import com.sie.usergl.util.PageResult;
import com.sie.usergl.exception.SieException;
import com.sie.usergl.mapper.HelpMapper;
import com.sie.usergl.mapper.RoleMapper;
import com.sie.usergl.service.RoleService;
import com.sie.usergl.vo.SieRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private HelpMapper helpMapper;

    @Override
    public SieRole findbyname(String name) {
        return roleMapper.findbyname(name);
    }

    @Override
    public SieRole findbyid(Long id) {
        return roleMapper.findbyid(id);
    }

    @Override
    public int addrole(SieRole role) {
        if (role==null){
            role=new SieRole();
        }
        checkrole(role, false);
        cfrole(role, false);
        return roleMapper.addrole(role);
    }

    @Override
    public int updaterole(SieRole role) {

        checkrole(role, true);
        cfrole(role, true);
        return roleMapper.updaterole(role);
    }

    @Override
    @Transactional
    public int deleterolelist(List<SieRole> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        int findrolesbyid = roleMapper.findrolesbyid(list);
        if (findrolesbyid != 0) {
            throw new SieException("删除的角色中有正在被使用的");
        }
        list.stream().forEach((role)->{
            if(new Long(1L).equals(role.getId())){
                throw new SieException("管理员角色不可删除");
            };
        });
        roleMapper.deleterole(list);
        helpMapper.deleteRoleOfMenu(list);
        return helpMapper.deleteUserRole(list);
    }

    @Override
    public int stateonuserlist(List<SieRole> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        Date date = new Date(new Date().getTime()-3000);
        return roleMapper.stateon(list,date);
    }

    @Override
    public int stateoffuserlist(List<SieRole> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        list.stream().forEach((role)->{
            if(new Long(1L).equals(role.getId())){
                throw new SieException("管理员角色不可禁用");
            };
        });
        Date date = new Date(new Date().getTime()-3000);
        return roleMapper.stateoff(list,date);
    }

    @Override
    public PageResult<SieRole> findall(Map<String, String> map) {
        if (map==null){
            map=new HashMap<>();
        }
        String changl1 = "rolename";
        String changl2 = "state";

        String rolename = map.get(changl1) == null ? "" : map.get(changl1);
        String state = map.get(changl2) == null ? "" : map.get(changl2);
        String start = map.get("start");
        String end = map.get("end");
        String page = map.get("page") == null ? "1" : map.get("page") + "";
        String pagetotal = map.get("pagetotal") == null ? "20" : map.get("pagetotal") + "";
        rolename = "%" + rolename + "%";
        state = "%" + state + "%";
        Map<String, Object> findmap = new HashMap<>();
        findmap.put(changl1, rolename);
        HelpUtil.ssk(state, start, end, page, pagetotal, findmap);
        List<SieRole> roleList = roleMapper.rolelist(findmap);
        int sum = roleMapper.sum(findmap);


        return new PageResult<>(sum, roleList);
    }


    @Override
    public void cfrole(SieRole role, boolean flag) {
        SieRole findbyname;
        if (flag) {
            findbyname = roleMapper.findbyname2(role);
        } else {
            findbyname = roleMapper.findbyname(role.getRolename());
        }
        if (findbyname != null) {
            throw new SieException("角色名称重复");
        }
    }

    @Override
    public void checkrole(SieRole role, boolean flag) {
        if(role==null){
            throw new SieException("数据异常");
        }
        if(new Long(1L).equals(role.getId())){
            throw new SieException("管理员角色不可以修改");
        }
        String rolecodematch = "^[\\u4e00-\\u9fa5a-zA-Z]{6,20}$";
        String rolenamematch = "^[\\u4e00-\\u9fa5a-zA-Z]{2,50}$";
        if (role.getRolename() == null ||! role.getRolename().matches(rolenamematch)) {
            throw new SieException("角色名不符合规范");
        }
        if (role.getRolecode() == null || !role.getRolecode().matches(rolecodematch)) {
            throw new SieException("角色编码不符合规范");
        }
        if (role.getIs_delete() == null || role.getIs_delete().length() == 0) {
            role.setIs_delete("0");
        }
        if (role.getCreatdate() == null) {
            role.setCreatdate(new Date());
        }
        if (role.getEffectivedate()==null){
            role.setEffectivedate(new Date());
        }

        if (flag) {
            role.setUpdatetime(new Date());
        }

    }
}
