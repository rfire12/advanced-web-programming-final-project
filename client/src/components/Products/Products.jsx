import React from "react";
import ProductCard from "./ProductCard";
import { makeStyles } from "@material-ui/core";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";

const useStyles = makeStyles((theme) => ({
  container: {
    display: "inline-block",
    margin: "10px",
  },
  title: {
    fontWeight: "bold",
    marginLeft: "7px"
  },
  paymentButton: {
    position: "relative",
    display: "block",
    margin: "40px auto",
  },
}));

const Products = () => {
  const classes = useStyles();

  const productsList = [
    { name: "Pre-Boda", image: "../../assets/pre-wedding.jpg", price: 1000 },
    { name: "Boda", image: "../../assets/wedding.jpg", price: 5000 },
    { name: "Cumpleaños", image: "../../assets/birthday.jpg", price: 3000 },
    { name: "Video de evento", image: "../../assets/video-event.jpg", price: 4000 },
  ];

  return (
    <>
      <Typography className={classes.title} variant="h6" gutterBottom>
        Seleccione los productos que desea adquirir
      </Typography>
      {productsList.map((product) => (
        <div className={classes.container}>
          <ProductCard title={product.name} image={product.image} price={product.price} />
        </div>
      ))}
      <Button variant="contained" color="primary" disableElevation className={classes.paymentButton}>
        Proceder con el pago
      </Button>
    </>
  );
};

export default Products;
