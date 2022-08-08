package com.sie.usergl.mapper;

import com.sie.usergl.vo.SieMenu;
import com.sie.usergl.vo.SieRole;
import com.sie.usergl.vo.SieUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface HelpMapper {
@Select("SELECT DISTINCT m.* from sie_menu m  join sie_role_menu rm on rm.menu_id=m.id  join sie_role r on r.id=rm.role_id and IF(r.effectivedate>NOW() or r.expirydate<NOW() ,'1','0')='0' and r.is_delete='0'  join sie_user_role ur on ur.role_id=r.id  join sie_user u on u.id=ur.user_id and u.is_delete='0' and u.state='0' where m.menutype!='3' and m.is_delete='0' and m.state='0' and BINARY u.username=#{username} order by menutype,orderNo")
     List<SieMenu> userPrivilege(String username);

     int addRoleForUser(@Param("list") List<Map<String,Object>>list);

     List<Long> findRoleForUser(Long id);

     List<Map<String,Object>> findAllRole();
 int deleteRoleForUser(@Param("id")Long id, @Param("list")List<Long>list);

 @Select("select menu_id from sie_role_menu where role_id=#{id} and is_delete='0'  ")
 List<Long> roleOfMenu(Long id);
    @Select("select * from sie_menu where is_delete='0' order by pid,Orderno")
 List<SieMenu> allMenu();
 int deleteUserRole(@Param("list")List<SieRole>list);
 int deleteUserOfRole(@Param("list")List<SieUser>list);

 int deleteRoleMenu(@Param("list")List<SieMenu>list);
 int deleteRoleOfMenu(@Param("list")List<SieRole>list);

 int addMenuForRole(@Param("list") List<Map<String,Object>>list);
 int deleteMenuForRole(@Param("id")Long id, @Param("list")List<Long>list);
@Select("select m.`name` as pname,mr.*,if(mr.expirydate<NOW() or mr.effectivedate>NOW(),'1','0') as state from ( select m.menutype,m.Orderno,m.`name`,m.id ,if(max(IFNULL(mm.expirydate,'9999-12-31'))='9999-12-31',null,mm.expirydate) as expirydate,min(mm.effectivedate) as effectivedate,m.pid  from sie_menu m join ( select m.id, m.`name`,m.menutype,rr.effectivedate,rr.expirydate from sie_menu m  join sie_role_menu rm on rm.menu_id=m.id , (select r.id, r.rolename,r.effectivedate,r.expirydate from sie_role r join sie_user_role ur on r.id=ur.role_id left join sie_user u on u.id=ur.user_id where username=#{map.username} and r.is_delete='0' and u.is_delete='0') rr where rr.id=rm.role_id   ORDER BY m.id) mm on mm.id=m.id where m.is_delete='0'and m.state='0' GROUP BY m.`name`ORDER BY m.pid,m.Orderno,m.menutype ) mr  left join sie_menu m on mr.pid=m.id and m.is_delete='0' and m.state='0' where mr.menutype like #{map.menutype} and mr.`name` like #{map.name} and if(mr.expirydate<NOW() or mr.effectivedate>NOW(),'1','0') like #{map.state} order by mr.menutype desc,mr.pid desc,mr.Orderno ")
 List<SieMenu>findUserMenutoTime(@Param("map")Map map);

@Select("SELECT r.rolename ,r.effectivedate,r.expirydate,if(r.expirydate<NOW() or r.effectivedate>NOW(),'1','0') as state from sie_role r join sie_user_role ur on r.id=ur.role_id join sie_user u on u.id=ur.user_id where u.username=#{map.username} and r.is_delete='0'  and u.is_delete='0' and r.rolename like #{map.name} and if(r.expirydate<NOW() or r.effectivedate>NOW(),'1','0') like #{map.state}")
 List<SieRole>findUserRoletoTime(@Param("map")Map map);

}
