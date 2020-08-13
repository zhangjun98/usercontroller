package com.platform.uc.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.uc.api.vo.request.PasswordVo;
import com.platform.uc.api.vo.request.UserRequest;
import com.platform.uc.api.vo.response.UserResponse;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 远程用户信息接口
 * @author hao.yan
 */
@FeignClient(value = "platform-uc-service", path = "/uc/user")
public interface RemoteUserService {

    /**
     * 通过用户名查询用户信息
     */
    @PostMapping("/account")
    BizResponse<UserResponse> selectUserByLogin(@RequestParam("accountName") String accountName);
    /**
     * 注册用户信息
     */
    @PostMapping("/register")
    BizResponse<Void> register(@RequestBody UserRequest request);
    /**
     * 通过用户信息编号查询用户信息
     */
    @GetMapping("/{mid}")
    BizResponse<UserResponse> selectUserByMid(@PathVariable("mid") String mid);

    @GetMapping("/{id}")
    @ApiOperation(value = "通过id查询信息") BizResponse<UserResponse> findById(@PathVariable String id) ;

    @PostMapping("/page")
    @ApiOperation(value = "分页查询") BizResponse<IPage<UserResponse>> findByPage(@RequestBody Map<String, Object> map);

    @PostMapping("/page/all")
    @ApiOperation(value = "分页查询获取所有用户") BizResponse<IPage<UserResponse>> findAllUserByPage(@RequestBody Map<String, Object> map);

    @GetMapping("/isExist")
    @ApiOperation(value = "检验是否存在") BizResponse<Boolean> isExist(@RequestParam String paramMap) ;

    @PutMapping
    @ApiOperation(value = "修改实体信息") BizResponse<Boolean> modify(@RequestBody UserRequest user) ;

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除实体信息") BizResponse<Integer> removeById(@PathVariable String id);

    @DeleteMapping("/batchRemove")
    @ApiOperation(value = "批量删除实体信息") BizResponse<Boolean> batchRemove(@RequestBody List<String> ids);

    @PutMapping("/{id}/password")
    @ApiOperation("重置密码") BizResponse resetPassword(@PathVariable("id") String id, @RequestBody Map<String, String> paramsMap) ;

    @PutMapping("/changePassword")
    @ApiOperation("修改密码") BizResponse changePassword(@RequestBody PasswordVo passwordVo);

    @PutMapping("/enable")
    @ApiOperation("启用用户") BizResponse enableUser(@RequestBody List<String> ids);

    @PutMapping("/disable")
    @ApiOperation("禁用用户") BizResponse disableUser(@RequestBody List<String> ids);

    @PutMapping("/updateUser")
    @ApiOperation("更新用户信息") BizResponse<Boolean> updateUser(UserRequest user);

    @PutMapping("/setUserImage/{id}") BizResponse<String> setUserImage(@PathVariable String id, @RequestBody String userImage);

    @PutMapping("/configureRoles/{id}") BizResponse configureRoles(@PathVariable String id , @RequestBody List<String> ids);

    @PutMapping("/configureClients/{id}") BizResponse configureClients(@PathVariable String id , @RequestBody List<String> ids);

}
