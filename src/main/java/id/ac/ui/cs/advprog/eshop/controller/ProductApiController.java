package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.exception.ProductNotFound;
import id.ac.ui.cs.advprog.eshop.model.IdDto;
import id.ac.ui.cs.advprog.eshop.model.ProductDto;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/api")
@RequiredArgsConstructor
public class ProductApiController {

    private final ProductService service;

    @PostMapping("/edit")
    public ResponseEntity<Void> editProductPost(@RequestBody ProductDto productDto) {
        try {
            service.edit(productDto);
        } catch (ProductNotFound e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteProductPost(@RequestBody IdDto idDto) {
        try {
            service.removeById(idDto.getId());
        } catch (ProductNotFound e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}