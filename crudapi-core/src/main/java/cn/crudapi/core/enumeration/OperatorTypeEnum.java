package cn.crudapi.core.enumeration;

//EQ 就是 EQUAL等于 
//NE就是 NOT EQUAL不等于 
//GT 就是 GREATER THAN大于　 
//LT 就是 LESS THAN小于 
//GE 就是 GREATER THAN OR EQUAL 大于等于 
//LE 就是 LESS THAN OR EQUAL 小于等于
public enum OperatorTypeEnum {
	EQ,
	NE,
	LIKE,
	MLIKE,
	IN,
	INSELECT,
	SEARCH,
	GE,
	GT,
	LE,
	LT,
	BETWEEN,
	ISNULL,
	ISNOTNULL
}
