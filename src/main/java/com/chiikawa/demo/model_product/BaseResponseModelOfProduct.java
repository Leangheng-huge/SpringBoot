package com.chiikawa.demo.model_product;

import com.chiikawa.demo.model.BaseResponseModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponseModelOfProduct extends BaseResponseModel {
    private Object data;

    public BaseResponseModelOfProduct(String status, String message, Object data) {
        super(status, message);
        this.data = data;
    }
}
