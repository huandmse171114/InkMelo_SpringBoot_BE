package com.inkmelo.ghn;

import java.util.EnumSet;
import java.util.Set;

public enum OrderTrackingStatus {
	READY_TO_PICK("ready_to_pick", "Mới tạo đơn hàng"),
	PICKING("PICKING", "Nhân viên đang lấy hàng"),
	CANCEL("CANCEL", "Hủy đơn hàng"),
	MONEY_COLLECT_PICKING("MONEY_COLLECT_PICKING", "Đang thu tiền người gửi"),
	PICKED("PICKED", "Nhân viên đã lấy hàng"),
	STORING("STORING", "Hàng đang nằm ở kho"),
	TRANSPORTING("TRANSPORTING", "Đang luân chuyển hàng"),
	SORTING("SORTING", "Đang phân loại hàng hóa"),
	DELIVERING("DELIVERING", "Nhân viên đang giao cho người nhận"),
	MONEY_COLLECT_DELIVERING("MONEY_COLLECT_DELIVERING", "Nhân viên đang thu tiền người nhận"),
	DELIVERED("DELIVERED", "Nhân viên đã giao hàng thành công"),
	DELIVERY_FAIL("DELIVERY_FAIL", "Nhân viên giao hàng thất bại"),
	WAITING_TO_RETURN("WAITING_TO_RETURN", "Đang đợi hàng trả về cho người gửi"),
	RETURN("RETURN", "Trả hàng");
	
	private final String value;
	private final String description;
	
	public static Set<OrderTrackingStatus> allStatus = EnumSet.of(
			READY_TO_PICK, PICKING, CANCEL, MONEY_COLLECT_PICKING, PICKED,
			STORING, TRANSPORTING, SORTING, DELIVERING, MONEY_COLLECT_DELIVERING,
			DELIVERED, DELIVERY_FAIL, WAITING_TO_RETURN, RETURN);
	
	private OrderTrackingStatus(String value, String description) {
		this.value = value;
		this.description = description;
	}

	public String getValue() {
		return this.value;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public static OrderTrackingStatus fromValue(String value) {
		for (OrderTrackingStatus b : OrderTrackingStatus.values()) {
			if (b.value.equalsIgnoreCase(value)) {
				return b;
			}
		}
		
		throw new IllegalArgumentException("Giá trị của chế độ gói tài nguyên sách không tồn tại.");
	}
}
