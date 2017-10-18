var householdNode = document.getElementsByClassName('household')[0];
var debugNode = document.getElementsByClassName('debug')[0];
var addNode = document.getElementsByClassName('add')[0];
var submitNode = document.getElementsByTagName('button')[1];

// Add 'type button' to avoid refresh on return from add event handler.
addNode.setAttribute('type', 'button');

// Remove 'type submit' & add 'type button' to avoid refresh on return 
// from submit event handler.
submitNode.removeAttribute('type', 'submit');
submitNode.setAttribute('type', 'button');

// Override 'display none', so household submission can be displayed.
debugNode.style.display = 'block';

// Establish event handler to add a Person to a Household.
addNode.addEventListener('click', addPerson, false);

// Establish event handler to submit household list to server
submitNode.addEventListener('click', submitHousehold, false);

// Adds new person to household list along with per-person delete button.
function addPerson() {

  var person = {};
  age = document.getElementsByName('age')[0].value;
  if (age == "") {
    alert("Age must be entered");
    return false;
  }
  if (age <= 0) {
    alert("Age must be greater than 0");
    return false;
  }
  person['age'] = age;

  rel = document.getElementsByName('rel')[0].value;
  if (rel == "") {
    alert("Relationship must be entered");
    return false;
  }
  person['rel'] = rel;

  person['smoker'] = document.getElementsByName('smoker')[0].checked;

  personNode = document.createTextNode(JSON.stringify(person));

  var deleteNode = document.createElement('button');
  deleteNode.textContent='delete';

  var itemNode = document.createElement('li');
  itemNode.appendChild(personNode); 
  itemNode.appendChild(deleteNode);

  householdNode.appendChild(itemNode);

  // Estabish event handler to delete individual entries
  itemNode.addEventListener('click', deletePerson, false);

  // Deletes an entry in the household list
  function deletePerson() {
    householdNode.removeChild(itemNode);
  }
}

// Submits household list to server.  At this time, the list is only
// put into the debug DOM element, then displayed.
function submitHousehold() {
  var household = "";

  var itemNodes = householdNode.childNodes;
  for (var itemNode of itemNodes) {
    personNode = itemNode.childNodes[0];
    household += personNode.textContent;
    household += "\n";
  }

  debugNode.innerText = household;
}