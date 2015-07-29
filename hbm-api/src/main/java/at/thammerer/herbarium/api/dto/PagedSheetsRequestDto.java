package at.thammerer.herbarium.api.dto;

/**
 * @author thammerer
 */
public class PagedSheetsRequestDto {

	private PagingInfoDto paging;
	private  HerbariumSheetFilterDto filter;

	public PagedSheetsRequestDto() {
	}

	public PagedSheetsRequestDto(PagingInfoDto paging, HerbariumSheetFilterDto filter) {
		this.paging = paging;
		this.filter = filter;
	}

	public PagingInfoDto getPaging() {
		return paging;
	}

	public void setPaging(PagingInfoDto paging) {
		this.paging = paging;
	}

	public HerbariumSheetFilterDto getFilter() {
		return filter;
	}

	public void setFilter(HerbariumSheetFilterDto filter) {
		this.filter = filter;
	}
}
