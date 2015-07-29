package at.thammerer.herbarium.api.dto;

import java.util.List;

/**
 * @author thammerer
 */
public class DataTableDto<T> {

	private int totalRecords;
	private int totalDisplayRecords;
	private int currentPage;
	private int maxPages;
	private List<T> data;

	public DataTableDto() {
	}

	public DataTableDto(int totalRecords, int iTotalDisplayRecords, List<T> data) {
		this.totalRecords = totalRecords;
		this.totalDisplayRecords = iTotalDisplayRecords;
		this.data = data;
	}

	public DataTableDto(int totalRecords, int totalDisplayRecords, int currentPage, int maxPages, List<T> data) {
		this.totalRecords = totalRecords;
		this.totalDisplayRecords = totalDisplayRecords;
		this.currentPage = currentPage;
		this.maxPages = maxPages;
		this.data = data;
	}


	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getTotalDisplayRecords() {
		return totalDisplayRecords;
	}

	public void setTotalDisplayRecords(int totalDisplayRecords) {
		this.totalDisplayRecords = totalDisplayRecords;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getMaxPages() {
		return maxPages;
	}

	public void setMaxPages(int maxPages) {
		this.maxPages = maxPages;
	}
}
