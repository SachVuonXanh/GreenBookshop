var buttonLoad;
var dropDownProvince;
var buttonAddProvince;
var buttonUpdateProvince;
var buttonDeleteProvince;
var labelProvinceName;
var fieldProvinceName;
var fieldProvinceCode;

$(document).ready(function() {
	buttonLoad = $("#buttonLoadProvinces");
	dropDownProvince = $("#dropDownProvinces");
	buttonAddProvince = $("#buttonAddProvince");
	buttonUpdateProvince = $("#buttonUpdateProvince");
	buttonDeleteProvince = $("#buttonDeleteProvince");
	labelProvinceName = $("#labelProvinceName");
	fieldProvinceName = $("#fieldProvinceName");
	fieldProvinceCode = $("#fieldProvinceCode");
	
	fieldProvinceName.prop("disabled", true);
	fieldProvinceCode.prop("disabled", true);

	buttonLoad.click(function() {
		loadProvinces();
	});

	dropDownProvince.on("change", function() {
		changeFormDistrictToSelectedProvince();
	});

	buttonAddProvince.click(function() {
		if (buttonAddProvince.val() == "Add") {
			// if(checkUnique()){
			// 	addProvince();
			// }
			addProvince();
		} else {
			changeFormDistrictToNewProvince();
		}
	});

	buttonUpdateProvince.click(function() {
		updateProvince();
	});

	buttonDeleteProvince.click(function() {
		deleteProvince();
	});
});

function deleteProvince() {
	optionValue = dropDownProvince.val();
	provinceId = optionValue.split("-")[0];

	url = contextPath + "provinces/delete/" + provinceId;

	$.ajax({
		type: 'DELETE',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function() {
		$("#dropDownProvinces option[value='" + optionValue + "']").remove();
		changeFormDistrictToNewProvince();
		showToastMessage("Thành phố đã bị xóa");
	}).fail(function() {
		showToastMessage("LỖI: Không thể kết nối với máy chủ hoặc máy chủ gặp lỗi");
	});		
}

function updateProvince() {
	
	if (!validateFormProvince()) return;
	
	url = contextPath + "provinces/save";
	provinceName = fieldProvinceName.val();
	provinceCode = fieldProvinceCode.val();

	provinceId = dropDownProvince.val().split("-")[0];

	jsonData = {id: provinceId, name: provinceName, code: provinceCode};

	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(provinceId) {
		$("#dropDownProvinces option:selected").val(provinceId + "-" + provinceCode);
		$("#dropDownProvinces option:selected").text(provinceName);
		showToastMessage("thành phố đã được cập nhật");

		changeFormDistrictToNewProvince();
	}).fail(function() {
		showToastMessage("LỖI: Không thể kết nối với máy chủ hoặc máy chủ gặp lỗi");
	});	
}

function addProvince() {
	
	if (!validateFormProvince()) return;
	
	url = contextPath + "provinces/save";
	provinceName = fieldProvinceName.val();
	provinceCode = fieldProvinceCode.val();
	jsonData = {name: provinceName, code: provinceCode};

	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(provinceId) {
		selectNewlyAddedProvince(provinceId, provinceCode, provinceName);
		showToastMessage("thành phố mới đã được thêm vào");
	}).fail(function() {
		showToastMessage("LỖI: Không thể kết nối với máy chủ hoặc máy chủ gặp lỗi");
	});

}

function selectNewlyAddedProvince(provinceId, provinceCode, provinceName) {
	optionValue = provinceId + "-" + provinceCode;
	$("<option>").val(optionValue).text(provinceName).appendTo(dropDownProvince);

	$("#dropDownProvinces option[value='" + optionValue + "']").prop("selected", true);

	fieldProvinceCode.val("");
	fieldProvinceName.val("").focus();
}

function changeFormDistrictToNewProvince() {
	buttonAddProvince.val("Add");
	labelProvinceName.text("Tên thành phố:");

	fieldProvinceName.prop("disabled", false);
	fieldProvinceCode.prop("disabled", false);

	buttonUpdateProvince.prop("disabled", false);
	buttonDeleteProvince.prop("disabled", false);

	fieldProvinceCode.val("");
	fieldProvinceName.val("").focus();	
}

function changeFormDistrictToSelectedProvince() {
	buttonAddProvince.prop("value", "New");
	
	fieldProvinceName.prop("disabled", false);
	fieldProvinceCode.prop("disabled", false);
	
	buttonUpdateProvince.prop("disabled", false);
	buttonDeleteProvince.prop("disabled", false);

	labelProvinceName.text("Lựa chọn thành phố:");

	selectedProvinceName = $("#dropDownProvinces option:selected").text();
	fieldProvinceName.val(selectedProvinceName);

	provinceCode = dropDownProvince.val().split("-")[1];
	fieldProvinceCode.val(provinceCode);

}

function loadProvinces() {
	url = contextPath + "provinces/list";
	$.get(url, function(responseJSON) {
		dropDownProvince.empty();
		
		$.each(responseJSON, function(index, province) {
			optionValue = province.id + "-" + province.code;
			$("<option>").val(optionValue).text(province.name).appendTo(dropDownProvince);
		});
		
	}).done(function() {
		buttonLoad.val("Làm mới Danh sách thành phố");
		showToastMessage("Tất cả các thành phố đã được nạp");
	}).fail(function() {
		showToastMessage("LỖI: Không thể kết nối với máy chủ hoặc máy chủ gặp lỗi");
	});
}

function showToastMessage(message) {
	$("#toastMessage").text(message);
	$(".toast").toast('show');
}

function validateFormProvince() {
	formProvince = document.getElementById("formProvince");
	if (!formProvince.checkValidity()) {
		formProvince.reportValidity();
		return false;
	}	

	return true;
}


function checkUnique() {

	console.log("checkUnique is working");
	
	provinceName = $("#fieldProvinceName").val();
	
	console.log(provinceName);
	
	csrfValue = $("input[name='_csrf']").val();
	
	jsonData = {name: provinceName, _csrf: csrfValue};
	
	checkUniqueUrl = contextPath + "provinces/check_unique";
	
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
			showToastMessage("Thành phố đã được thêm vào :  " + provinceName);
			return false;	
		} else {
			showToastMessage("Phản hồi không xác định từ hệ phục vụ");
			return false;
		}
	}).fail(function() {
		showToastMessage("ERROR: Could not connect to server or server encountered an error");
		return false;
	});
	
	return false;
}	 