package com.example.vendingmachine.product.service;

import com.example.vendingmachine.product.dto.BuyDTO;
import com.example.vendingmachine.product.dto.ProductDTO;
import com.example.vendingmachine.product.dto.ReceiptDTO;
import com.example.vendingmachine.product.model.Product;
import com.example.vendingmachine.role.model.Role;
import com.example.vendingmachine.role.model.RoleType;
import com.example.vendingmachine.user.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceImplTest {

    @Mock
    ProductService productService;

    @Test
    public void shouldReturnRequestedProduct() {
        Product product = new Product("Coca-cola", 1, 1.0);

        when(productService.getProduct(product.getId()))
                .thenReturn(Optional.of(product));

        Optional<Product> response = productService.getProduct(product.getId());
        assertThat(response.get().getAmountAvailable()).isEqualTo(1);
        assertThat(response.get().getName()).isEqualTo("Coca-cola");
        assertThat(response.get().getPrice()).isEqualTo(1.0);
    }

    @Test
    public void shouldReturnNullWhenProductDoesNotExist() {
        UUID id = UUID.fromString("7a730c66-05ae-40b9-8703-fa4dce3d50d5");
        when(productService.getProduct(id))
                .thenReturn(null);

        Optional<Product> response = productService.getProduct(id);
        assertThat(response).isNull();
    }


    @Test
    public void shouldReturnCorrectReceipt() {
        Product product = new Product("Coca-cola", 1, 5.0);
        Role role = new Role(RoleType.ROLE_BUYER);
        User user = new User("buyer", "password", role);

        //Request Body
        BuyDTO buyDTO = new BuyDTO(1);

        List<Integer> availableCoins = new ArrayList<>();
        availableCoins.add(5);

        when(productService.buy(user, product, buyDTO))
                .thenReturn(
                        new ReceiptDTO(10.0, new ProductDTO(1, "Coca-cola", 5.0), availableCoins)
                );

        ReceiptDTO receiptDTO = productService.buy(user, product, buyDTO);

        assertSoftly(
                softly -> {
                    softly.assertThat(receiptDTO.getProductDTO().getAmountAvailable()).isEqualTo(1);
                    softly.assertThat(receiptDTO.getProductDTO().getName()).isEqualTo("Coca-cola");
                    softly.assertThat(receiptDTO.getProductDTO().getPrice()).isEqualTo(5.0);

                    softly.assertThat(receiptDTO.getTotal()).isEqualTo(10.0);
                    softly.assertThat(receiptDTO.getChange()).isEqualTo(availableCoins);
                }
        );


    }

}