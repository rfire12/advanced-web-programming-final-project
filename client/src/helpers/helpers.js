import jwt_decode from "jwt-decode";

const USER = "user";
const HOST = "https://localhost:8080";
const USERSERVICE = "users-microservice";
const NOTIFICATIONSERVICE = "notifications-microservice";

export const getJWT = () => {
  return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEyMyIsIm5hbWUiOiJKb2hucyBEb2UiLCJlbWFpbCI6ImNvcnJlb0Bjb3JyZW8uY29tIiwidXNlclR5cGUiOiJjbGllbnQifQ.gG9Gnxb3XIItO9o7GSysJE-E-qE7fwCwJu-Lv8x4eLQ";
};

const getToken = () => localStorage.getItem(USER);

export const getUser = () => {
  return (
    localStorage.getItem(USER) || {
      id: "123",
      name: "Johns Doe",
      email: "correo@correo.com",
      userType: "client",
    }
  );
};

export const onLogin = (username, password) => {
  const url = `${HOST}/${USERSERVICE}/login`;
  const params = {
    method: "POST",
    headers: {
      "content-type": "application/json",
    },
    body: JSON.stringify({
      username,
      password,
    }),
  };
  console.log(username);
  console.log(password);
  /*
  fetch(url, params)
    .then((response) => response.json())
    .then((response) => {
      localStorage.setItem(USER, response.data.user);
      const user = getUser();
      if (user.userType === "client") {
        window.location.href = "/productos";
      } else {
        window.location.href = "/compras-realizadas";
      }
    })
    .catch((e) => console.log(e));*/
};

export const onCreateUser = (user) => {
  const url = `${HOST}/${USERSERVICE}/create`;
  const username = `${user.name.charAt(0)}${user.lastname}${Math.floor(Math.random() * 1000) + 1}`;
  const params = {
    method: "POST",
    headers: {
      "content-type": "application/json",
    },
    body: JSON.stringify({ ...user, username }),
  };


  fetch(url, params)
    .then((response) => response.json())
    .then((response) => {
      window.location.href = "/iniciar-sesion";
    })
    .catch((e) => console.log(e));
};
