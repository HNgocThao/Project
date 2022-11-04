package elec.lab.model;

import java.util.Date;

import javax.persistence.Column;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto{
	
	private Long productId;
	private Long categoryId;
	private Long producerId;
	
	@Column(length = 200)
	private String image;
	
	@NotEmpty
	@Length(min = 2)
	private String name;
	
	@NotNull
	@Min(value = 1)
	private int quantity;
	
	@NotNull
	@Min(value = 10000)
	private double unitPrice;
	
	@NotNull
	@Min(value = 0)
	@Max(value = 100)
	private double discount;
	
	
	@NotEmpty
	private String description;
	
	@NotEmpty
	private String brief;
	
	private Date enteredDate;
	private short status;
	private boolean isEdit = false;
	
	public ProductDto() {
		super();
	}

	public ProductDto(Long productId, Long categoryId, Long producerId, String image,
			@NotEmpty @Length(min = 2) String name, @NotNull @Min(1) int quantity,
			@NotNull @Min(10000) double unitPrice, @NotNull @Min(0) @Max(100) double discount,
			@NotEmpty String description, @NotEmpty String brief, Date enteredDate, short status, boolean isEdit) {
		super();
		this.productId = productId;
		this.categoryId = categoryId;
		this.producerId = producerId;
		this.image = image;
		this.name = name;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.discount = discount;
		this.description = description;
		this.brief = brief;
		this.enteredDate = enteredDate;
		this.status = status;
		this.isEdit = isEdit;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getProducerId() {
		return producerId;
	}

	public void setProducerId(Long producerId) {
		this.producerId = producerId;
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

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}
	
	
	
	
}
