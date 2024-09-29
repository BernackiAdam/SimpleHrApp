const addPhaseFormCheckbox = document.getElementById('addPhaseCheckbox');
const addPhaseForm = document.getElementById('addPhaseForm');

addPhaseFormCheckbox.addEventListener('change', function(){
    if(this.checked){
        addPhaseForm.style.display='block';
    }
    else{
        addPhaseForm.style.display='none';
    }
})