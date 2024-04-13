package com.doggy.project.service;

import com.doggy.doggycommon.model.entity.UserInterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author lenovo
 * @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
 * @createDate 2023-12-09 16:57:18
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    static void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {

    }

    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId,long userId);
}
