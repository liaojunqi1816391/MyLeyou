package com.leyou.address.service;

import com.leyou.address.client.AddressClient;
import com.leyou.address.interceptor.LoginInterceptor;
import com.leyou.auth.entity.UserInfo;
import com.leyou.item.pojo.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    @Autowired
    private AddressClient addressClient;

    public List<Address> getAddress() {
        // 首先获取用户消息
        UserInfo user = LoginInterceptor.getLoginUser();
        return addressClient.getAddressByUserId(user.getId());
    }

    public void addAddress(Address address) {
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        address.setUserId(userInfo.getId());
//        System.out.println(address);
        addressClient.addAddress(address);
    }
}
