package at.thammerer.herbarium.dao.impl;

import com.github.springtestdbunit.dataset.AbstractDataSetLoader;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.springframework.core.io.Resource;

/**
 * User: ckuehmayer
 * Date: 04.03.15
 * Time: 10:01
 */
public class ColumnSensingDataSetLoader extends AbstractDataSetLoader {
	@Override
	protected IDataSet createDataSet(Resource resource) throws Exception {
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		builder.setColumnSensing(true);
		return builder.build(resource.getInputStream());
	}
}
