package elec.lab.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

	private Long productId;
	private String image;
	private String name;
	private int quantity;
	private int quantityod;
	private double price;
	private Date dateAdd;
	
	public CartItem() {
//		super();
	}

	public CartItem(Long productId, String image, String name, int quantity, int quantityod, double price,
			Date dateAdd) {
		super();
		this.productId = productId;
		this.image = image;
		this.name = name;
		this.quantity = quantity;
		this.quantityod = quantityod;
		this.price = price;
		this.dateAdd = dateAdd;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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

	public int getQuantityod() {
		return quantityod;
	}

	public void setQuantityod(int quantityod) {
		this.quantityod = quantityod;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getDateAdd() {
		return dateAdd;
	}

	public void setDateAdd(Date dateAdd) {
		this.dateAdd = dateAdd;
	}

	
	
	
}
