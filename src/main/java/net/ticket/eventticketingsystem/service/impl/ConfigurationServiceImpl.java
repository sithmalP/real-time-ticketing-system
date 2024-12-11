package net.ticket.eventticketingsystem.service.impl;

import jakarta.annotation.PostConstruct;
import net.ticket.eventticketingsystem.controller.ConfigurationController;
import net.ticket.eventticketingsystem.dto.ConfigurationDto;
import net.ticket.eventticketingsystem.dto.Response;
import net.ticket.eventticketingsystem.mapper.ConfigurationMapper;
import net.ticket.eventticketingsystem.model.Configuration;
import net.ticket.eventticketingsystem.repository.ConfigurationRepository;
import net.ticket.eventticketingsystem.service.ConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationServiceImpl.class);


    @Autowired
    private ConfigurationRepository configurationRepository;

    @Autowired
    private ConfigurationMapper configurationMapper;

    private List<Configuration> configurationList;

    private final Lock lock = new ReentrantLock();

    @Override
    public List<ConfigurationDto> getAllConfigurations() {
        lock.lock();
        try {
            return configurationMapper.mapDtoToDaoList(configurationList);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Void updateConfiguration(Long id, ConfigurationDto configurationDto) {
        lock.lock();
        try {
            Optional<Configuration> optionalConfiguration = configurationList.stream()
                    .filter(config -> id == config.getId())
                    .findFirst();

            if (optionalConfiguration.isPresent()) {
                Configuration existingConfig = optionalConfiguration.get();
                existingConfig = configurationMapper.mapDtoToDao(configurationDto);
                configurationRepository.save(existingConfig);  // Update in the repository

                logger.info("Configuration updated successfully");
                updateConfigurationList();
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    @PostConstruct
    public void initializeConfigurations() {
        updateConfigurationList();
    }

    private void updateConfigurationList() {
        lock.lock();
        try {
            configurationList = configurationRepository.findAll();
            logger.info("successfully load the configuration list from database");
        } finally {
            lock.unlock();
        }
    }
}
