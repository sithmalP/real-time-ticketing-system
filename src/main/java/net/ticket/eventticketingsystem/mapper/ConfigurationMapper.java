package net.ticket.eventticketingsystem.mapper;

import net.ticket.eventticketingsystem.dto.ConfigurationDto;
import net.ticket.eventticketingsystem.model.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConfigurationMapper {

    public List<ConfigurationDto> mapDtoToDaoList(List<Configuration> configurationDtoList) {
        return configurationDtoList.stream()
                .map(this::mapConfigurationToDto)
                .collect(Collectors.toList());
    }

    public Configuration mapDtoToDao(ConfigurationDto configurationDto) {
        Configuration configuration = new Configuration();
        configuration.setId(configurationDto.getId());
        configuration.setParameter(configurationDto.getParameter());
        configuration.setDefaultValue(configurationDto.getDefaultValue());
        configuration.setValue(configurationDto.getValue());
        return configuration;
    }

    public ConfigurationDto mapConfigurationToDto(Configuration configuration) {
        ConfigurationDto configurationDto = new ConfigurationDto();
        configurationDto.setId(configuration.getId());
        configurationDto.setParameter(configuration.getParameter());
        configurationDto.setDefaultValue(configuration.getDefaultValue());
        configurationDto.setValue(configuration.getValue());
        return configurationDto;
    }

}
