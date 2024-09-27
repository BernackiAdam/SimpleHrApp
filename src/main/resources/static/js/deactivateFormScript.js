function toggleDeactivationForm(){
    const deactivationForm = document.getElementById('deactivationForm');

    if(deactivationForm.style.display === 'none'){
        deactivationForm.style.display = 'block';
    }
    else{
        deactivationForm.style.display = 'none';
    }
}
document.getElementById('deactivateBtn').addEventListener('click', toggleDeactivationForm);
document.getElementById('closeForm').addEventListener('click', toggleDeactivationForm);

const dateField = document.getElementById('reactivationDateInput');
const indefCheckbox = document.getElementById('indefinitelyCheckbox');

indefCheckbox.addEventListener('change', function(){
    if(this.checked){
        dateField.disabled = true;
        dateField.removeAttribute('required');
        dateField.value="";
    }
    else{
        dateField.disabled = false;
        dateField.setAttribute('required', '');
    }
});

const deactivationField = document.getElementById('deactivationDate');
const nowCheckbox = document.getElementById('nowCheckbox');

nowCheckbox.addEventListener('change', function(){
    if(this.checked){
        deactivationField.disabled = true;
        deactivationField.removeAttribute('required');
        dateField.value="";
    }
    else{
        deactivationField.disabled = false;
        deactivationField.setAttribute('required', '');
    }
})