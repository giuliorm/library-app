/**
 * Created by zotova on 11.08.2016.
 */
/**
 * Created by zotova on 08.08.2016.
 */
$(document).ready(function() {

    $("#logout").click(function() {
        var cookie = document.cookie;

        $.ajax({
            type: "GET",
            contentType: "application/xml",
            url: "/",
            success: function(data) {
                console.log("success");
            },
            error: function(data) {
                console.log("error")
            }
        });
        
    });
});