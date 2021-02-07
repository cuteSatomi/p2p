package com.zzx.p2p.mgrsite.base.listener;

import com.zzx.p2p.base.service.LoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author zzx
 * @date 2021-02-07 16:42:14
 */
@Component
public class InitAdminListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private LoginInfoService loginInfoService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loginInfoService.initAdmin();
    }
}
