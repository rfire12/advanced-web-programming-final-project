import jwt_decode from "jwt-decode";

const TOKEN = "token";

export const getJWT = () => {
  return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEyMyIsIm5hbWUiOiJKb2hucyBEb2UiLCJlbWFpbCI6ImNvcnJlb0Bjb3JyZW8uY29tIiwidXNlclR5cGUiOiJjbGllbnQifQ.gG9Gnxb3XIItO9o7GSysJE-E-qE7fwCwJu-Lv8x4eLQ";
};

const getToken = () => localStorage.getItem(TOKEN);

export const getUser = (section) => {
    const token = getJWT();
    const user = jwt_decode(token);
    return user;
  };