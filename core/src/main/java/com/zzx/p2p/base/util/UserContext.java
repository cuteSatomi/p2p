package com.zzx.p2p.base.util;

import com.zzx.p2p.base.domain.LoginInfo;
import com.zzx.p2p.base.vo.VerifyCodeVO;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * 用于存放当前用户的上下文
 *
 * @author zzx
 * @date 2021-02-04 16:00:04
 */
public class UserContext {
    public static final String USER_IN_SESSION = "loginInfo";
    public static final String VERIFY_CODE_IN_SESSION = "verify_code_in_session";

    public static final HttpSession getSession() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).
                getRequest().getSession();
    }

    public static void putCurrent(LoginInfo current) {
        getSession().setAttribute(USER_IN_SESSION, current);
    }

    public static LoginInfo getCurrent() {
        return (LoginInfo) getSession().getAttribute(USER_IN_SESSION);
    }

    /**
     * 将当前的短信验证码存入session中
     *
     * @return
     */
    public static void putCurrentVerifyCode(VerifyCodeVO verifyCodeVO) {
        getSession().setAttribute(VERIFY_CODE_IN_SESSION, verifyCodeVO);
    }

    /**
     * 得到当前的短信验证码
     *
     * @return
     */
    public static VerifyCodeVO getCurrentVerifyCode() {
        return (VerifyCodeVO) getSession().getAttribute(VERIFY_CODE_IN_SESSION);
    }
}
