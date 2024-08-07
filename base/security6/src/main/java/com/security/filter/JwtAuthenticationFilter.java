package com.security.filter;

import com.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * 总体流程：
     * 如果我们有我们的用户名并且用户未通过身份验证，我们会从数据库中获取用户详细信息（loadUserByUsername --> UserDetails）
     * 然后我们需要做的是检查用户是否有效，如果用户和令牌有效，我们创建一个UsernamePasswordAuthenticationToken对象，传递UserDetails & 凭证 & 权限信息
     * 扩展上面生成的authToken，包含我们请求的详细信息，然后更新安全上下文中的身份验证令牌
     * 最后一步执行过滤器chain，别忘记放行
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        // token的信息为空，或者开头不对
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        // 从token中解析userName
        username = jwtService.extractUsername(jwt);
        if (StringUtils.isNotBlank(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 根据解析出来的username，获取数据库中的用户信息，封装userDetails对象
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // 判断token是否过期
            Boolean isTokenValid = true;
            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                // 如果令牌有效，那么封装一个对象
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // 更新安全上下文的持有用户
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        }
    }
}
