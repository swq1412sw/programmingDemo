package com.sie.usergl;

import com.sie.usergl.util.*;
import com.sie.usergl.service.HelpService;
import com.sie.usergl.service.MenuService;
import com.sie.usergl.service.RoleService;
import com.sie.usergl.service.UserService;
import com.sie.usergl.vo.SieMenu;
import com.sie.usergl.vo.SieRole;
import com.sie.usergl.vo.SieUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest

class UserglApplicationTests {
@Autowired
    private UserService userService;
@Autowired
private HelpService helpService;
@Autowired
private RoleService roleService;
@Autowired
private PasswordEncoder passwordEncoder;
@Autowired
private MenuService menuService;
    @Test
    void testpassword() {
        System.out.println(passwordEncoder.encode("123456"));
        System.out.println(SecretUtil.encrypt("123456",SecretUtil.KEY,SecretUtil.IV));
    }
@Test
    void testuser_role(){
    List<SieMenu> admin = helpService.getusermenu("admin");
    System.out.println(admin);
}
@Test
void tsetcheckuser(){
    SieUser sieUser = new SieUser();
    sieUser.setUsername("手术大大苏打大苏打撒大苏打");
    SieUser sieUser2 = new SieUser();
    sieUser2.setUsername("大苏打大");
    SieUser sieUser3 = new SieUser();
    sieUser3.setUsername("手术sadsa");
    SieUser sieUser4 = new SieUser();
    sieUser4.setUsername("saadad");
    SieUser sieUser5 = new SieUser();
    sieUser5.setUsername("实打实2333");
    SieUser sieUser6 = new SieUser();
    sieUser6.setUsername("dsa");



}
@Test
void testdeleteuserlist(){
        List<Long>list = new ArrayList<Long>();
        for(int i=1;i<566;i++){
            list.add((long) (i));
        }

}

@Test
    void findalluser(){
    Map map = new HashMap<>();
    PageResult findalluser = userService.findalluser(map);
    System.out.println(findalluser);

}

@Test
    void addusers(){
String username ="角色编码";
String nickname ="普通角色";
String password =SecretUtil.encrypt("123456",SecretUtil.KEY,SecretUtil.IV);
List<SieRole>users=new ArrayList<SieRole>();
    for (int i = 0; i < 26; i++) {
        String username_=username+(char)(i+65);
        String nickname_=nickname+(char)(i+65);
        for (int j = 0; j < 26; j++) {
          String  username__=username_+(char)(j+65);
         String   nickname__=nickname_+(char)(j+65);
            for (int k = 0; k <26 ; k++) {
           String    username___=username__+(char)(k+65);
            String    nickname___=nickname__+(char)(k+65);
                SieRole sieUser = new SieRole();

                sieUser.setState("0");
                sieUser.setRolename(nickname___);
                sieUser.setRolecode(username___);
               roleService.addrole(sieUser);
            }
        }
    }


}
@Test
void menutest(){
//    PageResult<SieMenu> all = menuService.findAll(new HashMap<>());
//
//    Map<String,String> map =new HashMap<>();
//    map.put("name","查询用户");
//    map.put("state","0");
//    map.put("menutype","2");
//    PageResult<SieMenu> all1 = menuService.findAll(map);
//    System.out.println("----------------------------------------");
//
//    System.out.println(all);
//    System.out.println("----------------------------------------");
//    System.out.println(all1);
    SieUser sieUser = new SieUser();
    sieUser.setUsername("sssss");
    sieUser.setPassword("sssss");
    SieUser sieUser2 = new SieUser();
    sieUser2.setUsername("ss2");
    sieUser2.setPassword("ss2s");
    String s = TokenUtil.creatToken(sieUser);
    String s2 = TokenUtil.creatToken(sieUser2);
    String s1 = TokenUtil.refreshToken(s);
    System.out.println(s);
    System.out.println(s1);
    System.out.println(s2);

}


}
