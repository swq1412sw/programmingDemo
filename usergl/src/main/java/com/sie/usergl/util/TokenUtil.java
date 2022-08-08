package com.sie.usergl.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 帮助我们生成jwt
 */

public class TokenUtil {

    private  static String secret="sssss";

    private static int expiration=1000;

     public static Map<String,String>tokenMap=new HashMap<>();

     public static String creatToken(UserDetails userDetails){
         Map<String,Object>map =new HashMap<>();
         map.put("username",userDetails.getUsername());
         Date date = new Date();
         map.put("created",date);
         map.put("password",userDetails.getPassword());
         String compact = Jwts.builder().setClaims(map).
                 signWith(SignatureAlgorithm.HS256,TokenUtil.secret).
                 setExpiration(new Date(date.getTime() + TokenUtil.expiration* 3600L)).compact();

         TokenUtil.tokenMap.put(userDetails.getUsername(),compact);
         return compact;
     }

    public static Claims getTokenBody(String token){
        try {
            return Jwts.parser().
                    setSigningKey(secret).
                    parseClaimsJws(token).
                    getBody();
        }catch (Exception e){
            return new DefaultClaims();
        }

    }

    public static String getUserNameByToken(String token){
        try {
            return (String)getTokenBody(token).get("username");
        } catch (Exception e) {
            return "";
        }
    }
    public static String getPasswordByToken(String token){
        try {
            return (String)getTokenBody(token).get("password");
        } catch (Exception e) {
            return "";
        }
    }
public static String refreshToken(String token){
    Claims claims = getTokenBody(token).setExpiration(new Date(new Date().getTime() + TokenUtil.expiration * 3600L));
    String compact = Jwts.builder().setClaims(claims).
            signWith(SignatureAlgorithm.HS256, TokenUtil.secret).compact();
    String userNameByToken = getUserNameByToken(compact);
    String s = TokenUtil.tokenMap.get(userNameByToken);
    if(s!=null&&getTokenBody(compact).getExpiration().getTime()-getTokenBody(s).getExpiration().getTime()>15000){
        TokenUtil.tokenMap.put(userNameByToken,compact);
        return compact;
    }
    return s;
}
     public static Boolean isExpiration(String token){

         return getTokenBody(token).getExpiration().before(new Date());
     }




}
