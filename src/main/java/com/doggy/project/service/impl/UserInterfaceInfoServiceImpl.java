package com.doggy.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doggy.project.common.ErrorCode;
import com.doggy.project.exception.BusinessException;
import com.doggy.project.mapper.UserInterfaceInfoMapper;
import com.doggy.project.service.UserInterfaceInfoService;
import com.doggy.doggycommon.model.entity.UserInterfaceInfo;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
* @createDate 2023-12-09 18:14:34
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        if (add) {
            if (userInterfaceInfo.getInterfaceInfoId() <=0 || userInterfaceInfo.getUserId()<=0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口或用户不存在");
            }
        }
        if (userInterfaceInfo.getLeftNum()< 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余次数不能小于0");
        }

    }

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        //判断
        if(interfaceInfoId<=0||userId<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //使用 Updatewrapper 对象来构建更新条件
        UpdateWrapper<UserInterfaceInfo>updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId",interfaceInfoId);
        updateWrapper.eq("userId",userId);
        //setSql 方法用于设置要更新的 SQL 语句。这里通过 SQL表达式实现了两个字段的更新操作:
        //leftNum=leftNum-1和totalNum=totalNum+1。意思是将leftNum字段减一，totalNum字段加一。
        updateWrapper.setSql("leftNum = leftNum - 1,totalNum =totalNum+1");
        //最后，调用update方法执行更新操作，并返回更新是否成功的结果
        return this.update(updateWrapper);
    }
}




