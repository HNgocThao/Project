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
public class CategoryDto implements Serializable{

	private Long categoryId;
	
	@NotEmpty
	@Length(min = 1)
	private String name;
	
	private boolean isEdit = false;

	public CategoryDto() {
		super();
	}

	public CategoryDto(Long categoryId, @NotEmpty @Length(min = 1) String name, boolean isEdit) {
		super();
		this.categoryId = categoryId;
		this.name = name;
		this.isEdit = isEdit;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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
