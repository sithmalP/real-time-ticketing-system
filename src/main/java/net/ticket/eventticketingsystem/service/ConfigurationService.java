package net.ticket.eventticketingsystem.service;

import net.ticket.eventticketingsystem.dto.ConfigurationDto;
import net.ticket.eventticketingsystem.dto.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ConfigurationService {
    List<ConfigurationDto> getAllConfigurations();

    Void updateConfiguration(Long id, ConfigurationDto configurationDto);
}
