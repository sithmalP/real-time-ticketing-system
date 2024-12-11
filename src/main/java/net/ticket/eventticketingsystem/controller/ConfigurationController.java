package net.ticket.eventticketingsystem.controller;

import net.ticket.eventticketingsystem.dto.ConfigurationDto;
import net.ticket.eventticketingsystem.dto.Response;
import net.ticket.eventticketingsystem.service.ConfigurationService;
import net.ticket.eventticketingsystem.service.TicketOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/configuration")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationController.class);


    @GetMapping
    public Response<Object> getConfigurations(){
        logger.info("configuration GET request received");
        return new Response<>(1,"success", configurationService.getAllConfigurations());
    }
    @PutMapping("/{id}")
    public Response<Void> updateConfigurations(@PathVariable("id") Long id, @RequestBody ConfigurationDto configurationDto){
        logger.info("configuration PUT request received");
        return new Response<>(1, "success", configurationService.updateConfiguration(id,configurationDto));
    }
}
