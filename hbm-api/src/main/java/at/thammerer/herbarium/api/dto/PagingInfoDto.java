package at.thammerer.herbarium.api.dto;

public class PagingInfoDto {

	private int pageNr;
	private int recordsPerPage;

	public PagingInfoDto() {
	}

	public PagingInfoDto(int pageNr, int recordsPerPage) {
		this.pageNr = pageNr;
		this.recordsPerPage = recordsPerPage;
	}

	public int getPageNr() {
		return pageNr;
	}

	public void setPageNr(int pageNr) {
		this.pageNr = pageNr;
	}

	public int getRecordsPerPage() {
		return recordsPerPage;
	}

	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}
}