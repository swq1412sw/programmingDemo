package com.sie.usergl.service.impl;


import com.sie.usergl.util.*;
import com.sie.usergl.exception.SieException;
import com.sie.usergl.mapper.HelpMapper;
import com.sie.usergl.mapper.UserMapper;
import com.sie.usergl.service.UserService;
import com.sie.usergl.vo.SieUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${jwt.tokenhead}")
    private String tokenhead;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserMapper userMapper;
@Resource
    private HelpMapper helpMapper;
    @Override
    public List<String> permission(String name) {
        return userMapper.permission(name);
    }

    @Override
    public List<String> permissionMenu(Map<String,String>map) {
        if (map==null){
            map=new HashMap<>();
        }
        String name = map.get("name")==null?"":map.get("name");
        String path = map.get("path")==null?"":map.get("path");
        return userMapper.permissionMenu(name,path);
    }

    @Override
    public SieUser founndByUsername(String name) {
        return userMapper.foundbyusername(name);
    }

    @Override
    public Result login(SieUser user) {
        if (user==null||user.getPassword()==null||user.getUsername()==null){
            throw new SieException("登录失败");
        }
        String name = user.getUsername();
        user.setPassword(SecretUtil.desEncrypt(user.getPassword(),SecretUtil.KEY,SecretUtil.IV));
        UserDetails userDetails = userDetailsService.loadUserByUsername(name);
        if (userDetails == null || !(passwordEncoder.matches(user.getPassword(), userDetails.getPassword()))) {
            return Result.fail("账号或者密码错误，请重试");
        }
        if ("1".equals(((SieUser) userDetails).getState())) {
            return Result.fail("当前用户已经被禁用，请联系管理员或使用其他账号登入");
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        String token = TokenUtil.creatToken(userDetails);
        Map<String, String> map = new HashMap<>();
        map.put("tokenHead", tokenhead);
        map.put("token", token);
        return Result.success("登入成功", map);
    }

    @Override
    public void checkuser(SieUser user, boolean flag) {
        if (user==null){
            throw new SieException("数据异常");
        }


        String usernamematch = "^[\\u4e00-\\u9fa5a-zA-Z]{6,20}$";

        if (user.getUsername() == null || !user.getUsername().matches(usernamematch)) {
            throw new SieException("用户名不符合规格");
        }
        String nicknamematch = "^[\\u4e00-\\u9fa5a-zA-Z]{2,50}$";
        if (user.getNickname() == null || !user.getNickname().matches(nicknamematch)) {
            throw new SieException("用户名称不符合规格");
        }
        String emailcheck = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        if (user.getEmail() != null && !"".equals(user.getEmail()) && (!user.getEmail().matches(emailcheck) || user.getEmail().length() > 200)) {
            throw new SieException("邮箱不符合规格");
        }
        if (flag) {
            user.setUpdatetime(new Date());
        }
        if (user.getPhone() != null && user.getPhone().length() > 50) {
            throw new SieException("电话号码长度异常");
        }
        String passwordmatch = "^[A-Za-z0-9]{6,20}$";
        if (user.getPassword()==null){
            throw new SieException("密码格式错误");
        }
        user.setPassword(SecretUtil.desEncrypt(user.getPassword(),SecretUtil.KEY,SecretUtil.IV));
        if (user.getPassword() == null || !user.getPassword().matches(passwordmatch)) {

            throw new SieException("密码格式错误");
        }
        if (user.getNote() != null && user.getNote().length() > 500) {
            throw new SieException("备注过长");
        }
        user.setPassword(MD5Util.encode(user.getPassword()));
        noempty(user);
        if(new Long(1L).equals(user.getId()) && "1".equals(user.getState())){
            throw new SieException("系统账号不可被禁用");
        }
        if("1".equals(user.getState())&&user.getUsername().equals(SecurityUtil.getUsername())){
            throw new SieException("用户不可禁用自己的账号");
        }
    }

    @Override
    public void noempty(SieUser user) {
        if (user.getEmail() == null) {
            user.setEmail("");
        }

        if (user.getPhone() == null) {
            user.setPhone("");
        }

        if (user.getNote() == null) {
            user.setNote("");
        }
        if (user.getGender() == null) {
            user.setGender("");
        }
        if (user.getIs_delete() == null || "".equals(user.getIs_delete())) {
            user.setIs_delete("0");
        }
        if (user.getCreatdate() == null) {
            user.setCreatdate(new Date());
        }

        if (user.getState() == null || "".equals(user.getState())) {
            user.setState("0");
        }


    }

    @Override
    public int adduser(SieUser user) {
        checkuser(user, false);
        SieUser foundbyusername = userMapper.foundbyusername(user.getUsername());
        cm(foundbyusername);
        return userMapper.adduser(user);
    }

    @Override
    public void cm(SieUser foundbyusername) {
        if (foundbyusername != null) {
            throw new SieException("用户名重复");
        }
    }

    @Override
    public int addallusers(List<SieUser> users) {
        Set<String> set = new HashSet<>();
        if (users == null) {
            throw new SieException("数据异常");
        }
        for (SieUser user : users) {

            checkuser(user, false);
            if(!set.add(user.getUsername())){
                throw new SieException("用户名重复");
            }
        }
        List<String>list =new ArrayList<>(set);
        if (!list.isEmpty()){
            List<String> findlistname = userMapper.findlistname(list);
            if (!findlistname.isEmpty()){
                throw new SieException("用户名重复");
            }
        }
        if (users.isEmpty()) {
            throw new SieException("数据异常");
        }
        return userMapper.adduserList(users);
    }

    @Override
    public int updateuser(SieUser user) {
        checkuser(user, true);

        if(("sieadmin".equals(user.getUsername())||new Long(1L).equals(user.getId()))&&!user.getUsername().equals(SecurityUtil.getUsername())){
            throw new SieException("系统账号只能有系统账号用户修改");
        }
        int updateuser = userMapper.updateuser(user);
        if (TokenUtil.tokenMap.containsKey(user.getUsername())){
         TokenUtil.tokenMap.put(user.getUsername(),TokenUtil.creatToken(user));
        }
        return updateuser;
    }

    @Override
    @Transactional
    public int deleteuserlist(List<SieUser> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        list.stream().forEach((user)->{
            if(new Long(1L).equals(user.getId())){
                throw new SieException("系统账号不可删除");
            };
            if(user.getUsername().equals(SecurityUtil.getUsername())){
                throw new SieException("用户不可删除自己");
            };
        });
        userMapper.deleteuser(list);
        return helpMapper.deleteUserOfRole(list);
    }

    @Override
    public int stateonuserlist(List<SieUser> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        Date date = new Date();
        return userMapper.stateon(list,date);
    }

    @Override
    public int stateoffuserlist(List<SieUser> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        list.stream().forEach((user)->{
            if(new Long(1L).equals(user.getId())){
                throw new SieException("系统账号不可禁用");
            };
            if(user.getUsername().equals(SecurityUtil.getUsername())){
                throw new SieException("用户不可禁用自己");
            };
        });
        Date date = new Date();
        return userMapper.stateoff(list,date);
    }

    @Override
    public PageResult<SieUser> findalluser(Map<String, String> map) {
        String cl1 = "username";
        String cl2 = "nickname";

        String cl3 = "gender";
        if (map==null){
            map=new HashMap<>();
        }
        String username = map.get(cl1) == null ? "" : map.get(cl1);
        String nickname = map.get(cl2) == null ? "" : map.get(cl2);
        String state = map.get("state") == null ? "" : map.get("state");
        String gender = map.get(cl3) == null ? "" : map.get(cl3);
        String end = map.get("end");
        String start = map.get("start");
        username = "%" + username + "%";
        nickname = "%" + nickname + "%";
        gender = "%" + gender + "%";
        state = "%" + state + "%";
        String page = map.get("page") == null ? "1" : map.get("page") + "";
        String pagetotal = map.get("pagetotal") == null ? "20" : map.get("pagetotal") + "";

        Map<String, Object> findmap = new HashMap<>();

        findmap.put("nickname", nickname);
        findmap.put("username", username);
        findmap.put("gender", gender);
        HelpUtil.ssk(state, start, end, page, pagetotal, findmap);
        List<SieUser> userlist = userMapper.userlist(findmap);
        int sum = userMapper.sum(findmap);

        return new PageResult<>(sum, userlist);
    }

}
