//api.js
//takes input from search.html, then sends the information to Server.java
//when a response is recieved from the server, format the search results and
//sends it back to search.html to display on the webpage

//this file uses jQuery 3.1
//this can be found at jquery.com
//we used this library at blocks of code beginning at lines 37 and 69

$(document).ready(function() {
  var item, tile, author, publisher, bookLink, bookImg;
  var outputList = document.getElementById("list-output");
  var searchData;

  //listener for search title button
  $("#searchTitle").click(function() {
    //calls search function for title
    search("title");
   });

   //listener for search author button
   $("#searchAuthor").click(function() {
     //calls search function for author
     search("author");
    });

    //listener for search genre button
    $("#searchGenre").click(function() {
      //calls search function for genre
      search("genre");
     });

     //listener for trending button
    $("#trending").click(function() {
       outputList.innerHTML = ""; //empty html output
       document.body.style.backgroundImage = "url('')";
          $.ajax({
             url: `http://localhost:8010/test?search?trending`, //call to the java server with trending as search criteria
             dataType: "json", //data is returned as a json
              success: function(response) {
               console.log(response)
               if (response.result.length == 0) {
                 alert("There are no books with a rating of 5")
               }
               else {
                 $("#title").animate({'margin-top': '5px'}, 1000); //search box animation
                 $(".book-list").css("visibility", "visible");
                 displayResults(response);
               }
             },
             error: function () {
               //alerts the user if the search doe not work
               alert("Something went wrong.. <br>"+"Try again! <br>"+"Check internet or database connection");
             }
           });
         $("#search-box").val(""); //clear search box
      });

  function search(searchType) {
    outputList.innerHTML = ""; //empty html output
    document.body.style.backgroundImage = "url('')";
     searchData = $("#search-box").val();
     //handling empty search input field
     if(searchData === "" || searchData === null) {
       //alerts the user if they search for nothing
       alert("Search term can not be empty!");
     }
    else {
       $.ajax({
         //call to java server that includes data from searchbar and search type
          url: `http://localhost:8010/test?search?${searchType}?${searchData}`,
          dataType: "json",//data is returned as a json
          success: function(response) {
            console.log(response)
            if (response.result.length == 0) {
              alert("No results could be found for this search")
            }
            else {
              $("#title").animate({'margin-top': '5px'}, 1000); //search box animation
              $(".book-list").css("visibility", "visible");
              displayResults(response);
            }
          },
          error: function () {
            //alerts the user if the search doe not work
            alert("Something went wrong.. <br>"+"Try again! <br>"+"Check internet or database connection");
          }
        });
      }
      $("#search-box").val(""); //clear search box
  }

   //function to display result in search.html
   function displayResults(response) {
      for (var i = 0; i < response.result.length; i+=1) {
        item = response.result[i];
        title1 = item.title;
        authorfName1 = item.authorfName;
        authorlName1 = item.authorlName;
        genre1 = item.genre;
        isbn1 = item.isbn;
        rating1 = item.rating;

        // each search result has its own div, so it is on its own row
        outputList.innerHTML += '<div class="row mt-4 justify-content-center">' +
                                formatOutput(title1, authorfName1, authorlName1, genre1, isbn1, rating1) +
                                '</div>';

        console.log(outputList);
      }
   }

   //search result card formatter
   function formatOutput(title, authorfName, authorlName, genre, bookIsbn, rating) {
     //creates the html format for the cards the books appear on
     var htmlCard = `<div class="col-lg-6">
       <div class="card" style="">
         <div class="row no-gutters">
           <div class="col-md-8">
             <div class="card-body">
               <h5 class="card-title">${title}</h5>
               <p class="card-text">Author: ${authorfName} ${authorlName}</p>
               <p class="card-text">Genre: ${genre}</p>
               <p class="card-text">Rating: ${rating}</p>
               <a target="_blank" href="bookIsbn" class="btn btn-secondary">View Book</a>
               <a target="_blank" href="" class = "btn btn-secondary">Save Book</a>
             </div>
           </div>
         </div>
       </div>
     </div>`
     return htmlCard;
   }
});
