package at.thammerer.herbarium.wrapper;

import java.util.List;

/**
 * User: ckuehmayer
 * Date: 22.07.14
 * Time: 11:52
 */
public class ResultsWithInfo<T> {
	private List<T> data;
	/**
	 * the total results found for a query.
	 * may not correspond to the size of the returned collection.
	 */
	private int totalResults;
	private int totalDisplayResults;

	public ResultsWithInfo() {}

	public ResultsWithInfo(List<T> data, int totalResults){
		this.data = data;
		this.totalResults = totalResults;
	}

	public ResultsWithInfo(List<T> data, int totalResults, int totalDisplayResults) {
		this.data = data;
		this.totalResults = totalResults;
		this.totalDisplayResults = totalDisplayResults;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getTotalDisplayResults() {
		return totalDisplayResults;
	}

	public void setTotalDisplayResults(int totalDisplayResults) {
		this.totalDisplayResults = totalDisplayResults;
	}

	public int getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

}
