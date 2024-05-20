function toggleEdit(inputId) {
  var input = document.getElementById(inputId);
  input.readOnly = !input.readOnly; 
  input.focus(); 
}

function actualizarLabel(inputId) {
    var inputValue = document.getElementById(inputId).value;
    document.getElementById(inputId.replace("Input", "Label")).innerText = inputValue;
}
