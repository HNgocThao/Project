package elec.lab.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FillterDayByDay {

	private Date toDate;
    private Date fromDate;
	public FillterDayByDay() {
		super();
	}
	public FillterDayByDay(Date toDate, Date fromDate) {
		super();
		this.toDate = toDate;
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	
	
    
    
}
