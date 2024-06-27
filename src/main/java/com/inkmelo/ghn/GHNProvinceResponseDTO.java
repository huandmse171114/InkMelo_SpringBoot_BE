package com.inkmelo.ghn;

import java.util.List;

public record GHNProvinceResponseDTO(
			Integer code,
			String message,
			List<GHNProvince> data
		) {
}
