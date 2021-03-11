package com.leyou.item.api;

import com.leyou.item.pojo.Address;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("address")
public interface AddressApi {
    @GetMapping
    List<Address> getAddressByUserId(@RequestParam("userId") Long userId);

    @PostMapping
    ResponseEntity<Void> addAddress(Address address);
}
