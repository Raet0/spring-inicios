package ec.edu.ups.icc.fundamentos01.products.services;

import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductDto;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

public interface ProductService {

    ProductResponseDto create(CreateProductDto dto);

    List<ProductResponseDto> findAll();
    // // variables para la paginación
    // Page<ProductResponseDto> findAllPaginado(int page, int size, String[] sort);
    // Slice<ProductResponseDto> findAllSlice(int page, int size, String[] sort);

    ProductResponseDto findOne(int id);

    List<ProductResponseDto> findByUserId(Long userId);  

    List<ProductResponseDto> findByCategoryId(Long categoryId);  

    ProductResponseDto update(int id, UpdateProductDto dto);

    ProductResponseDto partialUpdate(int id, PartialUpdateProductDto dto);

    void delete(int id);

    Page<ProductResponseDto> findAllPaginado(int page, int size, String[] sort);

    Slice<ProductResponseDto> findAllSlice(int page, int size, String[] sort);
}