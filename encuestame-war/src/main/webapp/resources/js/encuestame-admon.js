function showlayer(layer){
    var myLayer=document.getElementById(layer);
    if(myLayer.style.display=="none" || myLayer.style.display==""){
        myLayer.style.display="block";
    } else {
        myLayer.style.display="none";
        }
}