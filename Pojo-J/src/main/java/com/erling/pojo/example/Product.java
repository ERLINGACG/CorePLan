package com.erling.pojo.example;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class Product {
    int id;
    String name;
    String description;
    double price;
    String category;

}
