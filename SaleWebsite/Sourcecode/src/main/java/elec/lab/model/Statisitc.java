package elec.lab.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statisitc implements Serializable{

	private Serializable sum;
	private double amount;
	private Date statisDate;
	
	public Statisitc() {
		super();
	}

	public Statisitc(Serializable sum, double amount, Date statisDate) {
		super();
		this.sum = sum;
		this.amount = amount;
		this.statisDate = statisDate;
	}

	public Serializable getSum() {
		return sum;
	}

	public void setSum(Serializable sum) {
		this.sum = sum;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getStatisDate() {
		return statisDate;
	}

	public void setStatisDate(Date statisDate) {
		this.statisDate = statisDate;
	}

	

	

	
	
	
}
