package org.example.splitwise.repo;

import org.example.splitwise.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Override
    Optional<User> findById(Long uId);
    Optional<User> findByEmail(String email);

    @Override
    List<User> findAllById(Iterable<Long> longs);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.groups WHERE u.id = :id")
    public Optional<User> findByIdWithGroup(@Param("id") Long id);
}
