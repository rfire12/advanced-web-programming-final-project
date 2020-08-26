import React from "react";
import Chart from "./Chart";

const Charts = () => {
  return (
    <>
      <Chart title="Compras realizadas" />
      <br />
      <Chart title="Solicitudes pendientes" />
      <br />
      <Chart title="Compras del dia actual" />
    </>
  );
};

export default Charts;
