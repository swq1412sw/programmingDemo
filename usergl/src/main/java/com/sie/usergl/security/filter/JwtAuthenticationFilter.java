package com.sie.usergl.security.filter;




import com.sie.usergl.util.TokenUtil;
import com.sie.usergl.vo.SieUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 这个拦截器在接口访问进行拦截，再登入后，拦截请求，判断验证token令牌
 *
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private UserDetailsService userDetailsService;


   @Value("${jwt.tokenHeader}")
   private String tokenHeader;
   @Value("${jwt.tokenhead}")
   private String tokenhead;
    public JwtAuthenticationFilter(@Lazy UserDetailsService userDetailsService ) {
        this.userDetailsService=userDetailsService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //获取前端发送的请求，去接受token信息来验证
        String header = request.getHeader(tokenHeader);

        //判断token是不是存在
        if (header!=null&&header.startsWith(tokenhead)){
            //获取token
            String token = header.substring(tokenhead.length());

            //再去根据token获取用户名
            String username = TokenUtil.getUserNameByToken(token);
            if (!token.equals(TokenUtil.tokenMap.get(username))){
                filterChain.doFilter(request,response);
                return;
            }


            //token存在，但是没有登入信息
            if (username!=null&&null== SecurityContextHolder.getContext().getAuthentication()){
                UserDetails userDetails= userDetailsService.loadUserByUsername(username);
                if("1".equals(((SieUser)userDetails).getState())){
                    filterChain.doFilter(request,response);
                    return;
                }
                if (!TokenUtil.isExpiration(token)&&username.equals(userDetails.getUsername())){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
               usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
 filterChain.doFilter(request,response);
    }
}
