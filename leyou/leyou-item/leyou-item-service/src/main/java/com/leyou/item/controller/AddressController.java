package com.leyou.item.controller;

import com.leyou.item.pojo.Address;
import com.leyou.item.service.AddressServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("address")
public class AddressController {
    @Autowired
    private AddressServer addressServer;

    /**
     * 获取用户的所有收货地址
     * @param userId
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Address>> getAddressByUserId(@RequestParam("userId") Long userId){
        List<Address> list = addressServer.getAddressByUserId(userId);
        if (CollectionUtils.isEmpty(list)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public  ResponseEntity<Void> addAddress(@RequestBody Address address){
//        System.out.println(address);
        this.addressServer.addAddress(address);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
