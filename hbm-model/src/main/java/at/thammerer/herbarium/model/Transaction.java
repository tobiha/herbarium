package at.thammerer.herbarium.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author thammerer
 */
@Entity
@Table(name = "transaction", uniqueConstraints = @UniqueConstraint(columnNames = {"iccid", "aid", "atc"}))
@NamedQueries({
	@NamedQuery(
		name = "Transaction.findByIccidAndAid",
		query = "select t from Transaction t where iccid = :iccid and lower(aid) = :aid order by date desc"
	),
	@NamedQuery(
		name = "Transaction.findByIccidAidAtc",
		query = "select t from Transaction t where iccid = :iccid and lower(aid) = :aid  and atc = :atc order by date desc"
	),
	@NamedQuery(
		name = "Transaction.getIdsByIccidAndAid",
		query = "select t.id from Transaction t where iccid = :iccid and lower(aid) = :aid order by date desc"
	),
	@NamedQuery(
		name = "Transaction.countByIccidAndAid",
		query = "select count(t.id) from Transaction t where iccid = :iccid and lower(aid) = :aid"
	)
})
public class Transaction {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Column(nullable = false)
	private String iccid;

	@NotNull
	@Column(nullable = false)
	private String aid;

	@NotNull
	@Column(nullable = false)
	private Long amount;

	@NotNull
	@Size(min = 3, max = 3)
	@Column(name="currency_code", nullable = false, length = 3)
	private String currencyCode;

	@NotNull
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@NotNull
	@Column(nullable = false)
	private String atc;

	public Transaction() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAtc() {
		return atc;
	}

	public void setAtc(String atc) {
		this.atc = atc;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Transaction that = (Transaction) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	public String toUniqueIdentifierString() {
		return "Transaction: " +
			"iccid:'" + iccid + "', " +
			"aid:'" + aid + "', " +
			"atc:'" + atc + "'";
	}

}
