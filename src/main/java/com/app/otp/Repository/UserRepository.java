package com.app.otp.Repository;
import com.app.otp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface
UserRepository extends JpaRepository<User,Long> {
    User findByUserName(String username);
}