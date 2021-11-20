package com.example.personmanagementsystem;

import java.io.Serializable;

public class PersonItems implements Serializable {

        String name;
        int id;

        public PersonItems(String name, int id) {
            this.name = name;
            this.id = id;
        }
}
