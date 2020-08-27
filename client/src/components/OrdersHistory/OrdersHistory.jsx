import React from "react";
import Table from "../Table/Table";

const OrdersHistory = () => {
  const fields = ["date", "products", "status", "amount"];

  const head = { date: "Fecha", products: "Productos", status: "Status", amount: "Total" };

  const [data, setData] = React.useState([]);

  const HOST = "https://localhost:8080";
  const EVENTSERVICE = "events-microservice";

  const getData = () => {
    const url = `${HOST}/${EVENTSERVICE}/invoices`;
    const params = {
      method: "GET",
      headers: {
        Accept: "application/json",
      },
    };

    fetch(url, params)
      .then((response) => response.json())
      .then((response) => {
        setData(response.data)
      })
      .catch((e) => console.log(e));
  };

  React.useEffect(() => {
    const newData = [
      createData(0, "16 Mar, 2019", "Pre-boda, Boda", "Tupelo, MS", 312.44),
      createData(1, "16 Mar, 2019", "Cumpleaños", "London, UK", 866.99),
      createData(2, "16 Mar, 2019", "Video Evento", "Boston, MA", 100.81),
      createData(3, "16 Mar, 2019", "Video Evento", "Gary, IN", 654.39),
      createData(4, "15 Mar, 2019", "Cumpleaños", "Long Branch, NJ", 212.79),
    ];

    setData(newData);
  }, []);

  // Generate Order Data
  function createData(id, date, products, status, amount) {
    return { id, date, products, status, amount };
  }

  return <Table title="Historial de ordenes" fields={fields} head={head} data={data} />;
};

export default OrdersHistory;
