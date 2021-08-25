package cn.crudapi.core.dto;


public abstract class BaseDTO {
	private String name;

	private String caption;

	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "BaseDTO [name=" + name + ", caption=" + caption + ", description=" + description + "]";
	}
}
