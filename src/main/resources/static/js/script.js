let btn = document.getElementById('mybtn');
btn.classList.add('disabled');
let myArray = [];
testpet1 = {username: "Trump 4 President", content: "Make America Great Again"};
testpet2 = {username: "Glazer's Out!", content: "Force the Glazer's out of Manchester United"};
myArray.push(testpet1);
myArray.push(testpet2);

myArray.forEach(function(item) {
        $("#comments").prepend("<li class='list-group-item'><div class='row p-1'>" +
                                            "<div class='col-md-2 text-start fw-bold'><span class='badge rounded-pill bg-primary me-2 fs-smaller'>" + item.username +"</span>"+
                                                 "</div>" +
                                            "<div class='col-md-10 text-start fw-light'>"
                                                + item.content + "</div>" +
                                        "</div></li>");
    });
btn.addEventListener('click', function handleClick(event) {

    // Each time we add a comment, we need to clear the comments section first
    let comments = document.getElementById('comments');
    comments.replaceChildren();

    let user = document.getElementById('handleInput').value;
    let post = document.getElementById('textInput').value;
    // create comment object, and add this to the array 'myArray'
    let comment = {username: user, content: post};
    myArray.push(comment);

    // Loop through myArray of comment objects, and prepend HTML to '#comments'
    myArray.forEach(function(item) {
        $("#comments").prepend("<li class='list-group-item'><div class='row p-1'>" +
                                            "<div class='col-md-2 text-start fw-bold'><span class='badge rounded-pill bg-primary me-2 fs-smaller'>" + item.username +"</span>"+
                                                 "</div>" +
                                            "<div class='col-md-10 text-start fw-light'>"
                                                + item.content + "</div>" +
                                        "</div></li>");
    });
    console.log(myArray); // debugging contents of array

    //need to add myArray to browser cookie so it can remember contents for display on other pages

    // clear contents of the input fields
    const inputs = document.querySelectorAll('#handleInput, #textInput');
    inputs.forEach(input => {
        input.value = '';
    });

    // make the 'Post Comment' button disabled again
    btn.classList.add('disabled');
    event.preventDefault();
});

// remove disabled class from 'Post' button if entries are not filled
document.getElementById('textInput').onkeydown = function(){
    if( document.getElementById('handleInput').value !== '' ){
        btn.classList.remove('disabled');
    }
    else{
        btn.classList.add('disabled');
    }
}
document.getElementById('handleInput').onkeydown = function(){
    if( document.getElementById('textInput').value !== '' ){
        btn.classList.remove('disabled');
    }
    else{
        btn.classList.add('disabled');
    }
}

function maxDigit(obj) {

    let a = obj.number.toString();
    let b = a.split('');

    console.log(Math.max.apply(null, b));

}
maxDigit({number: 128357});