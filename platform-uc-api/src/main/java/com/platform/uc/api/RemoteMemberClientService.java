package com.platform.uc.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.uc.api.vo.request.MemberClientRequest;
import com.platform.uc.api.vo.response.MemberClientResponse;
import com.platform.uc.api.vo.response.UserResponse;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 远程用户应用关系信息接口
 * @author hao.yan
 */
@FeignClient(value = "platform-uc-service", path = "/uc/member/client")
public interface RemoteMemberClientService {

    @GetMapping("/selectUserList/{clientId}/{pageNum}/{pageSize}")
    BizResponse<IPage<UserResponse>> selectUserPage(@PathVariable String clientId, @PathVariable Integer pageNum,
                                                           @PathVariable Integer pageSize);
    @PostMapping
    @ApiOperation(value = "保存实体信息")
    BizResponse<Integer> saveMemberClient(@RequestBody MemberClientRequest memberClient) ;

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除实体信息")
    BizResponse<Integer> removeById(@PathVariable String id);

    @DeleteMapping("/batchRemove")
    @ApiOperation(value = "批量删除实体信息")
    BizResponse<Integer> batchRemove(@RequestBody List<String> idList) ;
}
