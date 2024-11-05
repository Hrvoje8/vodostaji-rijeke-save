package com.sava.backend.repositories;

import com.sava.backend.model.Vodostaj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VodostajRepository extends JpaRepository<Vodostaj, Long> {
}
