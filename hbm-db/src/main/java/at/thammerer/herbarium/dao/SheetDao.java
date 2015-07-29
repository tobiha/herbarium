package at.thammerer.herbarium.dao;

import at.thammerer.herbarium.api.dto.HerbariumSheetFilterDto;
import at.thammerer.herbarium.model.HerbariumSheet;
import at.thammerer.herbarium.util.CommonUtils;
import at.thammerer.herbarium.wrapper.ResultsWithInfo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author thammerer
 */
@Repository
public class SheetDao {

	@PersistenceContext
	private EntityManager em;


	public HerbariumSheet findById(Long id){
		return em.find(HerbariumSheet.class, id);
	}

	public HerbariumSheet saveOrUpdate(HerbariumSheet sheet){
		return em.merge(sheet);
	}

	public ResultsWithInfo<HerbariumSheet> getPagedHerbariumSheets(int recordsPerPage, int index, HerbariumSheetFilterDto filter){

		CriteriaBuilder cb = em.getCriteriaBuilder();

		// the actual search query that returns one page of results
		CriteriaQuery<HerbariumSheet> searchQuery = cb.createQuery(HerbariumSheet.class);
		Root<HerbariumSheet> searchRoot = searchQuery.from(HerbariumSheet.class);
		searchQuery.select(searchRoot);
		searchQuery.where(getCommonWhereCondition(cb, searchRoot, filter));
		searchQuery.orderBy(cb.asc(searchRoot.<Long>get("number")));

		TypedQuery<HerbariumSheet> query = em.createQuery(searchQuery);
		query.setFirstResult(index);
		query.setMaxResults(recordsPerPage);
		List<HerbariumSheet> herbariumSheets = query.getResultList();


		CriteriaQuery<Long> countTotalDisplayQuery = cb.createQuery(Long.class);
		Root<HerbariumSheet> countTotalRoot = countTotalDisplayQuery.from(HerbariumSheet.class);
		countTotalDisplayQuery.select(cb.count(countTotalRoot))
			.where(getCommonWhereCondition(cb, searchRoot, filter));
		Query countDisplayQ = em.createQuery(countTotalDisplayQuery);
		Long totalDisplayResults = (Long) countDisplayQ.getSingleResult();


		CriteriaQuery<Long> countTotalQuery = cb.createQuery(Long.class);
		countTotalQuery.select(cb.count(countTotalQuery.from(HerbariumSheet.class)));
		Query countTotalQ = em.createQuery(countTotalQuery);
		Long totalResults = (Long) countTotalQ.getSingleResult();

		return new ResultsWithInfo<HerbariumSheet>(herbariumSheets, CommonUtils.safeLongToInt(totalResults), CommonUtils.safeLongToInt(totalDisplayResults));
	}




	private Predicate[] getCommonWhereCondition(CriteriaBuilder cb, Root<HerbariumSheet> searchRoot, HerbariumSheetFilterDto filter) {

		List<Predicate> predicates = new ArrayList<>();


		if (filter.getNumber() != null) {
			predicates.add(cb.equal(searchRoot.<Long>get("number"), filter.getNumber()));
		}

		if (filter.getScientificName() != null) {
			predicates.add(cb.like(searchRoot.<String>get("scientificName"), "%" + filter.getScientificName() + "%"));
		}

		if (filter.getTaxon() != null) {
			predicates.add(cb.like(searchRoot.<String>get("taxon"), "%" + filter.getTaxon() + "%"));
		}

		if (filter.getSubSpecies() != null) {
			predicates.add(cb.like(searchRoot.<String>get("subSpecies"), "%"+ filter.getSubSpecies() + "%"));
		}

		if (filter.getCollector() != null) {
			predicates.add(cb.like(searchRoot.<String>get("collector"), "%"+ filter.getCollector() + "%"));
		}

		if (filter.getLocationDescription() != null) {
			predicates.add(cb.like(searchRoot.<String>get("locationDescription"), "%"+ filter.getLocationDescription() + "%"));
		}

		if (filter.getAltitude() != null) {
			predicates.add(cb.like(searchRoot.<String>get("altitude"), "%"+ filter.getAltitude() + "%"));
		}

		if (filter.getExposition() != null) {
			predicates.add(cb.like(searchRoot.<String>get("exposition"), "%"+ filter.getExposition() + "%"));
		}

		if (filter.getHabitatInformation() != null) {
			predicates.add(cb.like(searchRoot.<String>get("habitatInformation"), "%"+ filter.getHabitatInformation() + "%"));
		}

		if (filter.getAnnotations() != null) {
			predicates.add(cb.like(searchRoot.<String>get("annotations"), "%"+ filter.getAnnotations() + "%"));
		}

		if (filter.getCollectionDateFrom() != null) {
			predicates.add(cb.greaterThanOrEqualTo(searchRoot.<Date>get("collectionDate"), returnBeginOfDay(filter.getCollectionDateFrom())));
		}

		if (filter.getCollectionDateTo() != null) {
			predicates.add(cb.lessThanOrEqualTo(searchRoot.<Date>get("collectionDate"), returnEndOfDay(filter.getCollectionDateTo())));
		}

		return predicates.toArray(new Predicate[]{});
	}


	private Date returnBeginOfDay(Date date){
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	private Date returnEndOfDay(Date date){
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}



}
