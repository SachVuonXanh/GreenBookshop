var dropDownProvince;
var dataListDistrict;
var fieldDistrict;

$(document).ready(function() {
	dropDownProvince = $("#province");
	// dataListDistrict =  $("#listDistricts"); // 1.Option Dependent Dropdown $("#listDistricts")
	dataListDistrict =  $("#districtList"); // 2.Option Dependent Dropdown $("#districtList")
	fieldDistrict = $("#district");

	dropDownProvince.on("change", function() {
		loadDistrictsForProvince();
		fieldDistrict.val("").focus();
	});
});

function loadDistrictsForProvince() {
	selectedProvince = $("#province option:selected");
	provinceId = selectedProvince.val();
	url = contextPath + "settings/list_districts_by_province/" + provinceId;

	$.get(url, function(responseJSON) {
		dataListDistrict.empty();

		$.each(responseJSON, function(index, district) {
			$("<option>").val(district.name).text(district.name).appendTo(dataListDistrict);
		});

	}).fail(function() {
		alert('failed to connect to the server!');
	});
}

function checkPasswordMatch(confirmPassword) {
	if (confirmPassword.value != $("#password").val()) {
		confirmPassword.setCustomValidity("Passwords do not match!");
	} else {
		confirmPassword.setCustomValidity("");
	}
}

