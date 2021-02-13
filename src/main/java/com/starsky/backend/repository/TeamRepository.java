package com.starsky.backend.repository;

import com.starsky.backend.domain.Team;
import com.starsky.backend.domain.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface TeamRepository extends JpaRepository<Team, Long> {
}