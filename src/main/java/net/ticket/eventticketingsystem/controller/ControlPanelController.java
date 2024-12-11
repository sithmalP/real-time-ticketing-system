package net.ticket.eventticketingsystem.controller;

import net.ticket.eventticketingsystem.dto.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("system")
public class SystemController {

    @GetMapping("/startOrStop")
    public Response startOrStop(){
        return null;
    }

    @GetMapping("/details")
    public Response getDetails(){
        return null;
    }

    @GetMapping("/logs")
    public Response getLogs(){
        return null;
    }
}
