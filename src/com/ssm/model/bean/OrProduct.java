package com.ssm.model.bean;

import java.util.List;
  //对VO对象再进行封装
public class OrProduct {
      private OrderProduct orderProduct;
      private List<OrderProduct> orderProducts;
      public  List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }
      public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

      public OrderProduct getOrderProduct() {
          return orderProduct;
      }
      public void setOrderProduct(OrderProduct orderProduct) {
          this.orderProduct = orderProduct;
      }
}
