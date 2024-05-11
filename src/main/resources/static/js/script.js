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
		
};