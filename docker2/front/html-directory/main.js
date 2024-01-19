const userAction = async () => {
  const response = await fetch('http://localhost:8080/api/v2/devs/names', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json'
    }
  });
  const myJson = await response.json();
  console.log(myJson);
  let myHeading = document.querySelector("h1");
  myHeading.textContent = "Hello back : " + myJson;
}

userAction();
