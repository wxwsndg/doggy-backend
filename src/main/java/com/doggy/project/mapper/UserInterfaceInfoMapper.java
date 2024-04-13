package com.doggy.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.doggy.doggycommon.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author lenovo
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Mapper
* @createDate 2023-12-09 18:14:34
* @Entity com.doggy.project.model.entity.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {
    //查询用户接口信息表中，按照指定的limit参数进行筛选。
    //返回前limit条记录的接口信息列表
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);

}




