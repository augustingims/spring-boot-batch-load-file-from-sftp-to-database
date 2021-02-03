package com.teamdevsolution.batch.repository;

import com.teamdevsolution.batch.domain.Formateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormateurRepository extends JpaRepository<Formateur, Integer> {
}
