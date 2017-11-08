package net.xapxinh.center.shared.dto;

import java.util.HashMap;

public class HashMapStringDto extends HashMap<String, String> implements RpcResponseDto {

	private static final long serialVersionUID = 1L;

	public static final String COMPANY = "Company";
	public static final String PHONE = "Phone";
	public static final String EMAIL = "Email";
	public static final String ADDRESS = "Address";
}
