package com.sie.usergl.mapper;

import com.sie.usergl.vo.SieRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface RoleMapper {
    @Select("select * from sie_role where id=#{id} and is_delete='0'")
    SieRole findbyid(Long id);

    @Select("select * from sie_role where BINARY rolename=#{name} and is_delete='0'")
    SieRole findbyname(String name);

    @Select("select * from sie_role where BINARY rolename=#{rolename} and is_delete='0' and id!=#{id} ")
    SieRole findbyname2(SieRole role);

    @Insert("insert into sie_role(id,rolecode,`rolename`,effectivedate,expirydate,creatdate,updatetime,is_delete,note) VALUES((select sl from (select  IFNULL(max(id)+1,0) as sl from sie_role) sie_role1),#{rolecode},#{rolename},#{effectivedate},#{expirydate},#{creatdate},#{updatetime},#{is_delete},#{note})")
    int addrole(SieRole role);

    @Update("update sie_role set `rolecode`=#{rolecode} ,rolename=#{rolename}  ,effectivedate=#{effectivedate} ,note=#{note}  ,expirydate=#{expirydate} ,updatetime=#{updatetime} where id=#{id}")
    int updaterole(SieRole role);

    void deleterole(@Param("list") List<SieRole> list);


    List<SieRole> rolelist(@Param("map") Map<String,Object> map);

     int stateoff(@Param("list") List<SieRole> list,@Param("date")Date date);

     int stateon(@Param("list") List<SieRole> list, @Param("date")Date date);

     int sum(@Param("map") Map<String,Object> map);

     int findrolesbyid(@Param("list") List<SieRole> list);
}
