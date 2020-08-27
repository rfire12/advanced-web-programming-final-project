import React from "react";
import ProductCard from "./ProductCard";
import { makeStyles } from "@material-ui/core";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import Fade from "@material-ui/core/Fade";
import { CartesianGrid } from "recharts";
import PayButton from "../PayButton/PayButton";
import { getToken } from "../../helpers/helpers";

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
    display: "block",
    marginTop: "20px",
    position: "relative",
    textAlign: "center",
  },
  hidePaymentButton: {
    visibility: "hidden",
  },
}));

const Products = () => {
  const classes = useStyles();
  const HOST = "http://localhost:8080";
  const ENVENTSERVICE = "events-microservice/api";

  const [cart, setCart] = React.useState([]);
  const [paidFor, setPaidFor] = React.useState(false);
  const [productsList, setProductsList] = React.useState([]);

  React.useEffect(() => {
    getProductList();
  }, []);

  const addToCart = (product) => {
    setCart((prevState) => [...prevState, product]);
  };

  const getTotalAmount = () => {
    const totalAmount = cart.reduce((total, product) => total + product.price, 0);
    return Math.round(totalAmount / 58);
  };

  const removeFromCart = (id) => {
    const indexToRemove = cart.findIndex((product) => product.id === id);
    let newCart = JSON.parse(JSON.stringify(cart));
    newCart.splice(indexToRemove, 1);
    setCart(newCart);
  };

  const images = ["../../assets/pre-wedding.jpg", "../../assets/wedding.jpg", "../../assets/birthday.jpg", "../../assets/video-event.jpg"];

  const getProductList = () => {
    const url = `${HOST}/${ENVENTSERVICE}/products`;

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
        const prodWithImages = response.map((prod, index) => ({ ...prod, image: images[index] }));
        console.log(response);
        setProductsList(prodWithImages);
      })
      .catch((e) => console.log(e));
  };



  const paymentButtonVisibility = cart.length === 0 ? `${classes.paymentButton} ${classes.hidePaymentButton}` : classes.paymentButton;

  return (
    <>
      {!paidFor ? (
        <div>
          <Typography className={classes.title} variant="h6" gutterBottom>
            Seleccione los productos que desea adquirir
          </Typography>
          {productsList.map((product) => (
            <div className={classes.container}>
              <ProductCard product={product} addToCart={addToCart} removeFromCart={removeFromCart} />
            </div>
          ))}
          <div className={paymentButtonVisibility}>
            <PayButton amount={getTotalAmount()} cart={cart} />
          </div>
          <Typography className={classes.title} variant="h6" gutterBottom>
            Total Pesos: RD${Math.round((getTotalAmount() * 58) / 1000) * 1000}.00
          </Typography>
          <Typography className={classes.title} variant="h6" gutterBottom>
            Total USD: &nbsp;&nbsp;&nbsp;US${getTotalAmount()}.00
          </Typography>
        </div>
      ) : (
        <Typography className={classes.title} variant="h6" gutterBottom>
          Gracias por su compra!
        </Typography>
      )}
    </>
  );
};

export default Products;

/*
            <Fade in>
              <Button variant="contained" color="primary" disableElevation className={classes.paymentButton}>
                Proceder con el pago
              </Button>
          </Fade>*/
