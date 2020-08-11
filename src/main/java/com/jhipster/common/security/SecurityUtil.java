package com.jhipster.common.security;

import java.util.Map;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import com.jhipster.common.constant.TokenConst;

/**
 * 当前用户工具类<br/>
 * 通过解析token，获取其中的用户基本信息
 * 
 * @author yuanke
 * @date 2019/10/10 20:24
 */
public final class SecurityUtil {

    private static JsonParser jsonParser = JsonParserFactory.getJsonParser();

    private SecurityUtil() {
    }

    /**
     * 获取当前用户id
     * 
     * @return userId 正常情况下非null
     */
    public static Integer getCurrentUserId() {
        OAuth2AuthenticationDetails details = getAuthenticationDetails();
        if (details != null) {
            Jwt jwt = JwtHelper.decode(details.getTokenValue());
            Map<String, Object> claimsMap = jsonParser.parseMap(jwt.getClaims());
            return (Integer) claimsMap.get(TokenConst.USER_ID);
        }
        return null;
    }

    /**
     * 获取当前用户的租户id
     *
     * @return tenantId *注意纯粹的系统管理员的tenantId为null，但是它不参与业务流程
     */
    public static Integer getCurrentTenantId() {
        OAuth2AuthenticationDetails details = getAuthenticationDetails();
        if (details != null) {
            Jwt jwt = JwtHelper.decode(details.getTokenValue());
            Map<String, Object> claimsMap = jsonParser.parseMap(jwt.getClaims());
            return (Integer) claimsMap.get(TokenConst.TENANT_ID);
        }
        return null;
    }

    private static OAuth2AuthenticationDetails getAuthenticationDetails() {
        OAuth2AuthenticationDetails details = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null) {
            Authentication authentication = securityContext.getAuthentication();
            if (authentication != null) {
                details = (OAuth2AuthenticationDetails) authentication.getDetails();
            }
        }
        return details;
    }
}
