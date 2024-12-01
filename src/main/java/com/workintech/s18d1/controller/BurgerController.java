package com.workintech.s18d1.controller;

import com.workintech.s18d1.dao.BurgerDao;
import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import com.workintech.s18d1.util.BurgerValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/burger")
public class BurgerController {

    private BurgerDao burgerDao ;  // Buradaki final keyword unu neden ihityaç duyarız. Ya da gerekli midir ?


    @Autowired
    public BurgerController(BurgerDao burgerDao) {
        this.burgerDao = burgerDao;
    }

    @GetMapping
    public List<Burger> getAllBurgers(){

       return  burgerDao.findAll();
    }
    @GetMapping("/{id}")
    public Burger getBurgerById(@PathVariable Long id){
        BurgerValidation.checkId(id);
        return burgerDao.findById(id);
    }

    @PostMapping
    public Burger saveBurger(@RequestBody Burger burger){
        BurgerValidation.checkId(burger.getId());
        BurgerValidation.checkNotNull(burger);
        return burgerDao.save(burger);
    }
    @PutMapping("/{id}")
    public Burger updateById(@PathVariable Long id, @RequestBody Burger burger){
        BurgerValidation.checkUpdatePathAndObjectId(id,burger);
        return burgerDao.update(burger);

    }
    @PutMapping()
    public Burger updateById(@RequestBody Burger burger){
        //BurgerValidation.checkUpdatePathAndObjectId(id,burger);
        return burgerDao.update(burger);

    }
    @GetMapping("/price/{price}")
    public List<Burger> getBurgerByPriceValue(@PathVariable double price){
        return burgerDao.findByPrice(price);
    }
    @GetMapping("/breadType/{breadType}")
    public List<Burger> getBurgerByBreadTypeValue(@PathVariable BreadType breadType){
        return burgerDao.findByBreadType(breadType);
    }
    @GetMapping("/content/{contents}")
    public List<Burger> getBurgerByContentValue(@PathVariable String contents){
        return burgerDao.findByContent(contents);
    }
    @DeleteMapping("/{id}")
    public Burger deleteBurgerById(@PathVariable Long id){

       return burgerDao.remove(id);
    }


}
