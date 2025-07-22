package com.chiikawa.demo.model_product;

import com.chiikawa.demo.model.BaseResponseModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponseWithDataModel extends BaseResponseModel {
    private Object data;

    public BaseResponseWithDataModel(String status, String message, Object data) {
        super(status, message);
        this.data = data;
    }
}
