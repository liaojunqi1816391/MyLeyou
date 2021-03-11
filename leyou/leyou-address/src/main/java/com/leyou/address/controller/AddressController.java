package com.leyou.address.controller;

import com.leyou.address.service.AddressService;
import com.leyou.item.pojo.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseEntity<List<Address>> getAddress(){
        List<Address> list = this.addressService.getAddress();
        if (CollectionUtils.isEmpty(list)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<Void> addAddress(@RequestBody Address address){
//        System.out.println(address);
        this.addressService.addAddress(address);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
