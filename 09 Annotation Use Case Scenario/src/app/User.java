package app;

@Entity(value="user1")
public class User {
	
	@Field(columnName="id", isKey=true)
	private Long id;
	
	@Field
	private String name;
	
	@Field
	private String password; 
	
	public User(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}
	
}
