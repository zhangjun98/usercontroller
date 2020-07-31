package com.platform.uc.adaper.controller;

import com.platform.uc.adaper.service.MemberService;
import com.platform.uc.adaper.vo.response.Member;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 开放的用户接口
 * @author hao.yan
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Resource
    private MemberService memberService;

    /**
     * 获取用户信息
     */
    @GetMapping("/info")
    public BizResponse<Member> info(){
        return memberService.selectMemberById();
    }

}
