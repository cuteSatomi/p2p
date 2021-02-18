package com.zzx.p2p.base.service.impl;

import com.zzx.p2p.base.service.VerifyCodeService;
import com.zzx.p2p.base.util.BidConst;
import com.zzx.p2p.base.util.DateUtil;
import com.zzx.p2p.base.util.UserContext;
import com.zzx.p2p.base.vo.VerifyCodeVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

/**
 * @author zzx
 * @date 2021-02-08 16:33:16
 */
@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {

    @Value("${sms.username}")
    private String username;

    @Value("${sms.password}")
    private String password;

    @Value("${sms.apiKey}")
    private String apiKey;

    @Value("${sms.url}")
    private String url;

    @Override
    public void sendVerifyCode(String phoneNumber) {
        VerifyCodeVO verifyCodeVO = UserContext.getCurrentVerifyCode();

        if (verifyCodeVO == null || DateUtil.secondsBetween(new Date(), verifyCodeVO.getLastSendTime()) > 90) {
            // 当第一次发送或者是距离上一次发送已经超过90秒，才可以继续发送验证码
            // 生成验证码，为了方便直接使用UUID了
            String verifyCode = UUID.randomUUID().toString().substring(0, 4);
            // 发送短信
            System.out.println("给手机" + phoneNumber + "发送验证码：" + verifyCode);
            try {
                URL url = new URL(this.url);
                // 通过URL得到HttpURLConnection连接对象
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // 拼接请求的内容
                StringBuilder content = new StringBuilder(100)
                        .append("username=").append(username).append("&password=").append(password)
                        .append("&apiKey=").append(apiKey).append("&mobile=").append(phoneNumber)
                        .append("验证码是: ").append(verifyCode).append(", 请在5分钟内使用");
                // 发送POST请求
                connection.setRequestMethod("POST");
                // 设置这个POST请求是有请求体的
                connection.setDoOutput(true);
                // 写入POST请求体
                connection.getOutputStream().write(content.toString().getBytes());
                // 得到响应流，将一个输入流按照指定编码转换为String
                String response = StreamUtils.copyToString(connection.getInputStream(), Charset.forName("UTF-8"));
                if(response.startsWith("success")){
                    // 发送成功，把手机号码，验证码，以及发送时间装配到VO中并保存到session中
                    verifyCodeVO = new VerifyCodeVO();
                    verifyCodeVO.setPhoneNumber(phoneNumber);
                    verifyCodeVO.setVerifyCode(verifyCode);
                    verifyCodeVO.setLastSendTime(new Date());
                    UserContext.putCurrentVerifyCode(verifyCodeVO);
                }else {
                    // 发送失败，抛出异常
                    throw new RuntimeException();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("短信发送失败");
            }
        } else {
            throw new RuntimeException("发送过于频繁，请稍后再试");
        }
    }

    @Override
    public boolean verify(String phoneNumber, String verifyCode) {
        VerifyCodeVO vo = UserContext.getCurrentVerifyCode();
        if (vo != null && vo.getPhoneNumber().equals(phoneNumber)
                && vo.getVerifyCode().equalsIgnoreCase(verifyCode)
                && DateUtil.secondsBetween(new Date(), vo.getLastSendTime()) <= BidConst.VERIFY_CODE_VALIDATE_SECOND) {
            return true;
        }
        return false;
    }
}
