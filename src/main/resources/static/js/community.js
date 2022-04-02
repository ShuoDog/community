function post() {
    var questionDTOid = $("#questionDTOid").val();
    var commentContent = $("#commentContent").val();
    $.ajax({
        type: "post",
        contentType: 'application/json',
        url: "/comment",
        data: JSON.stringify({
            "parentId": questionDTOid,
            "content": commentContent,
            "type": 1
        }),
        success: function (response) {
            if (response.code == 200) {
                $("#comment").hide();
            } else if (response.code == 1999) {
                var isAccepted = confirm(response.message);
                if (isAccepted) {
                    window.open("https://github.com/login/oauth/authorize?client_id=e1066b38ad8c9fb34f90&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                    window.localStorage.setItem("localData" , "true");
                }
                alert(response.message);
            }
            console.log(response);
        },
        dataType: "json"
    })

}