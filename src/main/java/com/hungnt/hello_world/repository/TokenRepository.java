package com.hungnt.hello_world.repository;

import com.hungnt.hello_world.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, String> {
}
