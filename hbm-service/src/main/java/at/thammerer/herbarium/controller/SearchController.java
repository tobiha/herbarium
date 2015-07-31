package at.thammerer.herbarium.controller;


import at.thammerer.herbarium.api.dto.DataTableDto;
import at.thammerer.herbarium.api.dto.HerbariumSheetFilterDto;
import at.thammerer.herbarium.api.dto.PagedSheetsRequestDto;
import at.thammerer.herbarium.api.dto.PagingInfoDto;
import at.thammerer.herbarium.api.dto.SheetDto;
import at.thammerer.herbarium.service.SheetService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.inject.Inject;

/**
 *
 *  REST service for users.
 *
 */

@Controller
@RequestMapping("/search")
public class SearchController {

    private static final Logger LOG = Logger.getLogger(SearchController.class);

    @Inject
    private SheetService sheetService;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/sheets", method = RequestMethod.POST)
    public DataTableDto<SheetDto> getPagedSheets(@RequestBody PagedSheetsRequestDto wrapper) {
        return sheetService.getPagedSheets(wrapper.getPaging(), wrapper.getFilter());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/sheets/{id}", method = RequestMethod.GET)
    public SheetDto getById(@PathVariable Long id) {
        return sheetService.findById(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/sheets/nr/{number}", method = RequestMethod.GET)
    public SheetDto getByNr(@PathVariable Long number) {
        return sheetService.findByNumber(number);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        LOG.error(exc.getMessage(), exc);
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
