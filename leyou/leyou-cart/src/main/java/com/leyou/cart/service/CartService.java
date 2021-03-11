package com.leyou.cart.service;

import com.leyou.auth.entity.UserInfo;
import com.leyou.cart.client.GoodsClient;
import com.leyou.cart.interceptor.LoginInterceptor;
import com.leyou.common.utils.JsonUtils;
import com.leyou.item.pojo.Cart;
import com.leyou.item.pojo.Sku;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private GoodsClient goodsClient;

    static final String KEY_PREFIX = "leyou:cart:uid:";

    static final Logger logger = LoggerFactory.getLogger(CartService.class);

    public void addCart(Cart cart) {
        // 获取登录用户
        UserInfo loginUser = LoginInterceptor.getLoginUser();
        // 组成redis的 key
        String key = KEY_PREFIX + loginUser.getId();
        // 获取 hash 操作对象
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(key);

        // 查询是否存在
        String skuId = cart.getSkuId().toString();
        Integer num = cart.getNum();
        if (hashOps.hasKey(skuId)){
            // 如果存在则获取购物车数据
            String cartJSON = hashOps.get(skuId).toString();
            cart = JsonUtils.parse(cartJSON,Cart.class);
            // 修改购物车数量
            cart.setNum(cart.getNum()+num);
        }else {
            // 若不存在则新增购物车数据
            cart.setUserId(loginUser.getId());
            // 其他商品信息则需要查询商品服务
            Sku sku = this.goodsClient.querySkuById(cart.getSkuId());
            cart.setImage(StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(), ",")[0]);
            cart.setPrice(sku.getPrice());
            cart.setTitle(sku.getTitle());
            cart.setOwnSpec(sku.getOwnSpec());
        }
        // 购物车数据重新写入 redis
        hashOps.put(skuId,JsonUtils.serialize(cart));
    }

    public List<Cart> queryCartList() {
        // 首先获取用户 ID 组成 key
        String key = KEY_PREFIX + LoginInterceptor.getLoginUser().getId();
        // 判断是否有 key
        if(!redisTemplate.hasKey(key)){
            return null;
        }
        // 从 redis 中将用户的购物车数据拿出来
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(key);
        List<Object> list = hashOps.values();
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        // 查询购物车
        return list.stream().map(o -> JsonUtils.parse(o.toString(),Cart.class)).collect(Collectors.toList());
    }

    public void updateNum(Cart cart) {
        // 通过登录的用户获得 redis 的 key
        String key = KEY_PREFIX + LoginInterceptor.getLoginUser().getId();
        // 获取 redis 中的购物车并修改数量
        if (!redisTemplate.hasKey(key)){
            return;
        }
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(key);
        String cartJSON = hashOps.get(cart.getSkuId().toString()).toString();
        Cart newCart = JsonUtils.parse(cartJSON, Cart.class);
        newCart.setNum(cart.getNum());
        // 重新存入redis
        hashOps.put(cart.getSkuId().toString(),JsonUtils.serialize(cart));
    }

    public void deleteCart(String skuId) {
        // 通过登录的用户获得 redis 的 key
        String key = KEY_PREFIX + LoginInterceptor.getLoginUser().getId();

        // 移除购物车
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(key);
        hashOps.delete(skuId);
    }

    public void addLocalCarts(Cart[] carts) {
        List<Long> skuIds = new ArrayList<>();
        Map<Long,Integer> sku_num = new HashMap<>();
        for (Cart c : carts){
            skuIds.add(c.getSkuId());
            sku_num.put(c.getSkuId(),c.getNum());
        }

        List<Sku> list = this.goodsClient.querySkus(skuIds);

        Cart cart = new Cart();
        String key = KEY_PREFIX + LoginInterceptor.getLoginUser().getId();
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(key);
        for(Sku sku : list){
            cart.setUserId(LoginInterceptor.getLoginUser().getId());
            cart.setSkuId(sku.getId());
            cart.setImage(StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(), ",")[0]);
            cart.setPrice(sku.getPrice());
            cart.setTitle(sku.getTitle());
            cart.setOwnSpec(sku.getOwnSpec());
            cart.setNum(sku_num.get(sku.getId()));
            // 判断用户购物车中是否有数据
            String k = cart.getSkuId().toString();
            if(hashOps.hasKey(k)){
                // 获取数据
                String cartJSON = hashOps.get(k).toString();
                Cart parse = JsonUtils.parse(cartJSON, Cart.class);
                cart.setNum(cart.getNum() + parse.getNum());
            }
            hashOps.put(k,JsonUtils.serialize(cart));
        }
    }
}
