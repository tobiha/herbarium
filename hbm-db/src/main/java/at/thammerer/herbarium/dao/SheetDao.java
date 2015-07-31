package at.thammerer.herbarium.dao;

import at.thammerer.herbarium.api.dto.HerbariumSheetFilterDto;
import at.thammerer.herbarium.model.HerbariumSheet;
import at.thammerer.herbarium.util.CommonUtils;
import at.thammerer.herbarium.wrapper.ResultsWithInfo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

	public HerbariumSheet findByNumber(Long number){
		TypedQuery<HerbariumSheet> query = em.createQuery("select s from HerbariumSheet s where s.number = :number", HerbariumSheet.class);
		query.setParameter("number", number);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public HerbariumSheet saveOrUpdate(HerbariumSheet sheet){
//		saveOrUpdateLocation(sheet);
		//TODO version problems
		return em.merge(sheet);
	}

	private void saveOrUpdateLocation(HerbariumSheet sheet) {
		 em.persist(sheet.getLocation());
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
			predicates.add(cb.like(cb.lower(searchRoot.<String>get("scientificName")), "%" + filter.getScientificName().toLowerCase() + "%"));
		}

		if (filter.getSubSpecies() != null) {
			predicates.add(cb.like(cb.lower(searchRoot.<String>get("subSpecies")), "%"+ filter.getSubSpecies().toLowerCase() + "%"));
		}

		if (filter.getCollector() != null) {
			predicates.add(cb.like(cb.lower(searchRoot.<String>get("collector")), "%"+ filter.getCollector().toLowerCase() + "%"));
		}

		if (filter.getLocationDescription() != null) {
			predicates.add(cb.like(cb.lower(searchRoot.<String>get("locationDescription")), "%"+ filter.getLocationDescription().toLowerCase() + "%"));
		}

		if (filter.getAltitude() != null) {
			predicates.add(cb.like(cb.lower(searchRoot.<String>get("altitude")), "%"+ filter.getAltitude().toLowerCase() + "%"));
		}

		if (filter.getExposition() != null) {
			predicates.add(cb.like(cb.lower(searchRoot.<String>get("exposition")), "%"+ filter.getExposition().toLowerCase() + "%"));
		}

		if (filter.getHabitatInformation() != null) {
			predicates.add(cb.like(cb.lower(searchRoot.<String>get("habitatInformation")), "%"+ filter.getHabitatInformation().toLowerCase() + "%"));
		}

		if (filter.getAnnotations() != null) {
			predicates.add(cb.like(cb.lower(searchRoot.<String>get("annotations")), "%"+ filter.getAnnotations().toLowerCase() + "%"));
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
