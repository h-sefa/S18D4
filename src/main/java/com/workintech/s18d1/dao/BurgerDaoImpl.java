package com.workintech.s18d1.dao;

import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Repository
public class BurgerDaoImpl implements BurgerDao{

    private EntityManager entityManager; // Burada finel kullanılmasa ne gibi sonuçlar doğurabilir ya da avantajlları

    @Autowired
    public BurgerDaoImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public Burger save(Burger burger) {
        entityManager.persist(burger);
        return burger;
    }

    @Override
    public Burger findById(long id) {
       Burger burger = entityManager.find(Burger.class, id);
       if(burger==null){
           throw new BurgerException("Burger not found : " + id, HttpStatus.NOT_FOUND); // Mesaj super e gidecek
       }
        return burger;
    }

    @Override
    public List<Burger> findAll() {
        TypedQuery<Burger> burgers = entityManager.createQuery("SELECT b FROM Burger b", Burger.class);
        return burgers.getResultList();
    }

    @Override
    public List<Burger> findByPrice(double price) {
        String query = "SELECT b FROM Burger b WHERE b.price > :price ORDER BY b.price DESC";
        TypedQuery<Burger> burgers = entityManager.createQuery(query, Burger.class);
        burgers.setParameter("price", price);
        return burgers.getResultList();
    }

    @Override
    public List<Burger> findByBreadType(BreadType breadType) {
        String query = "SELECT b FROM Burger b WHERE b.breadType = :breadType ORDER BY b.name";
        TypedQuery<Burger> burgers = entityManager.createQuery(query, Burger.class);
        burgers.setParameter("breadType", breadType);
        return burgers.getResultList();

    }

    @Override
    public List<Burger> findByContent(String contents) {
        String query = "SELECT b FROM Burger b WHERE b.contents LIKE CONCAT('%', :contents, '%' )";
        TypedQuery<Burger> burgers = entityManager.createQuery(query, Burger.class);
        burgers.setParameter("contents", contents);
        return burgers.getResultList();
    }

    @Transactional
    @Override
    public Burger update(Burger burger) {

        entityManager.merge(burger);
        return burger;
      /* if(newBurger == null){
           throw new BurgerException("Update cannot be made because a valid id could not be found : " + burger.getId(), HttpStatus.BAD_REQUEST);
        }
        else{

        }

       */

    }

    @Transactional
    @Override
    public Burger remove(long id) {
        Burger deletedBurger = entityManager.find(Burger.class, id);
        if(deletedBurger == null) {
            throw new BurgerException("No data to be deleted was found.", HttpStatus.BAD_REQUEST);
        }
        else{
            entityManager.remove(deletedBurger);
            return deletedBurger;
        }
    }
}
