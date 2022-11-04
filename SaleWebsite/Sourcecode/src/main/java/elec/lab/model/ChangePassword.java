package elec.lab.model;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassword {

	@NotEmpty
	@Length(min = 3)
	private String newPassword;
	
	@NotEmpty
	@Length(min = 3)
	private String confirmPassword;

	public ChangePassword() {
		super();
	}

	public ChangePassword(@NotEmpty @Length(min = 3) String newPassword,
			@NotEmpty @Length(min = 3) String confirmPassword) {
		super();
		this.newPassword = newPassword;
		this.confirmPassword = confirmPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
}
