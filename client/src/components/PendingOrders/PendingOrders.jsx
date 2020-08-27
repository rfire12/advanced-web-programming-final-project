import React from "react";
import Button from "@material-ui/core/Button";
import Table from "../Table/Table";

const PendingOrders = () => {
  const fields = ["date", "products", "amount", "assign"];

  const head = { date: "Fecha", products: "Productos", amount: "Total", assign: "" };

  const [data, setData] = React.useState([]);

  // Generate Order Data
  function createData(id, date, products, status, amount) {
    return { id, date, products, status, amount };
  }

  React.useEffect(() => {
    const newData = [
      createData(0, "16 Mar, 2020", "Boda", "Tupelo, MS", 312.44),
      createData(1, "19 Jul, 2020", "Pre-Boda", "London, UK", 866.99),
      createData(2, "16 Mar, 2020", "Cumpleaños", "Boston, MA", 100.81),
      createData(3, "15 Mar, 2020", "Boda", "Gary, IN", 654.39),
      createData(4, "18 Mar, 2020", "Pre-Boda", "Long Branch, NJ", 212.79),
    ];

    let dataWithAssignButton = [];

    newData.forEach((dataRow) => {
      let row = JSON.parse(JSON.stringify(dataRow));
      row.assign = (
        <Button variant="contained" color="primary">
          Autoasignar
        </Button>
      );
      dataWithAssignButton.push(row);
    });
    console.log(dataWithAssignButton);
    setData(dataWithAssignButton);
  }, []);

  return <Table title="Ordenes Pendientes" fields={fields} head={head} data={data} />;
};

export default PendingOrders;
