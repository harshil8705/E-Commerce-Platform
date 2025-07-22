package com.ecommerce.project.controller;

import com.ecommerce.project.model.Cart;
import com.ecommerce.project.payload.CartDTO;
import com.ecommerce.project.repositories.CartRepository;
import com.ecommerce.project.service.CartService;
import com.ecommerce.project.util.AuthUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId,
                                                    @PathVariable Integer quantity){

        CartDTO cartDTO = cartService.addProductToCart(productId, quantity);
        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.CREATED);

    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartDTO>> getCarts() {

        return new ResponseEntity<>(cartService.getAllCarts(), HttpStatus.FOUND);

    }

    @GetMapping("/carts/users/cart")
    public ResponseEntity<CartDTO> getCartsById(){

        String email = authUtil.loggedInEmail();

        Cart cart = cartRepository.findCartByEmail(email);

        Long cartId = cart.getCartId();

        return new ResponseEntity<>(cartService.getCartById(email, cartId), HttpStatus.OK);

    }

    @PutMapping("/cart/products/{productId}/quantity/{operation}")
    public ResponseEntity<CartDTO> updateCartProduct(@PathVariable Long productId,
                                                     @PathVariable String operation) {

        CartDTO cartDTO = cartService.updateProductQuantityInCart(productId, operation.equalsIgnoreCase("delete") ? -1 : 1);

        return new ResponseEntity<>(cartDTO, HttpStatus.OK);

    }

    @Transactional
    @DeleteMapping("/carts/{cartId}/product/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId,
                                                        @PathVariable Long productId) {

        String status = cartService.deleteProductFromCart(cartId, productId);

        return new ResponseEntity<>(status, HttpStatus.OK);

    }

}
