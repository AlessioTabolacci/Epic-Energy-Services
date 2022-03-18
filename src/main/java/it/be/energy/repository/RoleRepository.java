package it.be.energy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.be.energy.model.Role;
import it.be.energy.model.Roles;



public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByRoleName(Roles role);
}
