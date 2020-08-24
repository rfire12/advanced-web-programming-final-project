import React from "react";
import Product from "./Product";
import { makeStyles } from "@material-ui/core";

const useStyles = makeStyles((theme) => ({
  container: {
    display: "inline-block",
    margin: "10px",
  },
}));

const Products = () => {
  const classes = useStyles();
  const productsList = [{ name: "Pre-Boda" }, { name: "Boda" }, { name: "Cumpleaños" }, { name: "Video de evento" }];

  return productsList.map((product) => (
    <div className={classes.container}>
      <Product title={product.name} />
    </div>
  ));
};

export default Products;
