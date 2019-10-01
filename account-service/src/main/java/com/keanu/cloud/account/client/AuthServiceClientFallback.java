package com.keanu.cloud.account.client;

import com.keanu.cloud.account.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class AuthServiceClientFallback implements AuthServiceClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceClientFallback.class);

    @Override
    public void createUser(User user) {
        LOGGER.error("API 访问失败！也许是授权问题！");
    }

    @Override
    public Principal getUser(Principal principal) {
        principal = () -> "用户已退出，请重新登录。";
        return principal;
    }
}
