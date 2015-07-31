package at.thammerer.herbarium.controller;


import at.thammerer.herbarium.api.dto.SheetDto;
import at.thammerer.herbarium.service.SheetService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.inject.Inject;

/**
 *
 *  REST service for users.
 *
 */

@Controller
@RequestMapping("/sheet")
public class SheetController {

    private static final Logger LOG = Logger.getLogger(SheetController.class);

    @Inject
    private SheetService sheetService;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public SheetDto saveOrUpdate(@RequestBody SheetDto sheetDto) {
        LOG.debug("saving new sheet");
        return sheetService.saveOrUpdateSheet(sheetDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        LOG.error(exc.getMessage(), exc);
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }



}
