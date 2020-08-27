import React from "react";
import Chart from "./Chart";

import { getToken } from "../../helpers/helpers";
import { getUser } from "../../helpers/helpers";

const Charts = () => {
  const HOST = "http://localhost:8080";
  const EVENTSERVICE = "events-microservice/api";

  const [purchasedOrders, setPurchasedOrders] = React.useState([]);

  const getData = () => {
    const url = `${HOST}/${EVENTSERVICE}/invoices/`;
    const params = {
      method: "GET",
      headers: {
        Accept: "application/json",
        Authorization: getToken(),
      },
    };

    fetch(url, params)
      .then((response) => response.json())
      .then((response) => {
        console.log(response);
        const orderQuantity = response.map((prod) => ({ xAxis: prod.productsNames.join(","), yAxis: prod.total }));
        let result = {};

        orderQuantity.forEach((order) => {
          if (!result.hasOwnProperty(order.xAxis)) {
            result[order.xAxis] = 0;
          }
          result[order.xAxis] += 1;
        });

        const keys = Object.keys(result);


        result = keys.map((key) => ({ xAxis: [key], yAxis: result[key] }));


        setPurchasedOrders(result);
      })
      .catch((e) => console.log(e));
  };

  React.useEffect(() => {
    getData();
  }, []);

  return (
    <>
      <Chart title="Compras realizadas" data={purchasedOrders} />
      <br />
    </>
  );
};

export default Charts;
