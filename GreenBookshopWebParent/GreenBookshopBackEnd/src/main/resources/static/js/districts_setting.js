var buttonLoad4Districts;
var dropDownProvince4Districts;
var dropDownDistricts;
var buttonAddDistrict;
var buttonUpdateDistrict;
var buttonDeleteDistrict;
var labelDistrictName;
var fieldDistrictName;

$(document).ready(function() {
	buttonLoad4Districts = $("#buttonLoadProvincesForDistricts");
	dropDownProvince4Districts = $("#dropDownProvincesForDistricts");
	dropDownDistricts = $("#dropDownDistricts");
	buttonAddDistrict = $("#buttonAddDistrict");
	buttonUpdateDistrict = $("#buttonUpdateDistrict");
	buttonDeleteDistrict = $("#buttonDeleteDistrict");
	labelDistrictName = $("#labelDistrictName");
	fieldDistrictName = $("#fieldDistrictName");
	
	fieldDistrictName.prop("disabled", true);

	buttonLoad4Districts.click(function() {
		loadProvinces4Districts();
	});

	dropDownProvince4Districts.on("change", function() {
		loadDistricts4Province();
	});

	dropDownDistricts.on("change", function() {
		changeFormDistrictToSelectedDistrict();
	});

	buttonAddDistrict.click(function() {
		if (buttonAddDistrict.val() == "Add") {
			addDistrict();
		} else {
			changeFormDistrictToNew();
		}
	});

	buttonUpdateDistrict.click(function() {
		updateDistrict();
	});

	buttonDeleteDistrict.click(function() {
		deleteDistrict();
	});
});

function deleteDistrict() {
	districtId = dropDownDistricts.val();

	url = contextPath + "districts/delete/" + districtId;

	$.ajax({
		type: 'DELETE',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function() {
		$("#dropDownDistricts option[value='" + districtId + "']").remove();
		changeFormDistrictToNew();
		showToastMessage("Quận/huyện đã bị xóa");
	}).fail(function() {
		showToastMessage("LỖI: Không thể kết nối với máy chủ hoặc máy chủ gặp lỗi");
	});		
}

function updateDistrict() {
	
	if (!validateFormDistrict()) return;
	
	url = contextPath + "districts/save";
	districtId = dropDownDistricts.val();
	districtName = fieldDistrictName.val();

	selectedProvince = $("#dropDownProvincesForDistricts option:selected");
	provinceId = selectedProvince.val();
	provinceName = selectedProvince.text();

	jsonData = {id: districtId, name: districtName, province: {id: provinceId, name: provinceName}};

	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(districtId) {
		$("#dropDownDistricts option:selected").text(districtName);
		showToastMessage("Quận/huyện đã được cập nhật");
		changeFormDistrictToNew();
	}).fail(function() {
		showToastMessage("LỖI: Không thể kết nối với máy chủ hoặc máy chủ gặp lỗi");
	});	
}

function addDistrict() {
	
	if (!validateFormDistrict()) return;
	
	url = contextPath + "districts/save";
	districtName = fieldDistrictName.val();

	selectedProvince = $("#dropDownProvincesForDistricts option:selected");
	provinceId = selectedProvince.val();
	provinceName = selectedProvince.text();

	jsonData = {name: districtName, province: {id: provinceId, name: provinceName}};

	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(districtId) {
		selectNewlyAddedDistrict(districtId, districtName);
		showToastMessage("Quận/huyện đã được thêm vào");
	}).fail(function() {
		showToastMessage("LỖI: Không thể kết nối với máy chủ hoặc máy chủ gặp lỗi");
	});

}

function selectNewlyAddedDistrict(districtId, districtName) {
	$("<option>").val(districtId).text(districtName).appendTo(dropDownDistricts);

	$("#dropDownDistricts option[value='" + districtId + "']").prop("selected", true);

	fieldDistrictName.val("").focus();
}

function changeFormDistrictToNew() {
	buttonAddDistrict.val("Add");
	labelDistrictName.text("Tên quận/huyện:");

	buttonUpdateDistrict.prop("disabled", true);
	buttonDeleteDistrict.prop("disabled", true);
	
	fieldDistrictName.prop("disabled", false);
	
	fieldDistrictName.val("").focus();	
}

function changeFormDistrictToSelectedDistrict() {
	buttonAddDistrict.prop("value", "New");
	buttonUpdateDistrict.prop("disabled", false);
	buttonDeleteDistrict.prop("disabled", false);
	
	fieldDistrictName.prop("disabled", false);

	labelDistrictName.text("Lựa chọn quận/huyện:");

	selectedDistrictName = $("#dropDownDistricts option:selected").text();
	fieldDistrictName.val(selectedDistrictName);

}

function loadDistricts4Province() {
	selectedProvince = $("#dropDownProvincesForDistricts option:selected");
	provinceId = selectedProvince.val();
	url = contextPath + "districts/list_by_province/" + provinceId;

	$.get(url, function(responseJSON) {
		dropDownDistricts.empty();

		$.each(responseJSON, function(index, district) {
			$("<option>").val(district.id).text(district.name).appendTo(dropDownDistricts);
		});

	}).done(function() {
		changeFormDistrictToNew();
		showToastMessage("Tất cả các quận/huyện đã được nạp cho thành phố " + selectedProvince.text());
	}).fail(function() {
		showToastMessage("LỖI: Không thể kết nối với máy chủ hoặc máy chủ gặp lỗi");
	});	
}

function loadProvinces4Districts() {
	url = contextPath + "provinces/list";
	$.get(url, function(responseJSON) {
		dropDownProvince4Districts.empty();

		$.each(responseJSON, function(index, province) {
			$("<option>").val(province.id).text(province.name).appendTo(dropDownProvince4Districts);
		});

	}).done(function() {
		buttonLoad4Districts.val("Làm mới Danh sách thành phố");
		showToastMessage("Tất cả các thành phố đã được nạp");
	}).fail(function() {
		showToastMessage("LỖI: Không thể kết nối với máy chủ hoặc máy chủ gặp lỗi");
	});
} 

function validateFormDistrict() {
	formDistrict = document.getElementById("formDistrict");
	if (!formDistrict.checkValidity()) {
		formDistrict.reportValidity();
		return false;
	}	

	return true;
}

function checkUnique() {

	console.log("checkUnique is working");
	
	districtName = $("#fieldDistrictName").val();
	
	console.log(districtName);
	
	csrfValue = $("input[name='_csrf']").val();
	
	jsonData = {name: districtName, _csrf: csrfValue};
	
	checkUniqueUrl = contextPath + "districts/check_unique";
	
	$.ajax({
		type: 'POST',
		url: checkUniqueUrl,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(response) {
		if (response == "OK") {
			return true;
		} else if (response == "Duplicate") {
			showToastMessage("Quận/huyện đã được thêm vào :  " + districtName);
			return false;	
		} else {
			showToastMessage("Phản hồi không xác định từ máy chủ");
			return false;
		}
	}).fail(function() {
		showToastMessage("LỖI: Không thể kết nối với máy chủ hoặc máy chủ gặp lỗi");
		return false;
	});
	
	return false;
}	 