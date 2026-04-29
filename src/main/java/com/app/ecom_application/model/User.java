package com.app.ecom_application.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Data
@NoArgsConstructor
//@AllArgsConstructor

@Entity (name ="user_table")

//@Entity
public class User {

      @Id
      // generate unique value
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      private String firstName;
      private String lastName;

      private String email;
      private String phone;

      // This is comming from enum
      private UserRole role = UserRole.CUSTOMER;


       @OneToOne (cascade = CascadeType.ALL )
       @JoinColumn(name = "address_id" , referencedColumnName = "id")
       private Address address;

       @CreationTimestamp
       private LocalDate createdAt;

       @UpdateTimestamp
       private LocalDate updateAt;


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
