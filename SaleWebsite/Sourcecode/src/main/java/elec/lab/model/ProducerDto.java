package elec.lab.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProducerDto implements Serializable{

private Long producerId;
	
	@NotEmpty
	@Length(min = 1)
	private String name;
	
	private boolean isEdit = false;

	public ProducerDto() {
		super();
	}

	public ProducerDto(Long producerId, @NotEmpty @Length(min = 1) String name, boolean isEdit) {
		super();
		this.producerId = producerId;
		this.name = name;
		this.isEdit = isEdit;
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

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}
	
	
}
