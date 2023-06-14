package com.example.repo;

import com.example.model.CodeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeListRepo extends JpaRepository<CodeList,Integer> {
}
