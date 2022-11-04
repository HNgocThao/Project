package elec.lab.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


import elec.lab.model.ReportInventory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCTS")
public class Product implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	
	@ManyToOne
	@JoinColumn(name = "categoryId")
	@JsonBackReference
	private Category category;
	
	@Column(length = 200)
	private String image;
	
	@Column(columnDefinition = "nvarchar(100) not null")
	private String name;
	
	@Column(nullable = false)
	private int quantity;
	
	@Column
	private int quantityorder;
	
	@Column(nullable = false)
	private double unitPrice;
	
	@Column(nullable = false)
	private double discount;
	
	@ManyToOne
	@JoinColumn(name = "producerId")
	@JsonBackReference
	private Producer producer;
	
	@Column(columnDefinition = "NTEXT not null")
	private String description;
	
	@Column(columnDefinition = "nvarchar(4000) not null")
	private String brief;
	
	@Temporal(TemporalType.DATE)
	private Date enteredDate;
	
	@Column(nullable = false)
	private short status;
	
	
	
	public Product() {
		
	}



	public Product(Long productId, Category category, String image, String name, int quantity, int quantityorder,
			double unitPrice, double discount, Producer producer, String description, String brief, Date enteredDate,
			short status) {
		super();
		this.productId = productId;
		this.category = category;
		this.image = image;
		this.name = name;
		this.quantity = quantity;
		this.quantityorder = quantityorder;
		this.unitPrice = unitPrice;
		this.discount = discount;
		this.producer = producer;
		this.description = description;
		this.brief = brief;
		this.enteredDate = enteredDate;
		this.status = status;
	}



	public Long getProductId() {
		return productId;
	}



	public void setProductId(Long productId) {
		this.productId = productId;
	}



	public Category getCategory() {
		return category;
	}



	public void setCategory(Category category) {
		this.category = category;
	}



	public String getImage() {
		return image;
	}



	public void setImage(String image) {
		this.image = image;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getQuantity() {
		return quantity;
	}



	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}



	public int getQuantityorder() {
		return quantityorder;
	}



	public void setQuantityorder(int quantityorder) {
		this.quantityorder = quantityorder;
	}



	public double getUnitPrice() {
		return unitPrice;
	}



	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}



	public double getDiscount() {
		return discount;
	}



	public void setDiscount(double discount) {
		this.discount = discount;
	}



	public Producer getProducer() {
		return producer;
	}



	public void setProducer(Producer producer) {
		this.producer = producer;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getBrief() {
		return brief;
	}



	public void setBrief(String brief) {
		this.brief = brief;
	}



	public Date getEnteredDate() {
		return enteredDate;
	}



	public void setEnteredDate(Date enteredDate) {
		this.enteredDate = enteredDate;
	}



	public short getStatus() {
		return status;
	}



	public void setStatus(short status) {
		this.status = status;
	}

	
	
	
	
}
