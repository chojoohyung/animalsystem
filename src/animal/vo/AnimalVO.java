package animal.vo;

public class AnimalVO {
	private int id;
	private int age;
	private String specName;
	private String sex;
	private String keeper;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getspecName() {
		return specName;
	}

	public void setspecName(String specName) {
		this.specName = specName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getKeeper() {
		return keeper;
	}

	public void setKeeper(String keeper) {
		this.keeper = keeper;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "AnimalVO [id=" + id + ", age=" + age + ", specName=" + specName + ", sex=" + sex + ", keeper=" + keeper
				+ ", name=" + name + "]";
	}

}
