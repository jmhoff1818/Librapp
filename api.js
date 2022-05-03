$(document).ready(function() {
  var item, tile, author, publisher, bookLink, bookImg;
  var outputList = document.getElementById("list-output");
  var bookUrl = "https://www.googleapis.com/books/v1/volumes?q=";
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
             url: `http://localhost:8010/test?search?trending`,
             dataType: "json",
             success: function(response) {
               console.log(response)
               if (response.totalItems == 0) {
                 alert("No results could be found for this search")
               }
               else {
                 $("#title").animate({'margin-top': '5px'}, 1000); //search box animation
                 $(".book-list").css("visibility", "visible");
                 displayResults(response);
               }
             },
             error: function () {
               alert("Something went wrong.. <br>"+"Try again!");
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
       displayError();
     }
    else {
       $.ajax({
          url: `http://localhost:8010/test?search?${searchType}?${searchData}`,
          dataType: "json",
          success: function(response) {
            console.log(response)
            if (response.totalItems == 0) {
              alert("No results could be found for this search")
            }
            else {
              $("#title").animate({'margin-top': '5px'}, 1000); //search box animation
              $(".book-list").css("visibility", "visible");
              displayResults(response);
            }
          },
          error: function () {
            alert("Something went wrong.. <br>"+"Try again!");
          }
        });
      }
      $("#search-box").val(""); //clear search box
  }

   //function to display result in search.html
   function displayResults(response) {
      for (var i = 0; i < response.result.length; i+=2) {
        item = response.result[i];
        title1 = item.title;
        authorfName1 = item.authorfName;
        authorlName1 = item.authorlName;
        genre1 = item.genre;
        isbn1 = item.isbn;

        item2 = response.result[i+1];
        title2 = item2.title;
        authorfName2 = item2.authorFName;
        authorlName2 = item2.authorLName;
        genre2 = item2.genre;
        isbn2 = item2.isbn;

        // in production code, item.text should have the HTML entities escaped.
        outputList.innerHTML += '<div class="row mt-4">' +
                                formatOutput(title1, authorfName1, authorlName1, genre1, isbn1) +
                                formatOutput(title2, authorfName2, authorlName2, genre2, isbn2) +
                                '</div>';

        console.log(outputList);
      }
   }

   //search result card formatter
   function formatOutput(title, authorfName, authorlName, genre, bookIsbn) {
     // console.log(title + ""+ author +" "+ publisher +" "+ bookLink+" "+ bookImg)
     var htmlCard = `<div class="col-lg-6">
       <div class="card" style="">
         <div class="row no-gutters">
           <div class="col-md-8">
             <div class="card-body">
               <h5 class="card-title">${title}</h5>
               <p class="card-text">Author: ${authorfName} ${authorlName}</p>
               <p class="card-text">Genre: ${genre}</p>
               <p class="card-text">ISBN: ${bookIsbn}</p>
               <a target="_blank" href="" class="btn btn-secondary">View Book</a>
               <a target="_blank" href="" class = "btn btn-secondary">Save Book</a>
             </div>
           </div>
         </div>
       </div>
     </div>`
     return htmlCard;
   }

   //handling error for empty search box
   function displayError() {
     alert("Search term can not be empty!")
   }

});
