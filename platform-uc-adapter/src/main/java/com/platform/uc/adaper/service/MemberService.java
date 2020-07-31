package com.platform.uc.adaper.service;

import com.platform.uc.adaper.vo.response.Member;
import com.platform.uc.api.RemoteUserService;
import com.platform.uc.api.vo.response.MemberResponse;
import com.platform.uc.api.vo.response.UserResponse;
import com.ztkj.framework.common.authorization.utils.SecurityUtils;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.core.ErrorCode;
import com.ztkj.framework.response.exception.BizException;
import com.ztkj.framework.response.utils.BizResponseUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户信息查询
 * @author hao.yan
 */
@Service
public class MemberService {

    @Resource
    private RemoteUserService remoteUserService;

    /**
     * 获取用户信息
     */
    public BizResponse<Member> selectMemberById(){
        String mid = SecurityUtils.user().getMid();
        BizResponse<UserResponse> response = remoteUserService.selectUserByMid(mid);
        // 转换成Member对象
        if (!response.getCode().equals(ErrorCode.SUCCESS_CODE)){
            throw new BizException(response.getCode(), response.getMessage());
        }
        return BizResponseUtils.success(toMember(response.getData()));
    }

    /**
     *
     */
    private Member toMember(UserResponse response){
        MemberResponse memberResponse = response.getMember();
        Member member = new Member();
        member.setId(memberResponse.getId());
        member.setNickname(memberResponse.getNickname());
        member.setBirthday(memberResponse.getBirthday());
        member.setSex(memberResponse.getSex());
        return member;
    }

}
