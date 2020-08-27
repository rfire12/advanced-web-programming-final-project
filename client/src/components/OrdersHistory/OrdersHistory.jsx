import React from "react";
import Table from "../Table/Table";
import { getToken } from "../../helpers/helpers";
import { getUser } from "../../helpers/helpers";

const OrdersHistory = () => {
  const fields = ["date", "products", "status", "total"];

  const head = { date: "Fecha", products: "Productos", total: "Total" };

  const [data, setData] = React.useState([]);

  const HOST = "http://localhost:8080";
  const EVENTSERVICE = "events-microservice/api";

  const getData = () => {
    const user = getUser();
    const url = `${HOST}/${EVENTSERVICE}/invoices/client?username=${user.username}`;
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
        const products = response.map((prod) => ({
          ...prod,
          total: `RD$${Math.round((prod.total * 58) / 1000) * 1000}.00`,
          products: prod.productsNames.join(", "),
        }));
        setData(products);
      })
      .catch((e) => console.log(e));
  };

  React.useEffect(() => {
    getData();
  }, []);

  return <Table title="Historial de ordenes" fields={fields} head={head} data={data} />;
};

export default OrdersHistory;
