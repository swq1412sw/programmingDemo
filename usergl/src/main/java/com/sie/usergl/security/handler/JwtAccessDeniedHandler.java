package com.sie.usergl.security.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sie.usergl.util.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {

            response.setStatus(403);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            writer.write(new ObjectMapper().writeValueAsString(Result.fail("没有访问权限")));
            writer.flush();
            writer.close();
        }
}
