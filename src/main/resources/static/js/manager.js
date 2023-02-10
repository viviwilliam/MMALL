//移除地址列表
function removeAddress(obj){
    let index = $(".delete").index(obj);
    let id = parseInt($(".id").eq(index).val())
    if(confirm("是否确定要删除？")){
        window.location.href = "/userAddress/deleteById/"+id;
    }
}


// function changeAddress(obj){
//     let index = $(".change").index(obj);
//     let id = parseInt($("#Id")).val();
//     if(confirm("是否确定要删除？")){
//         window.location.href = "/userAddress/deleteById/"+id;
//     }
// }

function removeOrder(obj){
    let index = $(".changeOrder").index(obj);
    let id = parseInt($(".id").eq(index).val())
    if(confirm("是否确定要删除？")){
        window.location.href = "/orderDetail/deleteById/"+id;
    }
}



function changeState(obj){
    let index = $(".changeState").index(obj);
    let id = parseInt($(".id").eq(index).val())
    window.location.href = "/orderDetail/changeById/"+id;

}


