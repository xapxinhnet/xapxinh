package net.xapxinh.center.shared.dto;

public class StringSerializableDto extends SerializableDto {

	private static final long serialVersionUID = 1L;

	private String string;

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}
}
