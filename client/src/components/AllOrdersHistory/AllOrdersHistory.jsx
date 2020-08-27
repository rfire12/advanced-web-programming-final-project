import React from "react";
import Table from "../Table/Table";
import { getToken } from "../../helpers/helpers";
import { getUser } from "../../helpers/helpers";

const AllOrdersHistory = () => {
  const fields = ["date", "products", "status", "total"];

  const head = { date: "Fecha", products: "Productos", total: "Total" };

  const [data, setData] = React.useState([]);

  const HOST = "http://localhost:8080";
  const EVENTSERVICE = "events-microservice/api";

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
        const products = response.map((prod) => ({ ...prod, products: prod.productsNames.join(', ') }));
        setData(products);
      })
      .catch((e) => console.log(e));
  };

  React.useEffect(() => {
    getData();
  }, []);

  return <Table title="Historial de ordenes" fields={fields} head={head} data={data} />;
};

export default AllOrdersHistory;
