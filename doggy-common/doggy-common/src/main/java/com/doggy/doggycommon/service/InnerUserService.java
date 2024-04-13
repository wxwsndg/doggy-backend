package com.doggy.doggycommon.service;

import com.doggy.doggycommon.model.entity.User;

/**
 * 用户服务
 *
 * @author sun
 */
public interface InnerUserService {

    /**
     * 数据库中是否已分配给用户密钥（accessKey）
     * @param accessKey
     * @return
     */
    User getInvokeUser(String accessKey);

}
