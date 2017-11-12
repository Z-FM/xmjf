$(function () {
    //渲染进度条
    initInvestJd();
    //tab切换
    $('#tabs div').click(function () {
        $(this).addClass('tab_active');
        var show=$('#contents .tab_content').eq($(this).index());
        show.show();
        $('#tabs div').not($(this)).removeClass('tab_active');
        $('#contents .tab_content').not(show).hide();
        if($(this).index()==2){
            /**
             * 获取项目投资记录
             * ajax拼接tr
             * 追加tr到recordList
             */
            loadInvestRecodesList($("#itemId").val());
        }
    });
});

function initInvestJd() {
    var val=$("#rate").attr("data-val");
    radialIndicator.defaults.radius=40;
    radialIndicator.defaults.barColor="#fcb22f";
    radialIndicator.defaults.barWidth=10;
    radialIndicator.defaults.roundCorner=true;
    radialIndicator.defaults.percentage=true;
    radialIndicator.defaults.fontSize=25;
    $("#rate").radialIndicator();
    $("#rate").data('radialIndicator').value(val);
}

function play(){
    var rightbtn=$('#next')
    var leftbtn=$('#pre')
    var oUl=$('#slider');
    var n=0;
    var w=oUl.find('li').width()+12;
    var l=oUl.find('li').length;
    oUl.width(w*l);
    if(l<=4){
        rightbtn.unbind('click');
        leftbtn.unbind('click');
        rightbtn.hide();
        leftbtn.hide();
    }else{
        rightbtn.show();
        leftbtn.show();
        rightbtn.bind('click', function () {
            n++;
            oUl.css( 'left',''- n*w +'px' );
            if(n>0){
                leftbtn.show();
            }
            if(n==l-4){
                rightbtn.hide();
                leftbtn.show();
            }
        });
        leftbtn.bind('click',function () {
            n--;
            if(n<l-4){
                rightbtn.show();
            }
            oUl.css( 'left',''+ (-n)*w +'px' );
            leftbtn.show();
            if(n==0){
                leftbtn.hide();
            }
        })
    }
}


function picTab(ele,allNum,currentNum) {
    var ele=$('#imgLarge');
    var allNum=$('#slider').find('li');
    var  currentNum=0;
    allNum.click(function () {
        currentNum = $(this).index();
        ele.show(300);
        var ImgSrc = $(this).attr('data-url');

        ele.css('background-image', 'url('+ImgSrc+')');
    });
    $('.close').click(function () {
        ele.hide(300);
    });
    $('.left').click(function () {
        currentNum--;
        if (currentNum < 0) {
            currentNum = allNum.length - 1;
        }
        var ImgSrc = allNum.eq(currentNum).attr('data-url');
        ele.css('background-image', 'url('+ImgSrc+')');
    });

    $('.right').click(function () {
        currentNum++;
        if (currentNum > allNum.length - 1) {
            currentNum = 0;
        }
        var ImgSrc = allNum.eq(currentNum).attr('data-url');
        ele.css('background-image', 'url('+ImgSrc+')');
    })
}


function loadInvestRecodesList(itemId,pageNum) {
    var params={};
    params.itemId=itemId;
    if(!isEmpty(pageNum)){
        params.pageNum=pageNum;
    }
    $.ajax({
        type:"post",
        url:ctx+"/busItemInvest/queryBusItemInvestsByParams",
        data:params,
        dataType:"json",
        success:function(data){
            var investList=data.list;
            var paginator=data.paginator;
            // 初始化行记录
            initTrHtml(investList);
            // 页码初始化
            initNavigatePages(paginator);
        }
    })
}


function  initTrHtml(list) {
    /**
     *  <tr><td>135xxxx5698&nbsp;&nbsp;
     </td><td>1000</td><td>2017-10-31</td></tr>
     */
    if(null!=list&&list.length>0){
        var trs="";
        for(var i=0;i<list.length;i++){
            trs=trs+"<tr>";
            var tempData=list[i];
            trs=trs+"<td>" +tempData.mobile+"&nbsp;&nbsp;</td>" +
                "<td>"+tempData.investAmount+"</td>" +
                "<td>"+tempData.addtime+"</td>";
            trs=trs+"</tr>";
        }
        console.log(trs);
        $("#recordList").html(trs);
    }
}

function initNavigatePages(paginator) {
    var navigatepageNums=paginator.navigatepageNums;// 数组
    if(navigatepageNums.length>0){
        /**
         * 拼接导航页内容
         */
        var lis="";
        //var href=
        /**
         * 首页
         * 上一页
         * 下一页
         * 末页
         */
        if(!paginator.isFirstPage){
            lis=lis+"<li ><a href='javascript:getCurrentPageData(1)' title='首页' >首页</a></li>";
        }
        if(paginator.hasPreviousPage){
            lis=lis+"<li ><a href='javascript:getCurrentPageData("+(paginator.pageNum-1)+")' title='上一页' >上一页</a></li>";
        }







        for(var i=0;i<navigatepageNums.length;i++){
            var page=navigatepageNums[i];
            var href="javascript:getCurrentPageData("+page+")";
            if(paginator.pageNum==page){
                lis=lis+"<li class='active'><a  href='"+href+"' title='第"+page+"页' >"+page+"</a></li>";
            }else{
                lis=lis+"<li ><a href='"+href+"' title='第"+page+"页' >"+page+"</a></li>";
            }
        }

        if(paginator.hasNextPage){
            lis=lis+"<li ><a href='javascript:getCurrentPageData("+(paginator.pageNum+1)+")' title='下一页' >下一页</a></li>";
        }

        if(!paginator.isLastPage){
            lis=lis+"<li ><a href='javascript:getCurrentPageData("+(paginator.lastPage)+")' title='末页' >末页</a></li>";
        }
        $("#pages").html(lis);
    }

}


function getCurrentPageData(pageNum) {
    loadInvestRecodesList($("#itemId").val(),pageNum);
}