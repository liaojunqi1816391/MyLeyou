package com.leyou.item.api;

import com.leyou.item.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@RequestMapping("/user")
public interface UserApi {
    @GetMapping("/query")
    User queryUser(@RequestParam("username") String username,
                   @RequestParam("password") String password);
}
