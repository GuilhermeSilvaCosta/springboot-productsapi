package com.guilherme.springweb;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.guilherme.springweb.entities.Product;
import com.guilherme.springweb.repos.ProductRepository;

@WebMvcTest
public class ProductRestControllerMvcTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository repository;

    @Test
    public void testFindAll() throws Exception {
        
        List<Product> products = this.buildListOfProduct();        
        when(repository.findAll()).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders.get("/productapi/products")
            .contextPath("/productapi"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(this.ObjectToJson(products)));
    }

    @Test
    public void testCreateProduct() throws Exception {
        Product product = this.buildProduct();
        when(repository.save(any())).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.post("/productapi/products")
            .contextPath("/productapi")
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.ObjectToJson(product)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Product product = this.buildProduct();
        when(repository.save(any())).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.put("/productapi/products")
            .contextPath("/productapi")
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.ObjectToJson(product)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteProduct() throws Exception {
        doNothing().when(repository).deleteById(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/productapi/products/{id}", "1")
            .contextPath("/productapi")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String ObjectToJson(Object object) throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

        return objectWriter.writeValueAsString(object);
    }

    private List<Product> buildListOfProduct() {
        Product product = this.buildProduct();
        List<Product> products = Arrays.asList(product);

        return products;
    }

    private Product buildProduct() {
        Product product = new Product();
        product.setId(1);
        product.setName("MacBook");
        product.setDescription("Irs Awesome");
        product.setPrice(1000);
        
        return product;
    }
}
