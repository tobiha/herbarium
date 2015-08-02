package at.thammerer.herbarium.service;

import at.thammerer.herbarium.api.dto.DataTableDto;
import at.thammerer.herbarium.api.dto.HerbariumSheetFilterDto;
import at.thammerer.herbarium.api.dto.PagingInfoDto;
import at.thammerer.herbarium.api.dto.SheetDto;
import at.thammerer.herbarium.dao.SheetDao;
import at.thammerer.herbarium.model.HerbariumSheet;
import at.thammerer.herbarium.util.PagingUtil;
import at.thammerer.herbarium.wrapper.ResultsWithInfo;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


/**
 * Business service for Herbarium sheet entity related operations
 */
@Service
public class SheetService {

	private static final Logger LOG = LoggerFactory.getLogger(SheetService.class);

	@Inject
	private SheetDao sheetDao;

	@Inject
	private Mapper mapper;

	@Transactional(readOnly = true)
	public DataTableDto<SheetDto> getPagedSheets(PagingInfoDto paging, HerbariumSheetFilterDto filter) {

		int index = PagingUtil.calculateIndex(paging.getPageNr(), paging.getRecordsPerPage());

		ResultsWithInfo<HerbariumSheet> pagedHerbariumSheets = sheetDao.getPagedHerbariumSheets(paging.getRecordsPerPage(), index, filter);

		List<SheetDto> sheetDtos = new ArrayList<>(pagedHerbariumSheets.getData().size());
		for(HerbariumSheet sheet : pagedHerbariumSheets.getData()){
			sheetDtos.add(mapper.map(sheet, SheetDto.class));
		}

		int maxPages = PagingUtil.calculateMaxPages(pagedHerbariumSheets.getTotalDisplayResults(), paging.getRecordsPerPage());

		return new DataTableDto<SheetDto>(pagedHerbariumSheets.getTotalResults(), pagedHerbariumSheets.getTotalDisplayResults(), paging.getPageNr(), maxPages, sheetDtos );

	}

	@Transactional
	public SheetDto saveOrUpdateSheet(SheetDto sheetDto){
		//TODO validation, get next number
		HerbariumSheet newSheet = mapper.map(sheetDto, HerbariumSheet.class);

		if(newSheet.getId() != null){
			HerbariumSheet existingSheet = sheetDao.findById(newSheet.getId());
			existingSheet.updateData(newSheet);
			newSheet = existingSheet;
		}else {
			newSheet.setNumber(sheetDao.getNextHerbariumNumber());
		}
		HerbariumSheet persisted = sheetDao.saveOrUpdate(newSheet);
		LOG.info("new sheet persisted: {}", persisted.toString());
		return mapper.map(persisted, SheetDto.class);
	}

	@Transactional(readOnly = true)
	public SheetDto findById(Long id){
		HerbariumSheet sheet = sheetDao.findById(id);
		if(sheet != null){
			return mapper.map(sheet, SheetDto.class);
		}
		return null;
	}

	@Transactional(readOnly = true)
	public SheetDto findByNumber(Long number){
		HerbariumSheet sheet = sheetDao.findByNumber(number);
		if(sheet != null){
			return mapper.map(sheet, SheetDto.class);
		}
		return null;
	}


}
