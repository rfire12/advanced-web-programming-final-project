import jwt_decode from "jwt-decode";

const USER = "user";
const HOST = "http://localhost:8080";
const USERSERVICE = "users-microservice/api";
const NOTIFICATIONSERVICE = "notifications-microservice";

export const getJWT = () => {
  return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEyMyIsIm5hbWUiOiJKb2hucyBEb2UiLCJlbWFpbCI6ImNvcnJlb0Bjb3JyZW8uY29tIiwidXNlclR5cGUiOiJjbGllbnQifQ.gG9Gnxb3XIItO9o7GSysJE-E-qE7fwCwJu-Lv8x4eLQ";
};

export const getToken = () => localStorage.getItem(USER);

export const getUser = () => {
  const token = getToken();
  try {
    return jwt_decode(token.slice(7));
  } catch (e) {
    return {};
  }
};

export const onLogin = (username, password) => {
  const url = `${HOST}/${USERSERVICE}/auth`;
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

  fetch(url, params)
    .then((response) => response.json())
    .then((response) => {
      localStorage.setItem(USER, response.token);
      const user = getUser();
      if (user.role === "Client") {
        window.location.href = "/productos";
      } else {
        window.location.href = "/compras-realizadas";
      }
    })
    .catch((e) => console.log(e));
};

export const onCreateUser = (user) => {
  const endpoint = user.role === "Client" ? "create-client" : "create";
  const autorization = user.role === "Client" ? {} : { Authorization: getToken() };
  const url = `${HOST}/${USERSERVICE}/${endpoint}`;

  //const username = `${user.name.charAt(0)}${user.lastname}${Math.floor(Math.random() * 1000) + 1}`;
  const params = {
    method: "POST",
    headers: {
      "content-type": "application/json",
      ...autorization,
    },
    body: JSON.stringify(user),
  };
  console.log(user);

  fetch(url, params)
    .then((response) => {
      if (user.role === "Client") {
        window.location.href = "/iniciar-sesion";
      } else {
        alert("Usuario creado");
        //window.location.href = "/compras-realizadas";
      }
    })
    .catch((e) => console.log(e));
};
