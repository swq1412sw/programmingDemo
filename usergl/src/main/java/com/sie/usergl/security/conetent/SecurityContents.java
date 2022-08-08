package com.sie.usergl.security.conetent;

public class SecurityContents {
    public static final  String[] WHITE_LIST;
    static {
        WHITE_LIST = new String[]{


                "/log/login",


                //swagger相关的页面
                "/doc.html",
                "/configuration/ui",
                "/configuration/security",
                "/static/**"
                , "/swagger-ui.html"
                , "/webjars/**",
                "index.html"
                , "/v2/**"
                , "/swagger-resources/**",
                "favicon.ico"
                , "/api/**"
                , "/socket/**"
                , "File/**"
                , "file:File/image/upload/**"

    };
    }
}
