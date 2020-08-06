package com.platform.uc.adapter.controller;

import com.platform.uc.adapter.vo.MemberResponse;
import com.ztkj.framework.common.authorization.vo.OAuthUser;
import com.ztkj.framework.response.core.BizResponse;
import com.ztkj.framework.response.utils.BizResponseUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 用户信息管理
 * @author hao.yan
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @GetMapping("/info")
    public BizResponse<MemberResponse> userInfo(){
        OAuthUser user = (OAuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MemberResponse member = new MemberResponse();
        member.setAvatar("https://dss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3854906927,947371630&fm=26&gp=0.jpg");
        member.setId(user.getMid());
        member.setNickname("YH");
        member.setBirthday(new Date());
        member.setSex(0);
        return BizResponseUtils.success(member);
    }

}
