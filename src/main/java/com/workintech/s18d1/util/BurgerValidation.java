package com.workintech.s18d1.util;

import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class BurgerValidation {
    public static void checkId(Long id) {
        if(id<0){
            throw new BurgerException("Invalid id : " + id, HttpStatus.BAD_REQUEST);
        }
        log.info("Valid Id");
    }

    public static void checkNotNull(Burger burger) {
        if(burger.getName().isEmpty() || burger.getBreadType() == null || burger.getContents() == null || burger.getPrice() == null){
            throw new BurgerException("Fields should be not empty or null", HttpStatus.BAD_REQUEST);
        }
        log.info("Fields are not empty");
    }

    public static void checkUpdatePathAndObjectId(Long id, Burger burger) {
        if(burger.getId() != id){
            throw new BurgerException("There is a conflict between IDs", HttpStatus.BAD_REQUEST);
        }
        log.info("There is not a conflict between IDs");
    }
}
