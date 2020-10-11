function updateLoginForm(response, showLoginCont) {
  let vis = showLoginCont ? "visible" : "hidden";
  // alert(showLoginCont);
  document.getElementById("contLogin").style.visibility = vis;

  if (!showLoginCont) {
    // alert("CREATING BUTTON DYNAMICALLY");
    let span = document.createElement("P");
    var myArr = JSON.parse(response);
    span.innerText = "Welcome back " + myArr.username;
    document.getElementById("contLoggedIn").appendChild(span);

    let formhtml = "";
    formhtml +=
      "<form method='POST' action='userlogin'><input type='hidden' name='logout' value='true'><div id='buttonholder'><button type='submit' class='btn btn-light form-custSize-sm'>LOGOUT</button></div></form>";
    document.getElementById("contLoggedIn").innerHTML += formhtml;
  }
  if (showLoginCont) {
    // alert("SHOULD BE DESTROYING CONT_LOGGEDIN CONTENTS");
    document.getElementById("contLoggedIn").innerHTML = "";
  }
}

function loginUser(sendString, showLoginCont) {
  //  alert(sendString);
  let xhttp = new XMLHttpRequest();
  //xhttp.onreadystatechange = updateLoginForm(xhttp.response);
  xhttp.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      updateLoginForm(this.response, showLoginCont);
    }
  };
  xhttp.open("POST", "userlogin", true);
  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhttp.send(sendString);
}

//This function is for producing a popup window throughout the application
const popupCenter = ({ url, title, w, h }) => {
  // Fixes dual-screen position                             Most browsers      Firefox
  const dualScreenLeft = window.screenLeft !== undefined ? window.screenLeft : window.screenX;
  const dualScreenTop = window.screenTop !== undefined ? window.screenTop : window.screenY;

  const width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
  const height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;

  const systemZoom = width / window.screen.availWidth;
  const left = (width - w) / 2 / systemZoom + dualScreenLeft;
  const top = (height - h) / 2 / systemZoom + dualScreenTop;
  const newWindow = window.open(
    url,
    title,
    `
  scrollbars=yes,
  width=${w / systemZoom}, 
  height=${h / systemZoom}, 
  top=${top}, 
  left=${left}
  `
  );
  if (window.focus) newWindow.focus();
};

//Closes popup windows - called from popup form buttons
function closeWindow() {
  window.open("", "_self").close();
}
