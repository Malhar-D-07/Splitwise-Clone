package org.example.splitwise.repo;

import org.example.splitwise.models.SplitwiseGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepo extends JpaRepository<SplitwiseGroup, Long> {

    @Override
     Optional<SplitwiseGroup> findById(Long gropuId);
}
