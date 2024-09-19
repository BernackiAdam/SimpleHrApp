function toggleSearchProject(){
    var searchByField = document.getElementById('searchBy').value;

    document.getElementById('titleField').style.display='none';
    document.getElementById('typeField').style.display='none';
    document.getElementById('currentPhaseField').style.display='none';

    if(searchByField=='Title'){
        document.getElementById('titleField').style.display='block';
    } else if(searchByField=='Type'){
        document.getElementById('typeField').style.display='block';
    } else if(searchByField=='Current Phase'){
        document.getElementById('currentPhaseField').style.display='block';
    }
}

window.onload = function(){
    toggleSearchProject();
}