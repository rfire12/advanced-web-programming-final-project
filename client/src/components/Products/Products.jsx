import React from "react";
import ProductCard from "./ProductCard";
import { makeStyles } from "@material-ui/core";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import Fade from "@material-ui/core/Fade";

const useStyles = makeStyles((theme) => ({
  container: {
    display: "inline-block",
    margin: "10px",
  },
  title: {
    fontWeight: "bold",
    marginLeft: "7px",
  },
  paymentButton: {
    position: "relative",
    display: "block",
    margin: "40px auto",
  },
}));

const Products = () => {
  const classes = useStyles();

  const [cart, setCart] = React.useState([]);

  const addToCart = (product) => {
    setCart((prevState) => [...prevState, product]);

  };

  const removeFromCart = (id) => {
    const indexToRemove = cart.findIndex((product) => product.id === id);
    let newCart = JSON.parse(JSON.stringify(cart));
    newCart.splice(indexToRemove, 1);
    setCart(newCart);
  };

  const productsList = [
    { id: 1, name: "Pre-Boda", image: "../../assets/pre-wedding.jpg", price: 1000 },
    { id: 2, name: "Boda", image: "../../assets/wedding.jpg", price: 5000 },
    { id: 3, name: "Cumpleaños", image: "../../assets/birthday.jpg", price: 3000 },
    { id: 4, name: "Video de evento", image: "../../assets/video-event.jpg", price: 4000 },
  ];

  return (
    <>
      <Typography className={classes.title} variant="h6" gutterBottom>
        Seleccione los productos que desea adquirir
      </Typography>
      {productsList.map((product) => (
        <div className={classes.container}>
          <ProductCard product={product} addToCart={addToCart} removeFromCart={removeFromCart} />
        </div>
      ))}
      {cart.length > 0 && (
        <Fade in>
          <Button variant="contained" color="primary" disableElevation className={classes.paymentButton}>
            Proceder con el pago
          </Button>
        </Fade>
      )}
    </>
  );
};

export default Products;
