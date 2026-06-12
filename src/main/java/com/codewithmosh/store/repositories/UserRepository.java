package com.codewithmosh.store.repositories;

import com.codewithmosh.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


//public interface UserRepository extends CrudRepository<User, Long> {
//}
//TODO: change CrudRepository to JpaRepository because change user to userDto
public interface UserRepository extends JpaRepository<User, Long> {
}
