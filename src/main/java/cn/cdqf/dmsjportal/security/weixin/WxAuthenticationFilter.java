package cn.cdqf.dmsjportal.security.weixin;

import cn.cdqf.dmsjportal.security.mobile.SmsAuthenticationToken;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WxAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String WEIXIN_OPENID = "openid";
    public static final String USERID= "userid";
    private String openidParameter = "openid";
    private String useridParameter = "userid";
    private boolean postOnly = true;

    public WxAuthenticationFilter() {
        super(new AntPathRequestMatcher("/weixin/**", "GET"));
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("GET")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String openid = this.obtainOpenId(request);
            String userid = this.obtainUserId(request);
            if (openid == null) {
                openid = "";
            }

            if (userid == null) {
                userid = "";
            }

            openid = openid.trim();
            userid = userid.trim();
            WxAuthenticationToken authRequest = new WxAuthenticationToken(openid, userid);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    @Nullable
    protected String obtainUserId(HttpServletRequest request) {
        return request.getParameter(this.useridParameter);
    }

    @Nullable
    protected String obtainOpenId(HttpServletRequest request) {
        return request.getParameter(this.openidParameter);
    }

    protected void setDetails(HttpServletRequest request, WxAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setOpenidParameter(String openidParameter) {
        Assert.hasText(openidParameter, "Username parameter must not be empty or null");
        this.openidParameter = openidParameter;
    }

    public void setUserIdParameter(String useridParameter) {
        Assert.hasText(useridParameter, "Password parameter must not be empty or null");
        this.useridParameter = useridParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getOpenIdParameter() {
        return this.openidParameter;
    }

    public final String getUserIdParameter() {
        return this.useridParameter;
    }
}
