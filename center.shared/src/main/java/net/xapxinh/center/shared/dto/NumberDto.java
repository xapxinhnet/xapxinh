package net.xapxinh.center.shared.dto;



public class NumberDto implements RpcResponseDto {

	private Number number;
	
	private static final long serialVersionUID = 1L;

	public Number getNumber() {
		return number;
	}

	public void setNumber(Number number) {
		this.number = number;
	}
}
