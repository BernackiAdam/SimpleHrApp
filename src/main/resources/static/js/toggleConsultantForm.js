function toggleConsultantForm(){
    var checkbox = document.getElementById('projectConsultantCheckbox');
    var consultantForm = document.getElementById('projectConsultantForm');

    if(checkbox.checked){
        consultantForm.style.display = 'block';
    }
    else{
        consultantForm.style.display = 'none'
    }
}