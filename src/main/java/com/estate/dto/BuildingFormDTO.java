package com.estate.dto;

import com.estate.enums.Direction;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuildingFormDTO {
    private Long id; // Null khi thêm mới, có giá trị khi sửa

    @Size(max = 255, message = "Tên tòa nhà không được vượt quá 255 ký tự")
    private String name;

    private Long districtId;

    @Size(max = 100, message = "Tên phường không được vượt quá 100 ký tự")
    private String ward;

    @Size(max = 255, message = "Tên đường không được vượt quá 255 ký tự")
    private String street;

    @Min(value = 1, message = "Số tầng phải >= 1")
    private Integer numberOfFloor;

    @Min(value = 0, message = "Số tầng hầm phải >= 0")
    private Integer numberOfBasement;

    @Min(value = 0, message = "Diện tích sàn phải >= 0")
    private Integer floorArea;

    private String direction;

    private String level;

    @DecimalMin(value = "0.0", inclusive = true, message = "Giá thuê phải >= 0")
    private BigDecimal rentPrice;

    @DecimalMin(value = "0.0", inclusive = true, message = "Phí dịch vụ phải >= 0")
    private BigDecimal serviceFee;

    @DecimalMin(value = "0.0", inclusive = true, message = "Phí ô tô phải >= 0")
    private BigDecimal carFee;

    @DecimalMin(value = "0.0", inclusive = true, message = "Phí xe máy phải >= 0")
    private BigDecimal motorbikeFee;

    @DecimalMin(value = "0.0", inclusive = true, message = "Phí nước phải >= 0")
    private BigDecimal waterFee;

    @DecimalMin(value = "0.0", inclusive = true, message = "Phí điện phải >= 0")
    private BigDecimal electricityFee;

    @DecimalMin(value = "0.0", inclusive = true, message = "Đặt cọc phải >= 0")
    private BigDecimal deposit;

    @Size(max = 500, message = "Link tòa nhà không được vượt quá 500 ký tự")
    private String linkOfBuilding;

    @Size(max = 500, message = "Link hình ảnh không được vượt quá 500 ký tự")
    private String image;

    @Pattern(
            regexp = "^\\d+(,\\d+)*$",
            message = "Chuỗi diện tích phải có dạng: 100,200,300 (các số nguyên ngăn cách bởi dấu phẩy)"
    )
    private String rentAreaValues;

    @Size(min = 1, message = "Vui lòng chọn ít nhất một nhân viên quản lý")
    private List<Long> staffIds = new ArrayList<>();
}
