package cn.crudapi.core.enumeration;

public enum EngineEnum {
	INNODB("InnoDB"),

	MYISAM("MyISAM");

	public String getCode() {
		return code;
	}

	EngineEnum(String code) {
		this.setCode(code);
	}

	private void setCode(String code) {
		this.code = code;
	}

	private String code;
}
