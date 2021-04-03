package com.starsky.backend.repository;

import com.starsky.backend.domain.Team;
import com.starsky.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> getAllByOwner(User owner);
}