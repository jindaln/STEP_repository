// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.S
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
      ['Hello!', '¡Hola!', '你好！', 'Bonjour!', "Salve!", "नमस्ते!", "سلام"];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

function getMessage(){
    fetch('/data').then(response => response.json()).then((history) => {
        const historyEl = document.getElementById('history');
        console.log("This is history: ", history);
        history.forEach((object) => {
            historyEl.appendChild(createListElement(object.name + " : " + object.comment));
        });
    });
}

/** Creates an <li> element containing text. */
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}

function validateForm() {
  var name = document.forms["comments_form"]["name"].value;
  var comment = document.forms["comments_form"]["comment"].value;
  if (name == "") {
    alert("Name must be filled out");
    return false;
  }
  if (comment == "") {
    alert("Comment must be filled out");
    return false;
  }
}