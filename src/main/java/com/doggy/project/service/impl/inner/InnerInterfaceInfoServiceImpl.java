package com.doggy.project.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.doggy.doggycommon.model.entity.InterfaceInfo;
import com.doggy.doggycommon.service.InnerInterfaceInfoService;
import com.doggy.project.common.ErrorCode;
import com.doggy.project.exception.BusinessException;
import com.doggy.project.mapper.InterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {
    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    /**
     * 实现接口中的getInterfaceInfo方法，用于根据URL和请求方法获取内部接口信息
     *
     * @param url 请求URL
     * @param method 请求方法
     * @return 内部接口信息，如果找不到匹配的接口则返回null
     * @throws BusinessException 参数错误时抛出业务异常
     */

    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        //参数校验
        if (StringUtils.isAnyBlank(url,method)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //创建查询条件包装器
        QueryWrapper<InterfaceInfo>queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("url",url);
        queryWrapper.eq("method",method);
        //使用 InterfaceInfoMapper 的 selectOne 方法查询接口信息
        return interfaceInfoMapper.selectOne(queryWrapper);
    }
}
