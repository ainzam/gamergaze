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
    
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    
    $(".likeButton").click(function() {
        var publicationId = $(this).data("publication-id");
        var likeButton = $(this);
        var likeCountSpan = likeButton.find(".likeCount");
        var userId = $("#currentUserId").val();

        $.ajax({
            url: '/publications/' + publicationId + '/like',
            type: 'POST',
            data: {
                userId: userId
            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function(response) {
                likeCountSpan.text(response);
                likeButton.find("i").addClass("liked");
            },
            error: function(xhr, status, error) {
                console.error("Error liking the publication:", error);
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