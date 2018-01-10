package com.pvirtech.pzpolice.test.coffee;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Singleton;

import dagger.Component;

public class CoffeeActivity extends AppCompatActivity {
    @Singleton
    @Component(modules = {DripCoffeeModule.class})
    public interface Coffee {
        CoffeeMaker maker();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* Coffee coffee = DaggerCoffeeActivity_Coffee.builder().build();
        coffee.maker().brew();*/
    }

      /* @Singleton
    @Component(modules = {DripCoffeeModule.class})
    public interface Coffee {
        CoffeeMaker maker();
    }

    public static void main(String[] args) {
        Coffee coffee = DaggerCoffeeApp_Coffee.builder().build();
        coffee.maker().brew();
    }*/
}
