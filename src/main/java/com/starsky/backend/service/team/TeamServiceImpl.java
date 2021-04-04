package com.starsky.backend.service.team;

import com.starsky.backend.domain.Role;
import com.starsky.backend.domain.Team;
import com.starsky.backend.domain.TeamMember;
import com.starsky.backend.domain.User;
import com.starsky.backend.repository.TeamMemberRepository;
import com.starsky.backend.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final Logger logger = LoggerFactory.getLogger(TeamServiceImpl.class);

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, TeamMemberRepository teamMemberRepository) {
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
    }

    @Override
    public List<Team> getTeams(User user) {
        List<Team> teams;
        if (user.getRole() == Role.MANAGER) {
            teams = teamRepository.getAllByOwner(user);
        } else {
            teams = teamMemberRepository.getAllByMember(user).stream().map(TeamMember::getTeam).collect(Collectors.toList());
        }
        return teams;
    }

    @Override
    public Team getTeam(long id) throws ResourceNotFoundException {
        var team = teamRepository.findById(id);
        if (team.isPresent()) {
            return team.get();
        }
        var error = "Team (id=%d) does not exist.".formatted(id);
        this.logger.warn(error);
        throw new ResourceNotFoundException(error);
    }


    @Override
    public List<TeamMember> getTeamMembers(long teamId) throws ResourceNotFoundException {
        var team = teamRepository.findById(teamId);
        if (team.isPresent()) {
            return teamMemberRepository.getAllByTeam(team.get());
        }
        var error = "Team (id=%d) does not exist.".formatted(teamId);
        this.logger.warn(error);
        throw new ResourceNotFoundException(error);
    }

    @Override
    public Team createTeam(String teamName, User owner) {
        boolean exists = teamRepository.existsByOwnerAndName(owner, teamName);
        if (exists) {
            var error = "Key (team name)=(%s) already exists for user %s.".formatted(teamName, owner.getName());
            this.logger.warn(error);
            throw new DataIntegrityViolationException(error);
        }
        var team = new Team(teamName, owner);
        return teamRepository.save(team);
    }

    @Override
    public TeamMember createTeamMember(User member, Team team) {
        boolean exists = teamMemberRepository.existsByMemberAndTeam(member, team);
        if (exists) {
            var error = "Key (team member)=(%s) already exists in team %s.".formatted(member.getName(), team.getName());
            this.logger.warn(error);
            throw new DataIntegrityViolationException(error);
        }
        var teamMember = new TeamMember(member, team);
        return teamMemberRepository.save(teamMember);
    }
}
