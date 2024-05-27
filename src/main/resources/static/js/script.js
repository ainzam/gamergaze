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
            type: "POST",
            success: function(data) {
                likeCountSpan.text(data);
            },
            error: function(error) {
                console.log("Error:", error);
            }
        });
    });
    
    $(".commentButton").click(function() {
	    var publicationId = $(this).closest(".post").find(".likeButton").attr("id");
	    window.location.href = "/publications/" + publicationId + "/post";
	});
    const textContent = document.getElementById("textContent");
    const submitBtn = document.getElementById("submitBtn");


    function toggleSubmitButton() {
        if (textContent.value.trim() === "") {
            submitBtn.disabled = true;
        } else {
            submitBtn.disabled = false;
        }
    }

    toggleSubmitButton();

    textContent.addEventListener("input", toggleSubmitButton);

      	
};