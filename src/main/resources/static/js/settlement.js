$(function(){
    //计算总价
    let array = $(".qprice");
    let totalCost = 0;
    for(let i = 0;i < array.length;i++){
        let val = parseInt($(".qprice").eq(i).html().substring(1));
        totalCost += val;
    }
    $("#totalprice").html("￥"+totalCost);
    //settlement2使用
    $("#settlement2_totalCost").val(totalCost);
});



function addQuantity(obj){
    console.log(obj)
    let index  = $(".car_btn_2").index(obj);
    let quantity = parseInt($(".car_ipt").eq(index).val());
    let stock = parseInt($(".productStock").eq(index).val());
    let id = parseInt($(".id").eq(index).val());
    if(quantity == stock){
        alert("库存不足！");
        return false;
    }
    quantity++;
    let price = parseFloat($(".productPrice").eq(index).val())
    let cost = quantity * price;

    //将最新的quantity和cost发给后台，动态更新数据库
    $.ajax({
        url:"/cart/update/"+id+"/"+quantity+"/"+cost,
        type:"POST",
        success:function (data) {
            if(data =="success"){
                $(".qprice").eq(index).text('￥'+cost);
                $(".car_ipt").eq(index).val(quantity);


                let array = $(".qprice");
                let totalCost = 0;
                for(let i = 0;i < array.length;i++){
                    let val = parseInt($(".qprice").eq(i).html().substring(1));
                    totalCost += val;
                }
                $("#totalprice").html("￥"+totalCost);
                //settlement2使用
                $("#settlement2_totalCost").val(totalCost);
            }
        }
    });

}



function subQuantity(obj) {
    let index  = $(".car_btn_1").index(obj);
    let quantity = parseInt($(".car_ipt").eq(index).val());
    let id = parseInt($(".id").eq(index).val());
    if(quantity == 1){
        alert("至少选择一件商品！");
        return false;
    }
    quantity--;
    let price = parseFloat($(".productPrice").eq(index).val())
    let cost = quantity * price;

    $.ajax({
        url:"/cart/update/"+id+"/"+quantity+"/"+cost,
        type:"POST",
        success:function (data) {
            if(data =="success"){
                $(".qprice").eq(index).text('￥'+cost);
                $(".car_ipt").eq(index).val(quantity);



                let array = $(".qprice");
                let totalCost = 0;
                for(let i = 0;i < array.length;i++){
                    let val = parseInt($(".qprice").eq(i).html().substring(1));
                    totalCost += val;
                }
                $("#totalprice").html("￥"+totalCost);
                //settlement2使用
                $("#settlement2_totalCost").val(totalCost);
            }
        }
    });


}

//移出购物车
function removeCart(obj){
    let index = $(".delete").index(obj);
    let id = parseInt($(".id").eq(index).val());
    if(confirm("是否确定要删除？")){
        window.location.href = "/cart/deleteById/"+id;
    }


}
function settlement2() {
    var totalCost = $("#totalprice").text();
    if(totalCost=="￥0"){
        alert("购物车为空，不能结算！");
    }
    else{
        window.location.href="/cart/settlement2";
    }

}
