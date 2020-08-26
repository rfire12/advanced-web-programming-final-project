import React from "react";
import { PayPalButton } from "react-paypal-button-v2";

const PayButton = ({ amount }) => {
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
