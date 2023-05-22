package com.hiFive.FridgeCircle.repository;

import com.hiFive.FridgeCircle.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface UserRepository  extends CrudRepository<User, BigInteger> {

    User findByUsernameAndPassword(String userName, String password);

    User findByEmail(String email);

}
