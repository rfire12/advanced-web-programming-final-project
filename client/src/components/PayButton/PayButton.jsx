import React from "react";
import { PayPalButton } from "react-paypal-button-v2";
import { getToken } from "../../helpers/helpers";
import { getUser } from "../../helpers/helpers";

const PayButton = ({ amount, cart = [] }) => {
  const HOST = "http://localhost:8080";
  const ENVENTSERVICE = "events-microservice/api";

  const onPayment = () => {
    const url = `${HOST}/${ENVENTSERVICE}/invoice`;
    const ids = cart.map((product) => product.id);
    const productsNames = cart.map((product) => product.name);
    const user = getUser();

    const params = {
      method: "POST",
      headers: {
        "content-type": "application/json",
        Authorization: getToken(),
      },
      body: JSON.stringify({
        products: ids,
        total: amount,
        username: user.username,
        productsNames,
      }),
    };

    fetch(url, params)
      .then((response) => {
        alert("Gracias por su compra");
        setTimeout(() => (window.location.href = "/historial-compras"), 0);
      })
      .catch((e) => console.log(e));
  };

  return (
    <PayPalButton
      amount={amount}
      onSuccess={(details, data) => {
        onPayment();
      }}
      options={{
        clientId: "AdPyWLQa2hziR70RmnTiacG8OPzy8kRiQbsQNF0iZSPGf6Lh5e-lqFzjvLeQZHWDrwj6uCcOVoG52jZw",
      }}
    />
  );
};

export default PayButton;
