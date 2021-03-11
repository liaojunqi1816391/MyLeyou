package com.leyou.cart.controller;

import com.leyou.cart.service.CartService;
import com.leyou.item.pojo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    /**
     * 添加购物车
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody Cart cart) {
//        DatatypeConverter
        System.out.println("11 : ");
        this.cartService.addCart(cart);
        System.out.println("22");
        return ResponseEntity.ok().build();
    }

    /**
     * 查询购物车列表
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Cart>> queryCartList() {
        List<Cart> carts = this.cartService.queryCartList();
        if (carts == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(carts);
    }

    /**
     * 修改购物车
     * @param cart
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateNum(@RequestBody Cart cart){
        this.cartService.updateNum(cart);
        return ResponseEntity.ok().build();
    }

    /**
     * 移除购物车
     * @param skuId
     * @return
     */
    @DeleteMapping("/{skuId}")
    public ResponseEntity<Void> deleteCart(@PathVariable("skuId") String skuId){
        this.cartService.deleteCart(skuId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("all")
    public ResponseEntity<Void> addLocalCarts(@RequestBody Cart[] carts){
        for(Cart c : carts){
            System.out.println(c);
        }
        this.cartService.addLocalCarts(carts);
        return ResponseEntity.ok().build();
    }


}
