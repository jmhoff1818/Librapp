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
        searchData = $("#search-box").val();
        //handling empty search input field
        if(searchData === "" || searchData === null) {
          displayError();
        }
       else {
          $.ajax({
             url: bookUrl + searchData,
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
          url: bookUrl + searchData,
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
      for (var i = 0; i < response.items.length; i+=2) {
        item = response.items[i];
        title1 = item.volumeInfo.title;
        author1 = item.volumeInfo.authors;
        publisher1 = item.volumeInfo.publisher;
        bookLink1 = item.volumeInfo.previewLink;
        bookIsbn = item.volumeInfo.industryIdentifiers[1].identifier
        bookImg1 = (item.volumeInfo.imageLinks) ? item.volumeInfo.imageLinks.thumbnail : placeHldr ;

        item2 = response.items[i+1];
        title2 = item2.volumeInfo.title;
        author2 = item2.volumeInfo.authors;
        publisher2 = item2.volumeInfo.publisher;
        bookLink2 = item2.volumeInfo.previewLink;
        bookIsbn2 = item2.volumeInfo.industryIdentifiers[1].identifier
        bookImg2 = (item2.volumeInfo.imageLinks) ? item2.volumeInfo.imageLinks.thumbnail : placeHldr ;

        // in production code, item.text should have the HTML entities escaped.
        outputList.innerHTML += '<div class="row mt-4">' +
                                formatOutput(bookImg1, title1, author1, publisher1, bookLink1, bookIsbn) +
                                formatOutput(bookImg2, title2, author2, publisher2, bookLink2, bookIsbn2) +
                                '</div>';

        console.log(outputList);
      }
   }

   //search result card formatter
   function formatOutput(bookImg, title, author, publisher, bookLink, bookIsbn) {
     // console.log(title + ""+ author +" "+ publisher +" "+ bookLink+" "+ bookImg)
     var viewUrl = 'book.html?isbn='+bookIsbn; //constructing link for bookviewer
     var htmlCard = `<div class="col-lg-6">
       <div class="card" style="">
         <div class="row no-gutters">
           <div class="col-md-4">
             <img src="${bookImg}" class="card-img">
           </div>
           <div class="col-md-8">
             <div class="card-body">
               <h5 class="card-title">${title}</h5>
               <p class="card-text">Author: ${author}</p>
               <p class="card-text">Publisher: ${publisher}</p>
               <a target="_blank" href="${viewUrl}" class="btn btn-secondary">View Book</a>
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
