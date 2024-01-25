package com.example.cafe.Service.Impl;

import com.example.cafe.Entity.Category;
import com.example.cafe.Entity.Product;
import com.example.cafe.Config.JwtFilter;
import com.example.cafe.Constants.CafeConstants;
import com.example.cafe.DAO.ProductDAO;
import com.example.cafe.Service.ProductService;
import com.example.cafe.Utils.ProjectUtils;
import com.example.cafe.DTO.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDAO productDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> createProduct(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validate(requestMap, false)) {
                    Product product = getProductFromMap(requestMap, false);
                    product.setCreatedDate(new Date());
                    productDao.save(product);
                    return ProjectUtils.getResponseEntity("Thêm mới sản phẩm thành công.", HttpStatus.OK);
                } else {
                    return ProjectUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
            } else {
                return ProjectUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
        try {
            return new ResponseEntity<>(productDao.getAllProduct(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validate(requestMap, true)) {
                    Optional<Product> optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()) {
                        Product product = getProductFromMap(requestMap, true);
                        product.setStatus(optional.get().getStatus());
                        product.setCreatedDate(new Date());
                        productDao.save(product);
                        return ProjectUtils.getResponseEntity("Cập nhật sản phẩm thành công.", HttpStatus.OK);
                    } else {
                        return ProjectUtils.getResponseEntity("Không tìm thấy sản phẩm này.", HttpStatus.OK);
                    }
                } else {
                    return ProjectUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
            } else {
                return ProjectUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional optional = productDao.findById(id);
                if (!optional.isEmpty()) {
                    productDao.deleteById(id);
                    return ProjectUtils.getResponseEntity("Xóa sản phẩm thành công.", HttpStatus.OK);
                }
                return ProjectUtils.getResponseEntity("Không tìm thấy sản phẩm này.", HttpStatus.OK);
            } else {
                return ProjectUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
                if (!optional.isEmpty()) {
                    productDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    return ProjectUtils.getResponseEntity("Cập nhật trạng thái sản phẩm thành công.", HttpStatus.OK);
                }
                return ProjectUtils.getResponseEntity("Không tìm thấy sản phẩm này.", HttpStatus.OK);
            } else {
                return ProjectUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getByCategory(Integer id) {
        try {
            return new ResponseEntity<>(productDao.getProductByCategory(id), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductDTO> getByIdProduct(Integer id) {
        try {
            return new ResponseEntity<>(productDao.getByIdProduct(id), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ProductDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validate(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {

        Category category = new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));

        Product product = new Product();
        if (isAdd) {
            product.setId(Integer.parseInt(requestMap.get("id")));
        } else {
            product.setStatus("true");
        }

        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setQuantity_product(Integer.parseInt(requestMap.get("quantity_product")));
        product.setPrice(Integer.parseInt(requestMap.get("price")));

        return product;
    }

    public ResponseEntity<String> decrementProductQuantity(Integer id, Integer quantity) {
        if (id != null && quantity != null) {
            Product product = productDao.findById(id).orElse(null);

            if (product != null && product.getQuantity_product() != null) {
                int currentQuantity = product.getQuantity_product();

                if (currentQuantity >= quantity) {
                    int newQuantity = currentQuantity - quantity;
                    product.setQuantity_product(newQuantity);
                    product.setCreatedDate(new Date());
                    productDao.save(product);
                    return ProjectUtils.getResponseEntity("Số lượng sản phẩm đã được cập nhât thành công.", HttpStatus.OK);
                } else {
                    return ResponseEntity.badRequest().body("Số lượng không đủ");
                }
            } else {
                return ResponseEntity.badRequest().body("Không tìm thấy sản phẩm hoặc số lượng chưa khởi tạo");
            }
        } else {
            return ResponseEntity.badRequest().body("ProductId hoặc Quantity không được null");
        }
    }

    public ResponseEntity<String> incrementProductQuantity(Integer id, Integer quantity) {
        try {
            Optional<Product> optionalProduct = productDao.findById(id);

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                int currentQuantity = product.getQuantity_product();
                product.setQuantity_product(currentQuantity + quantity);
                product.setCreatedDate(new Date());
                productDao.save(product);
            }
            return ProjectUtils.getResponseEntity("Reset sản phẩm thành công.", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ProjectUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<List<String>>> getProductByQuantity() {
        try {
            List<ProductDTO> productDTOList = productDao.getProductByQuantity();
            List<List<String>> finalChartDataList = new ArrayList<>();
            List<String> productNames = new ArrayList<>();
            List<String> quantities = new ArrayList<>();

            for (ProductDTO productDTO : productDTOList) {
                String productName = productDTO.getName();
                String quantity = String.valueOf(productDTO.getQuantity_product());
                productNames.add(productName);
                quantities.add(quantity);
            }

            List<String> dataRow = new ArrayList<>(productNames);
            List<String> dataColumn = new ArrayList<>(quantities);

            finalChartDataList.add(dataRow);
            finalChartDataList.add(dataColumn);

            return new ResponseEntity<>(finalChartDataList, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
