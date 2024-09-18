function toggleSearchEmployee(){
    var searchByField = document.getElementById('searchBy').value;

    document.getElementById('fullNameField').style.display = 'none';
    document.getElementById('emailField').style.display = 'none';
    document.getElementById('telephoneNumberField').style.display = 'none';
    document.getElementById('seniorityField').style.display = 'none';
    document.getElementById('positionField').style.display = 'none';
    document.getElementById('fullPositionField').style.display = 'none';


    if(searchByField === 'Full Name'){
        document.getElementById('fullNameField').style.display = 'flex';
    } else if(searchByField == 'Email'){
        document.getElementById('emailField').style.display = 'block';
    } else if(searchByField == 'Telephone Number'){
        document.getElementById('telephoneNumberField').style.display = 'block';
    } else if(searchByField == 'Seniority'){
        document.getElementById('seniorityField').style.display = 'block';
    } else if(searchByField == 'Position'){
        document.getElementById('positionField').style.display = 'block';
   } else if(searchByField == 'Full Position'){
       document.getElementById('fullPositionField').style.display = 'flex';
   }
}