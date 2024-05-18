function toggleEdit(inputId) {
  var input = document.getElementById(inputId);
  input.readOnly = !input.readOnly; 
  input.focus(); 
}