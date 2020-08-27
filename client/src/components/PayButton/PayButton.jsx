import React from "react";
import { PayPalButton } from "react-paypal-button-v2";

const PayButton = ({ amount, cart = [] }) => {

  const onLogin = () => {
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

  return (
    <PayPalButton
      amount={amount}
      onSuccess={(details, data) => {
        alert("Transaction completed by " + details.payer.name.given_name);
      }}
      options={{
        clientId: "AdPyWLQa2hziR70RmnTiacG8OPzy8kRiQbsQNF0iZSPGf6Lh5e-lqFzjvLeQZHWDrwj6uCcOVoG52jZw",
      }}
    />
  );
};

export default PayButton;
