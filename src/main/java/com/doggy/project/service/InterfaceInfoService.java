package com.doggy.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.doggy.doggycommon.model.entity.InterfaceInfo;

/**
* @author lenovo
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-11-26 15:05:06
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {
  void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
