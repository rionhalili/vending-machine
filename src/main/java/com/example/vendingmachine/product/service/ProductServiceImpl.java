package com.example.vendingmachine.product.service;

import com.example.vendingmachine.product.dto.BuyDTO;
import com.example.vendingmachine.product.dto.ProductDTO;
import com.example.vendingmachine.product.dto.ReceiptDTO;
import com.example.vendingmachine.product.model.Product;
import com.example.vendingmachine.product.repository.ProductRepository;
import com.example.vendingmachine.user.model.User;
import com.example.vendingmachine.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;
    private static final int[] AVAILABLE_COINS = {100, 50, 20, 10, 5};

    public ProductServiceImpl(ProductRepository productRepository, UserService userService) {
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    public Optional<Product> getProduct(UUID id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }

    @Override
    public Product save(Product product) {
        return productRepository.saveAndFlush(product);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return productRepository.findById(id);
    }

    @Override
    public Product update(Product product, ProductDTO productDTO) {
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setAmountAvailable(productDTO.getAmountAvailable());
        return productRepository.save(product);
    }

    @Override
    public ReceiptDTO buy(User user, Product product, BuyDTO buyDTO) {
        int finalAmountOfProducts = getAmount(product.getAmountAvailable(), buyDTO.getAmountOfProducts());

        product.setAmountAvailable(finalAmountOfProducts);
        Product boughtProduct = productRepository.save(product);

        double change = getChange(user.getDeposit(), (product.getPrice() * buyDTO.getAmountOfProducts()));

        //update buyer deposit
        userService.updateBuyerDeposit(user, change);
        //update seller deposit
        userService.updateSellerDeposit(boughtProduct.getUserId(), boughtProduct.getPrice());

        return new ReceiptDTO(
                product.getPrice(),
                new ProductDTO(boughtProduct.getAmountAvailable(), boughtProduct.getName(), boughtProduct.getPrice()),
                returnChange(change)
        );
    }

    private double getChange(double deposit, double price) {
        return deposit - price;
    }

    private int getAmount(int availableAmount, int requiredAmount) {
        return availableAmount - requiredAmount;
    }

    private List<Integer> returnChange(double amount) {
        List<Integer> finalRes = new ArrayList<>();

        while (amount > 0) {
            for (int coin : AVAILABLE_COINS) {
                if (amount >= coin) {
                    amount = amount - coin;
                    finalRes.add(coin);
                }
            }
        }
        return finalRes;
    }
}
