package net.ticket.eventticketingsystem.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Validator {

    private static final Logger logger = LoggerFactory.getLogger(Validator.class);


    public static boolean validateValue(Long value){
        try{
            if (value == null){
                logger.info("value is required");
                return true;
            }else if (value < 0){
                logger.info("Value should be greater than zero");
                return true;
            }
            return false;
        }catch (NumberFormatException e){
            logger.info("Invalid value");
            return true;
        }
    }

}
