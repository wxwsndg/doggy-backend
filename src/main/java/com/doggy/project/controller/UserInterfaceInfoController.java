package com.doggy.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doggy.doggycommon.model.entity.User;
import com.doggy.doggycommon.model.entity.UserInterfaceInfo;
import com.doggy.project.annotation.AuthCheck;
import com.doggy.project.common.BaseResponse;
import com.doggy.project.common.DeleteRequest;
import com.doggy.project.common.ErrorCode;
import com.doggy.project.common.ResultUtils;
import com.doggy.project.constant.CommonConstant;
import com.doggy.project.constant.UserConstant;
import com.doggy.project.exception.BusinessException;
import com.doggy.project.model.dto.userinterfaceinfo.UserInterfaceInfoAddRequest;
import com.doggy.project.model.dto.userinterfaceinfo.UserInterfaceInfoQueryRequest;
import com.doggy.project.model.dto.userinterfaceinfo.UserInterfaceInfoUpdateRequest;
import com.doggy.project.service.UserService;
import com.doggy.project.service.UserInterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 接口管理
 */
@RestController
@RequestMapping("/userinterfaceInfo")
@Slf4j
public class UserInterfaceInfoController {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Resource
    private UserService userService;


    /**
     * 创建
     *
     * @param userinterfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addInterfaceInfo(@RequestBody UserInterfaceInfoAddRequest userinterfaceInfoAddRequest, HttpServletRequest request) {
        if (userinterfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userinterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(userinterfaceInfoAddRequest, userinterfaceInfo);
        // 校验
        UserInterfaceInfoService.validUserInterfaceInfo(userinterfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        userinterfaceInfo.setUserId(loginUser.getId());
        boolean result = userInterfaceInfoService.save(userinterfaceInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newInterfaceInfoId = userinterfaceInfo.getId();
        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        UserInterfaceInfo oldInterfaceInfo = userInterfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = userInterfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param userinterfaceInfoUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody UserInterfaceInfoUpdateRequest userinterfaceInfoUpdateRequest,
                                                     HttpServletRequest request) {
        if (userinterfaceInfoUpdateRequest == null || userinterfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userinterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(userinterfaceInfoUpdateRequest, userinterfaceInfo);
        // 参数校验
        UserInterfaceInfoService.validUserInterfaceInfo(userinterfaceInfo, false);
        User user = userService.getLoginUser(request);
        long id = userinterfaceInfoUpdateRequest.getId();
        // 判断是否存在
        UserInterfaceInfo oldInterfaceInfo = userInterfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = userInterfaceInfoService.updateById(userinterfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<UserInterfaceInfo> getInterfaceInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userinterfaceInfo = userInterfaceInfoService.getById(id);
        return ResultUtils.success(userinterfaceInfo);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param userinterfaceInfoQueryRequest
     * @return
     */
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @GetMapping("/list")
    public BaseResponse<List<UserInterfaceInfo>> listInterfaceInfo(UserInterfaceInfoQueryRequest userinterfaceInfoQueryRequest) {
        UserInterfaceInfo userinterfaceInfoQuery = new UserInterfaceInfo();
        if (userinterfaceInfoQueryRequest != null) {
            BeanUtils.copyProperties(userinterfaceInfoQueryRequest, userinterfaceInfoQuery);
        }
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>(userinterfaceInfoQuery);
        List<UserInterfaceInfo> userinterfaceInfoList = userInterfaceInfoService.list(queryWrapper);
        return ResultUtils.success(userinterfaceInfoList);
    }

    /**
     * 分页获取列表
     *
     * @param userinterfaceInfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<UserInterfaceInfo>> listInterfaceInfoByPage(UserInterfaceInfoQueryRequest userinterfaceInfoQueryRequest, HttpServletRequest request) {
        if (userinterfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userinterfaceInfoQuery = new UserInterfaceInfo();
        BeanUtils.copyProperties(userinterfaceInfoQueryRequest, userinterfaceInfoQuery);
        long current = userinterfaceInfoQueryRequest.getCurrent();
        long size = userinterfaceInfoQueryRequest.getPageSize();
        String sortField = userinterfaceInfoQueryRequest.getSortField();
        String sortOrder = userinterfaceInfoQueryRequest.getSortOrder();
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>(userinterfaceInfoQuery);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<UserInterfaceInfo> userInterfaceInfoPage = userInterfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(userInterfaceInfoPage);
    }

}
