package com.sie.usergl.mapper;

import com.sie.usergl.vo.SieMenu;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MenuMapper {
    @Select("select me.*,m.name as pname from sie_menu me left join sie_menu m on me.pid=m.id and m.is_delete='0'where me.is_delete='0' and me.id=#{id} ")
    SieMenu findById(long id);

    @Select("select me.*,m.name as pname from sie_menu me left join sie_menu m on me.pid=m.id and m.is_delete='0'where me.is_delete='0' and me.menutype='1' and BINARY me.path=#{path} ")
    SieMenu findByPath(String path);

    @Select("select me.*,m.name as pname from sie_menu me left join sie_menu m on me.pid=m.id and m.is_delete='0'where me.is_delete='0' and me.menutype='1' and BINARY me.path=#{path} and me.id != #{id} ")
    SieMenu findByPath2(SieMenu menu);

    @Select("select me.*,m.name as pname from sie_menu me left join sie_menu m on me.pid=m.id and m.is_delete='0'where me.is_delete='0' and me.menutype='2' and BINARY me.logo=#{logo} ")
    SieMenu findByLogo(String logo);

    @Select("select me.*,m.name as pname from sie_menu me left join sie_menu m on me.pid=m.id and m.is_delete='0'where me.is_delete='0' and me.menutype='2' and BINARY me.logo=#{logo} and me.id != #{id} ")
    SieMenu findByLogo2(SieMenu menu);

    @Select("select me.*,m.name as pname from sie_menu me left join sie_menu m on me.pid=m.id and m.is_delete='0'\n" +
            "where me.is_delete='0' and BINARY me.name like #{map.name} and me.state like #{map.state} and BINARY me.menutype like #{map.type}  order by me.menutype desc,me.pid desc ,me.Orderno  ")
    List<SieMenu> findAll(@Param("map") Map<String, Object> map);
@Select("select me.*,m.name as pname from sie_menu me left join sie_menu m on me.pid=m.id and m.is_delete='0' where me.is_delete='0' order by me.pid,me.Orderno ")
    List<SieMenu> allFind();
    @Select("select count(me.id)+1 as pname from sie_menu me left join sie_menu m on me.pid=m.id and m.is_delete='0'\n" +
            "where me.is_delete='0' and BINARY me.name like #{map.name} and me.state like #{map.state} and BINARY me.menutype like #{map.type}  order by me.pid,me.Orderno ")
    int sum(@Param("map") Map<String, Object> map);

    @Select("select ifnull(max(Orderno)+1,0) from sie_menu where pid =#{id}  ")
    int orderNo(long id);

    @Insert("insert into sie_menu(id,`name`,`path`,logo,pid,menutype,Orderno,level,state,creatdate,updatetime,is_delete,`describe`,ico) VALUES((select sl from (select  IFNULL(max(id)+1,0) as sl from sie_menu) sie_menu1),#{name},#{path},#{logo},#{pid},#{menutype},#{Orderno},#{level},#{state},#{creatdate},#{updatetime},#{is_delete},#{describe},#{ico})")
    int addMenu(SieMenu menu);

    @Update("update sie_menu set pid=#{pid}, `name`=#{name}, Orderno=#{Orderno}, level=#{level}, state=#{state}, path=#{path}, logo=#{logo}, updatetime=#{updatetime}, `describe`=#{describe}, ico=#{ico} where id=#{id}")
    int updateMenu(SieMenu menu);

    int deleteMenu(@Param("list") List<SieMenu> list);

    int stateOn(@Param("list") List<SieMenu> list, @Param("date")Date date);

    int stateOff(@Param("list") List<SieMenu> list,@Param("date")Date date);

    int findIsDelete(@Param("list") List<SieMenu> list);
}
