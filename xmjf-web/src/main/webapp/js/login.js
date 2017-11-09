$(function () {
    $("#login").click(function () {
        var phone = $('#phone').val();
        var password = $('#password').val();
        if (isEmpty(phone)){
            layer.tips("请先输入手机号","#phone");
            return;
        }
        if (!/^[1][3,4,5,7,8][0-9]{9}$/.exec(phone)) {
            layer.tips("请输入正确的手机号",'#phone');
            return;
        }
        if(isEmpty(password)){
            layer.tip("请输入密码",'#password');
            return;
        }
        $.ajax({
            type:"post",
            url:ctx+"/user/userLogin",
            data:{
                phone:phone,
                password:password
            },
            dataType:"json",
            success: function (data) {
                if(data.code==200){
                    window.location.href=ctx+"/index";
                }else{
                    layer.tips(data.msg,'#login');
                }
            }
        });
    });
    $(".login-content").keydown(function() {
        //keyCode=13 是回车键
        if (event.keyCode == "13") {
            $('#login').click();
        }
    });
})