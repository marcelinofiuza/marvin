package br.com.resvut42.marvin.enums;

/****************************************************************************
 * Enum lista de dias
 * 
 * @author Bob-Odin - 02/04/2017
 ****************************************************************************/
public enum DiasMes {

	D01("01"),
	D02("02"),
	D03("03"),
	D04("04"),
	D05("05"),
	D06("06"),
	D07("07"),
	D08("08"),
	D09("09"),
	D10("10"),
	D11("11"),
	D12("12"),
	D13("13"),
	D14("14"),
	D15("15"),
	D16("16"),
	D17("17"),
	D18("18"),
	D19("19"),
	D20("20"),
	D21("21"),
	D22("22"),
	D23("23"),
	D24("24"),
	D25("25"),
	D26("26"),
	D27("27"),
	D28("28"),
	D29("29"),
	D30("30"),
	D31("31");
	
	private final String dia;
	
	private DiasMes(String dia){
		this.dia = dia;
	}
	
	public String getDia(){
		return this.dia;
	}
	
}
