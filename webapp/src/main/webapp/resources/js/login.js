/**
 * Created by zotova on 08.08.2016.
 */
$(document).ready(function() {

    $("#submit_btn").click(function() {

        window.localStorage.setItem('jwtToken', null);

        var username = $("#username").val();
        var password = $("#password").val();
        $.ajax({
            type: "GET",
            contentType : "application/json",
            url: "/library/login",
            data: {
                "username": username,
                "password": password
            },
            success: function (data) {
                console.log("SUCCESS");
                document.cookie = "token=" + data.token;
                //window.localStorage.setItem('Token',data.token);
                //window.sessionStorage.accessToken = data.token;
                window.location.href = "/library/main";
                
            },
            error: function (xhr, status, error) {
                console.log(xhr['responseText']);
            }
        });
    });
});