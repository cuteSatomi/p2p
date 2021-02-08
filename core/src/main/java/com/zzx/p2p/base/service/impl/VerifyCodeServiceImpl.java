package com.zzx.p2p.base.service.impl;

import com.zzx.p2p.base.service.VerifyCodeService;
import com.zzx.p2p.base.util.BidConst;
import com.zzx.p2p.base.util.DateUtil;
import com.zzx.p2p.base.util.UserContext;
import com.zzx.p2p.base.vo.VerifyCodeVO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @author zzx
 * @date 2021-02-08 16:33:16
 */
@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {
    @Override
    public void sendVerifyCode(String phoneNumber) {
        VerifyCodeVO verifyCodeVO = UserContext.getCurrentVerifyCode();

        if (verifyCodeVO == null || DateUtil.secondsBetween(new Date(), verifyCodeVO.getLastSendTime()) > 90) {
            // 当第一次发送或者是距离上一次发送已经超过90秒，才可以继续发送验证码
            // 生成验证码，为了方便直接使用UUID了
            String verifyCode = UUID.randomUUID().toString().substring(0, 4);
            // 发送短信
            System.out.println("给手机" + phoneNumber + "发送验证码：" + verifyCode);
            // 把手机号码，验证码，以及发送时间装配到VO中并保存到session中
            verifyCodeVO = new VerifyCodeVO();
            verifyCodeVO.setPhoneNumber(phoneNumber);
            verifyCodeVO.setVerifyCode(verifyCode);
            verifyCodeVO.setLastSendTime(new Date());
            UserContext.putCurrentVerifyCode(verifyCodeVO);
        } else {
            throw new RuntimeException("发送过于频繁，请稍后再试");
        }
    }

    @Override
    public boolean verify(String phoneNumber, String verifyCode) {
        VerifyCodeVO vo = UserContext.getCurrentVerifyCode();
        if (vo != null && vo.getPhoneNumber().equals(phoneNumber)
                && vo.getVerifyCode().equalsIgnoreCase(verifyCode)
                && DateUtil.secondsBetween(new Date(),vo.getLastSendTime()) <= BidConst.VERIFY_CODE_VALIDATE_SECOND) {
            return true;
        }
        return false;
    }
}
