package com.platform.uc.service.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.uc.api.vo.request.UserRequest;
import com.platform.uc.api.vo.response.UserResponse;
import com.platform.uc.service.service.UserService;
import com.platform.uc.service.vo.PasswordVo;
import com.platform.uc.service.vo.User;
import com.ztkj.framework.common.domain.ResultBean;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 用户控制器
 * @author hao.yan
 */
@RequestMapping("/uc/user")
@RestController
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 登录信息查询
     */
    @PostMapping("/account")
    public BizResponse<UserResponse> selectUserByLogin(@RequestParam("accountName") String accountName){
        UserResponse user = userService.selectUserByLogin(accountName);
        return BizResponseUtils.success(user);
    }

    /**
     * 注册用户信息
     */
    @PostMapping("/register")
    public BizResponse<Void> register(@RequestBody UserRequest request){
        userService.register(request);
        return BizResponseUtils.success();
    }

    /**
     * 通过mid获取用户信息与账户信息
     */
    @GetMapping("/{mid}")
    public BizResponse<UserResponse> selectUserByMid(@PathVariable("mid") String mid){
        return BizResponseUtils.success(userService.selectByMid(mid));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "通过id查询信息")
    public BizResponse<UserResponse> findById(@PathVariable String id) {
        return BizResponseUtils.success(userService.selectById(id));
    }

    @PostMapping("/page")
    @ApiOperation(value = "分页查询")
    public BizResponse<IPage<User>> findByPage(@RequestBody Map<String, Object> map) {
        return BizResponseUtils.success(userService.findByPageDataScope(map));
    }

    @PostMapping("/page/all")
    @ApiOperation(value = "分页查询获取所有用户")
    public BizResponse<IPage<User>> findAllUserByPage(@RequestBody Map<String, Object> map) {
        return BizResponseUtils.success(userService.findByPageDataScope(map));
    }

    @GetMapping("/isExist")
    @ApiOperation(value = "检验是否存在")
    public BizResponse<Boolean> isExist(@RequestParam String paramMap) {
        if (userService.selectUserByLogin(paramMap) != null) {
            return BizResponseUtils.success(true);
        } else {
            return BizResponseUtils.success(false);
        }
    }

    @PutMapping
    @ApiOperation(value = "修改实体信息")
    public BizResponse<Boolean> modify(@RequestBody User user) {
        return BizResponseUtils.success(userService.udpate(user));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除实体信息")
    public BizResponse<Integer> removeById(@PathVariable String id) {
        return BizResponseUtils.success(userService.logicDeleteById(id));
    }

    @DeleteMapping("/batchRemove")
    @ApiOperation(value = "批量删除实体信息")
    public BizResponse<Boolean> batchRemove(@RequestBody List<String> ids) {
        return BizResponseUtils.success(userService.logicBatchDelete(ids));
    }

    /**
     * 重置密码
     */
    @PutMapping("/{id}/password")
    @ApiOperation("重置密码")
    public BizResponse resetPassword(@PathVariable("id") String id, @RequestBody Map<String, String> paramsMap) {
        userService.resetPassword(id, paramsMap);
        return BizResponseUtils.success("重置成功！");
    }

    /**
     * 修改密码
     *
     * @param passwordVo
     * @return
     */
    @PutMapping("/changePassword")
    @ApiOperation("修改密码")
    public BizResponse changePassword(@RequestBody PasswordVo passwordVo) {
        userService.changePassword(passwordVo);
        return BizResponseUtils.success("修改成功！");
    }

    /**
     * 启用用户
     *
     * @param ids
     * @return
     */
    @PutMapping("/enable")
    @ApiOperation("启用用户")
    public BizResponse enableUser(@RequestBody List<String> ids) {
        userService.enableUser(ids);
        return BizResponseUtils.success("启用成功！");
    }

    /**
     * 禁用用户
     *
     * @param ids
     * @return
     */
    @PutMapping("/disable")
    @ApiOperation("禁用用户")
    public BizResponse disableUser(@RequestBody List<String> ids) {
        userService.disableUser(ids);
        return BizResponseUtils.success("禁用成功！");
    }

    /**
     * 设置当前用户图像
     *
     * @param userImage
     * @return
     */
    @PutMapping("/setUserImage")
    public ResultBean<String> setUserImage(String id, String userImage) {
        userService.setUserImage(id, userImage);
        return ResultBean.success("设置成功！");
    }

    /**
     * 设置角色
     *
     * @param id 用户id
     * @param ids 角色ID
     * @return
     */
    @PutMapping("/configureRoles/{id}")
    public BizResponse configureRoles(@PathVariable String id , @RequestBody List<String> ids)
    {
        if(ids==null || ids.size()<=0)
        {
            return BizResponseUtils.error("999999", "角色配置不能为空");
        }
        userService.configureRoles(id, ids);
        return  BizResponseUtils.success("操作成功");
    }



}
