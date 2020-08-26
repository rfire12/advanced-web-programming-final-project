import jwt_decode from "jwt-decode";

const TOKEN = "token";
const HOST = "https://localhost:8080";
const USERSERVICE = "users-microservice";
const NOTIFICATIONSERVICE = "notifications-microservice";

export const getJWT = () => {
  return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEyMyIsIm5hbWUiOiJKb2hucyBEb2UiLCJlbWFpbCI6ImNvcnJlb0Bjb3JyZW8uY29tIiwidXNlclR5cGUiOiJjbGllbnQifQ.gG9Gnxb3XIItO9o7GSysJE-E-qE7fwCwJu-Lv8x4eLQ";
};

const getToken = () => localStorage.getItem(TOKEN);

export const getUser = () => {
  const token = getJWT();
  const user = jwt_decode(token);
  return user;
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

  fetch(url, params)
    .then((response) => response.json())
    .then((response) => {
      localStorage.setItem(TOKEN, response.data.token);
      const user = getUser();
      if (user.userType === "client") {
        window.location.href = "/productos";
      } else {
        window.location.href = "/compras-realizadas";
      }
    })
    .catch((e) => console.log(e));
};

export const onCreateUser = (name = "", lastName = "", email = "", userType = "client") => {
  const url = `${HOST}/${USERSERVICE}/signup`;
  const username = `${name.charAt(0)}${lastName}${Math.floor(Math.random() * 1000) + 1}`;
  const params = {
    method: "POST",
    headers: {
      "content-type": "application/json",
    },
    body: JSON.stringify({
      name,
      lastName,
      email,
      username,
      userType,
    }),
  };

  fetch(url, params)
    .then((response) => response.json())
    .then((response) => {
      window.location.href = "/iniciar-sesion";
    })
    .catch((e) => console.log(e));
};
