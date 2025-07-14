package com.example.btl.cart;

import com.example.btl.model.Movie;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final List<Movie> cartItems = new ArrayList<>();

    public static void addToCart(Movie movie) {
        cartItems.add(movie);
    }

    public static List<Movie> getCartItems() {
        return new ArrayList<>(cartItems); // Trả bản sao để tránh chỉnh trực tiếp
    }

    public static void removeFromCart(Movie movie) {
        cartItems.remove(movie);
    }

    public static void clearCart() {
        cartItems.clear();
    }
}
