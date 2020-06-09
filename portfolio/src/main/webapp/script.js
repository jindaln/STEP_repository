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
    var max_comment = document.getElementById("max_comments").value;
    var language = document.getElementById("language").value;
    fetch(`/list_comments?max_comments=${max_comment}&language=${language}`).then(response => response.json()).then((history) => {
        console.log(history);
        const commentsElement = document.getElementById('comments');
        commentsElement.innerHTML= "";
        history.forEach((object) => {
            commentsElement.appendChild(createListElement(object.name, object.comment, object.imageUrl));
        });
    });
}


/** Creates an <li> element containing text. */
function createListElement(name, comment, imgUrl) {
    const divElement = document.createElement("div");
    const imgElement = document.createElement("img");
    imgElement.setAttribute("src", imgUrl);
    imgElement.setAttribute("class", "floated");
    const nameElement = document.createElement("p");
    nameElement.innerText = name;
    const commentElement = document.createElement("p");
    commentElement.innerText = comment;
    divElement.appendChild(imgElement);
    divElement.appendChild(nameElement);
    divElement.appendChild(commentElement);
    return divElement;
}

function fetchBlobstoreUrlAndShowForm() {
  fetch('/blobstore-upload-url')
      .then((response) => {
        return response.text();
      })
      .then((imageUploadUrl) => {
        const messageForm = document.getElementById('comments_form');
        messageForm.action = imageUploadUrl;
      });
}

function deleteMessages(){
    const request = new Request("/delete-data", {method: 'POST'});
    fetch(request).then(getMessage());
}

function createMapSingapore() {
    var directionsService = new google.maps.DirectionsService();
    var directionsRenderer = new google.maps.DirectionsRenderer();
    var contentStringOffice = '<div class="Singapore"> <img src="/images/Singapore.jpg"> Singapore Office </div>';
    var contentStringHome = '<div class="Singapore"> <img src="/images/SingHome.JPG"> Singapore Home </div>';

    const SingHome = {lat: 1.334614, lng: 103.784676};
    const Singapore = {lat: 1.283577, lng: 103.845012};
    const map = new google.maps.Map(
        document.getElementById('mapSG'),
        {center: Singapore, zoom: 16});

    directionsRenderer.setMap(map);
    var infowindowOffice = createInfoWindow(contentStringOffice);
    var infowindowHome = createInfoWindow(contentStringHome);
    var markerOffice = createMarker(Singapore, map);
    var markerHome = createMarker(SingHome, map);
    addListenerToMarker(markerOffice, infowindowOffice, map);
    addListenerToMarker(markerHome, infowindowHome, map);
    calcRoute(SingHome, Singapore, google.maps.TravelMode['WALKING'], directionsService, directionsRenderer);
}

function createMapHongKong() {
    var directionsService = new google.maps.DirectionsService();
    var directionsRenderer = new google.maps.DirectionsRenderer();
    var contentStringOffice = '<div class="HongKong"> <img src="/images/HongKong.JPG"> Hong Kong Office </div>';
    var contentStringHotel = '<div class="HongKong"> <img src="/images/HKHotel.JPG"> Walking to HK Office from Hotel</div>';

    const HKHotel = {lat: 22.287380, lng:114.192221 };
    const HongKong = {lat: 22.285711, lng: 114.190767};
    const map = new google.maps.Map(
      document.getElementById('mapHK'),
      {center: HongKong, zoom: 16});

    directionsRenderer.setMap(map);
    var infowindowOffice = createInfoWindow(contentStringOffice);
    var infowindowHotel = createInfoWindow(contentStringHotel);
    var markerOffice = createMarker(HongKong, map);
    var markerHotel = createMarker(HKHotel, map);
    addListenerToMarker(markerOffice, infowindowOffice, map);
    addListenerToMarker(markerHotel, infowindowHotel, map);
    calcRoute(HKHotel, HongKong, google.maps.TravelMode['WALKING'], directionsService, directionsRenderer);
}

function createInfoWindow(contentString){
    return new google.maps.InfoWindow({
        content: contentString,
        maxWidth: 200
    });
}

function createMarker(position, map){
    return new google.maps.Marker({position: position, animation: 
        google.maps.Animation.DROP, map: map});
}

function addListenerToMarker(marker, infoWindow, map){
    marker.addListener('click', function() {
        infoWindow.open(map, marker);
    });
}

function calcRoute(start, end, travel, directionsService, directionsRenderer) {
  var request = {
    origin: start,
    destination: end,
    travelMode: travel
  };
  console.log(request);
  directionsService.route(request, function(result, status) {
    if (status == 'OK') {
        directionsRenderer.setDirections(result);
        directionsRenderer.setOptions({suppressMarkers: true});
    }
    else{
        console.log("ERROR");
    }
  });
}

function validateForm() {
    var name = document.forms["comments_form"]["name"].value;
    var comment = document.forms["comments_form"]["comment"].value;
    if (!name) {
        alert("Name must be filled out");
        return false;
    }
    if (!comment) {
        alert("Comment must be filled out");
        return false;
    }
    return true; 
}
