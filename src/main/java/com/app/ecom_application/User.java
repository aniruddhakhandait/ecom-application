package com.app.ecom_application;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor

//@Entity (name ="user_table")

@Entity
public class User {

      @Id
      // generate unique value
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      private String firstName;
      private String lastName;

//      // Default constructure mandentory
//      public User() {
//      }
//
//      public User(Long id, String firstName, String lastName) {
//            this.id = id;
//            this.firstName = firstName;
//            this.lastName = lastName;
//      }



}
