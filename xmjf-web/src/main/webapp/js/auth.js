$(function () {
    $("#identityNext").click(function () {
        var realName=$("#realName").val();
        var card=$("#card").val();
        var _ocx_password=$("#_ocx_password").val();
        var _ocx_password1=$("#_ocx_password1").val();
        if(isEmpty(realName)){
            layer.tips("真实姓名不能为空!","#realName");
            return;
        }
        if(isEmpty(card)){
            layer.tips("身份证号不能为空!","#card");
            return;
        }
        if(isEmpty(_ocx_password)){
            layer.tips("交易密码不能为空!","#_ocx_password");
            return;
        }
        if(isEmpty(_ocx_password1)){
            layer.tips("交易密码不能为空!","#_ocx_password1");
            return;
        }
        if(_ocx_password!=_ocx_password1){
            layer.tips("交易密码不匹配!","#_ocx_password1");
            return;
        }
        $.ajax({
            type:"post",
            url:ctx+"/user/userAuth",
            data:{
                realName:realName,
                idCard:card,
                payPassword:_ocx_password
            },
            dataType:"json",
            success:function (data) {
                if(data.code==200){
                    layer.tips("用户实名认证成功!","#identityNext");
                    // 转至 账户设置页面
                }else{
                    layer.tips(data.msg,"#identityNext");
                }
            }
        })
    })
})