package demos.mybatis;

public class User {

	private int id;
	private String name;
	private String loginName;
	private String email;

	public String getEmail() {
		return email;
	}

	public int getId() {
		return id;
	}

	public String getLoginName() {
		return loginName;
	}

	public String getName() {
		return name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setName(String name) {
		this.name = name;
	}

}
