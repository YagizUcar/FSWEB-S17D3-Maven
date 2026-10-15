package com.workintech.zoo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Koala {
    private long id;
    private String name;
    private double weight;
    private double sleepHour; // Testte double olarak çağrılıyor
    private String gender;
}