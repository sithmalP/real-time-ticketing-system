package net.ticket.eventticketingsystem.repository;

import net.ticket.eventticketingsystem.model.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {
}
