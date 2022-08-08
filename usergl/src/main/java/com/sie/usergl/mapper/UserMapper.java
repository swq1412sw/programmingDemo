package com.sie.usergl.mapper;


import com.sie.usergl.vo.SieUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserMapper {
    @Select("select * from sie_user where BINARY username=#{username} and is_delete='0'")
    SieUser foundbyusername(String username);

    @Insert("insert into sie_user(id,username,`password`,nickname,creatdate,is_delete,state,gender,note,email,phone,updatetime) VALUES((select sl from (select  IFNULL(max(id)+1,0) as sl from sie_user) sie_user1),#{username},#{password},#{nickname},#{creatdate},#{is_delete},#{state},#{gender},#{note},#{email},#{phone},#{updatetime})")
    int adduser(SieUser user);

    @Update("update sie_user set `password`=#{password} ,nickname=#{nickname} ,state=#{state} ,gender=#{gender} ,note=#{note} ,email=#{email} ,phone=#{phone} ,updatetime=#{updatetime} where username=#{username}")
    int updateuser(SieUser user);


    int deleteuser(@Param("list") List<SieUser> list);

    int stateoff(@Param("list") List<SieUser> list, @Param("date")Date date);

    int stateon(@Param("list") List<SieUser> list,@Param("date")Date date);

    int adduserList(@Param("list") List<SieUser> list);

    List<SieUser> userlist(@Param("map") Map<String, Object> map);

    List<String>findlistname(@Param("list")List<String>list);

    int sum(@Param("map") Map<String, Object> map);
@Select("select logo from (SELECT m.logo,u.username,m.pid,m.Orderno,m.id FROM sie_user_role ur  JOIN sie_user u ON ur.user_id = u.id AND u.is_delete = '0' AND u.state = '0'  JOIN sie_role r ON ur.role_id = r.id AND r.is_delete = '0' AND IF(r.effectivedate>NOW() or r.expirydate<NOW() ,'1','0')='0'  JOIN sie_role_menu rm ON ur.role_id = rm.role_id  JOIN sie_menu m ON m.id = rm.menu_id AND m.is_delete = '0' AND m.state = '0' WHERE  m.menutype = '2' UNION SELECT m.path,u.username,m.pid,m.Orderno,m.id FROM sie_user_role ur  JOIN sie_user u ON ur.user_id = u.id AND u.is_delete = '0' AND u.state = '0'  JOIN sie_role r ON ur.role_id = r.id AND r.is_delete = '0' AND IF(r.effectivedate>NOW() or r.expirydate<NOW() ,'1','0')='0' JOIN sie_role_menu rm ON ur.role_id = rm.role_id  JOIN sie_menu m ON m.id = rm.menu_id  AND m.is_delete = '0' AND m.state = '0' WHERE  m.menutype = '1') pre where BINARY username =#{name} ORDER BY pid,Orderno,id")
    List<String>permission(String name);
@Select("select DISTINCT me.logo from sie_menu me  join sie_menu m on m.id=me.pid and m.is_delete='0' and m.state='0' join sie_role_menu rm on rm.menu_id=me.id  join sie_role r on r.id=rm.role_id and IF(r.effectivedate>NOW() or r.expirydate<NOW() ,'1','0')='0' and r.is_delete='0'  join sie_user_role ur on ur.role_id=r.id  join sie_user u on u.id=ur.user_id and u.is_delete='0' and u.state='0' where BINARY u.username=#{name} and me.menutype='2' and m.path=#{path} and me.state='0' and me.is_delete='0'")
List<String>permissionMenu(@Param("name") String name,@Param("path") String path);
}


