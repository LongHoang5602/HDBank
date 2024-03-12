package com.example.hdbank.controller;

import com.example.hdbank.exception.ResourceNotFoundException;
import com.example.hdbank.model.Cart;
import com.example.hdbank.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/carts")
    public List<Cart> getAllCart(){
        return cartRepository.findAll();
    }

    @PostMapping("/item")
    public Cart addItem(@RequestBody Cart cart) {
        return cartRepository.save(cart);
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<Cart> getItemInCartById(@PathVariable Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not exist with id :" + id));
        return ResponseEntity.ok(cart);
    }


    @PatchMapping("/item/{id}")
    public ResponseEntity<Cart> updateQuantityItem(@PathVariable Long id, @RequestBody Cart cart){
        Cart item = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not exist with id :" + id));

        item.setQuantity(cart.getQuantity());

        Cart updatedCart = cartRepository.save(item);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteItem(@PathVariable Long id){
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not exist with id :" + id));

        cartRepository.delete(cart);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
