package com.leyou.item.service.impl;

import com.leyou.item.dao.AddressMapper;
import com.leyou.item.pojo.Address;
import com.leyou.item.service.AddressServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServerImpl implements AddressServer {
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Address> getAddressByUserId(Long userId) {
        Address address = new Address();
        address.setUserId(userId);
        return addressMapper.select(address);
    }

    @Override
    public void addAddress(Address address) {
        addressMapper.insert(address);
    }
}
