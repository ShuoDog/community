function post(){
    var questionDTOid = $("#questionDTOid").val();
    var commentContent = $("#commentContent").val();
    $.ajax({
        type:"post",
        contentType:'application/json',
        url:"/comment",
        data:JSON.stringify({
            "parentId":questionDTOid,
            "content":commentContent,
            "type":1
        }),
        success:function (response){
            if(response.code==200){
                $("#comment").hide();
            }
            else {
                alert(response.message);
            }
            console.log(response);
        },
        dataType:"json"
    })

}