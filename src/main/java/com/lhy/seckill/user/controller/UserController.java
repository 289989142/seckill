package com.lhy.seckill.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhy.seckill.user.controller.param.UserQueryParam;
import com.lhy.seckill.user.entity.SkUserEntity;
import com.lhy.seckill.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description
 * @Author lihengyu
 * @Date 2021/5/6 17:41
 */
@Controller
public class UserController {
    //        //获取用户信息
//        UserDetailImpl principal = (UserDetailImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    //TODO 可能要在前台加一个个人中心

    @Autowired
    private UserService userService;

    private static final String REDIRECT_TO_ADMIN_USER = "redirect:/admin/user/list";

    @GetMapping("admin/user/list")
    public String userList(Model model,
                           @RequestParam(required = false,defaultValue = "0",value = "pageNum") long pageNum,
                           @RequestParam(required = false,defaultValue = "10",value = "pageSize") long pageSize) {
        UserQueryParam param = new UserQueryParam();
        param.setPageNum(pageNum);
        param.setPageSize(pageSize);
        IPage<SkUserEntity> userPage = userService.getPageByParam(param);
        model.addAttribute("pageInfo",userPage);
        return "admin/users";
    }
    @RequestMapping("admin/user/{id}/delete")
    public String deleteUser(@PathVariable("id") Integer id){
        userService.delete(id);
        return REDIRECT_TO_ADMIN_USER;
    }
}
