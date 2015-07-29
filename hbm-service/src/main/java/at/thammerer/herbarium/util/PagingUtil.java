package at.thammerer.herbarium.util;

/**
 * @author thammerer
 */
public class PagingUtil {

	public static int calculateIndex(int pageNr, int recordsPerPage){
		if(pageNr < 1) pageNr = 1;
		if(recordsPerPage < 1) recordsPerPage = 1;
		return (pageNr-1)*recordsPerPage;
	}

	public static int calculateMaxPages(int totalRecords, int recordsPerPage){
		return (int) Math.ceil((double)totalRecords/(double)recordsPerPage);
	}

}
