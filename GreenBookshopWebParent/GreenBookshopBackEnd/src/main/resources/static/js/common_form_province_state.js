var dropdownProvinces;
var dropdownDistricts;

$(document).ready(function() {
	dropdownProvinces = $("#province");
	dropdownDistricts = $("#listDistricts");

	dropdownProvinces.on("change", function() {
		loadDistricts4Province();
		$("#district").val("").focus();
	});	

	loadDistricts4Province();

	$('#buttonCancel').click(function() { 
		window.location = moduleURL;	
	});		
});

function loadDistricts4Province() {
	selectedProvince = $("#province option:selected");
	provinceId = selectedProvince.val();

	url = contextPath + "districts/list_by_province/" + provinceId;

	$.get(url, function(responseJson) {
		dropdownDistricts.empty();

		$.each(responseJson, function(index, district) {
			$("<option>").val(district.name).text(district.name).appendTo(dropdownDistricts);
		});
	}).fail(function() {
		showErrorModal("Error loading districts/townships for the selected province.");
	})	
}	