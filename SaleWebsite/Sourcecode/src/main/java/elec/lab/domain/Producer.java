package elec.lab.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCERS")
public class Producer implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long producerId;
	
	@Column(name = "producer_name", length = 100, columnDefinition = "nvarchar(100) not null")
	private String name;
	
	@OneToMany(mappedBy = "producer", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<Product> products;

	public Producer() {
		super();
	}

	public Producer(Long producerId, String name, Set<Product> products) {
		super();
		this.producerId = producerId;
		this.name = name;
		this.products = products;
	}

	public Long getProducerId() {
		return producerId;
	}

	public void setProducerId(Long producerId) {
		this.producerId = producerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
	
}
