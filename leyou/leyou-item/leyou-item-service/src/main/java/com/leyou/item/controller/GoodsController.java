package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuBo;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuBoByPage(
            @RequestParam(value = "key", required = false)String key,
            @RequestParam(value = "saleable", required = false)Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "rows", defaultValue = "5")Integer rows
    ){
        PageResult<SpuBo> pageResult = this.goodsService.querySpuBoByPage(key, saleable, page, rows);
        if(CollectionUtils.isEmpty(pageResult.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pageResult);
    }

    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody SpuBo spuBo){
        this.goodsService.saveGoods(spuBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody SpuBo spuBo){
        this.goodsService.updateGoods(spuBo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("spu/detail/{spuId}")
    public ResponseEntity<SpuDetail> querySpuDetailBySpuId(@PathVariable("spuId")Long spuId){
        SpuDetail spuDetail = this.goodsService.querySpuDetailBySpuId(spuId);
        if (spuDetail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(spuDetail);
    }

    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> querySkusBySpuId(@RequestParam("id")Long spuId){
        List<Sku> skus = this.goodsService.querySkusBySpuId(spuId);
        if (CollectionUtils.isEmpty(skus)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(skus);
    }


    @DeleteMapping("goods/del/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable("id") Long spuId){
        this.goodsService.deleteGoodsById(spuId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("goods/status")
    public ResponseEntity<Void> changeSaleable(@RequestParam("spuId") Long spuId,@RequestParam("status") Boolean saleable){
        this.goodsService.changeSaleableById(spuId,saleable);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("spu/{id}")
    public ResponseEntity<Spu> querySpuById(@PathVariable("id") Long id){
        Spu spu = this.goodsService.querySpuById(id);
        if(spu == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(spu);
    }

    @GetMapping("sku/{id}")
    public ResponseEntity<Sku> querySkuById(@PathVariable("id") Long id){
        Sku sku = this.goodsService.querySkuById(id);
        if(sku == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(sku);
    }

    @GetMapping("skus")
    public ResponseEntity<List<Sku>> querySkus(@RequestParam("ids") List<Long> ids){
        List<Sku> skus = this.goodsService.querySkusBySkuIds(ids);
        if (CollectionUtils.isEmpty(skus)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(skus);
    }
}