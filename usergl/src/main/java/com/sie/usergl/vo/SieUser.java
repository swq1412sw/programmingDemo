package com.sie.usergl.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SieUser implements Serializable, UserDetails {
    //主键
    private Long id;
    //
    private String username;
    private String password;
    private String nickname;
    private String gender;
    private String state;
    private String note;
    private String email;
    private String phone;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Shanghai")
    private Date creatdate;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Shanghai")
    private Date updatetime;
    private String is_delete;


    @JSONField(serialize = false)
    public List<String>authors;

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(new Long(1L).equals(this.id)){
            authors.add("admin");
        }
        List<SimpleGrantedAuthority> collect = authors.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        return collect;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return "0".equals(this.getState());
    }
}
