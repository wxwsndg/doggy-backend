package com.doggy.project.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.doggy.doggycommon.model.entity.User;
import com.doggy.doggycommon.service.InnerUserService;
import com.doggy.project.common.ErrorCode;
import com.doggy.project.exception.BusinessException;
import com.doggy.project.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerUserServiceImpl implements InnerUserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 实现接口中的 getInvokeUser 方法，用于根据密钥获取内部用户信息
     *
     * @param accessKey 密钥
     * @return 内部用户信息，如果找不到匹配的用户则返回 null
     * @throws BusinessException 参数错误时抛出业务异常
     */

    @Override
    public User getInvokeUser(String accessKey) {
        //参数校验
        if(StringUtils.isAnyBlank(accessKey)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //创建查询条件包装器
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accesskey",accessKey);

        //使用UserMapper 的 selectOne 方法查询用户信息
        return userMapper.selectOne(queryWrapper);
    }
}
