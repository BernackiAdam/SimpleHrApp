function toggleSubmitButton(){
    var employeeIdRadioButton = document.querySelector('input[name="employeeId"]:checked');
    var roleSelect = document.getElementById('roleSelect').value;

    document.getElementById('submitButton').style.display= 'none';

    if(employeeIdRadioButton.checked && roleSelect != ''){
        document.getElementById('submitButton').style.display='block';
    }
}

function selectEmployee(employeeId){
    document.getElementById('employeeInput').value = employeeId;
    toggleSubmitButton();
}

