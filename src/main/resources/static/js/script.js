window.onload=function(){
	
	var btn = document.querySelector("#btn");
	var sidebar = document.querySelector(".sidebar");
	btn.onclick = function(){
		sidebar.classList.toggle('active');
	}
	
	var searchIcon = document.querySelector(".bx.bxs-search");
	
    searchIcon.addEventListener("click", function(event) {
        event.preventDefault();
        
        var searchForm = document.getElementById("searchForm");
        searchForm.submit();
    });
    
    $(".likeButton").click(function() {
        var publicationId = $(this).attr("id");
        var likeCountSpan = $(this).find("span");

        $.ajax({
            url: "/publications/" + publicationId + "/like",
            type: "GET",
            success: function(data) {
                likeCountSpan.text(data);
            },
            error: function(error) {
                console.log("Error:", error);
            }
        });
    });
    
		
};