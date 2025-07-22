package com.ecommerce.project.service;

import com.ecommerce.project.payload.CartDTO;

import java.util.List;

public interface CartService {

    CartDTO addProductToCart(Long productId, Integer quantity);

    List<CartDTO> getAllCarts();

    CartDTO getCartById(String email, Long cartId);

    CartDTO updateProductQuantityInCart(Long productId, int quantity);

    String deleteProductFromCart(Long cartId, Long productId);

    void updateProductInCarts(Long cartId, Long productId);

}
