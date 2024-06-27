package com.inkmelo.ghn;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GHNProvince {
	private Integer provinceID;
	
	private String provinceName;
	
	private Integer countryID;
	
	private String code;
	
	private List<String> nameExtension;
	
	private Integer isEnable;
	
	private Integer regionID;
	
	private Integer regionCPN;
	
	private Long updatedBy;
	
	private Timestamp createdAt;
	
	private Timestamp updatedAt;
	
	private boolean canUpdateCOD;
	
	private Integer status;
	
	private String updatedIP;
	
	private Long updatedEmployee;
	
	private String updatedSource;
	
	private Timestamp updatedDate;
	
	@Override
	public String toString() {
		return provinceName + "-" + provinceID;
	}
}
