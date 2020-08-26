import React from "react";
import ProductCard from "./ProductCard";
import { makeStyles } from "@material-ui/core";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import Fade from "@material-ui/core/Fade";
import { CartesianGrid } from "recharts";

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

  const [cart, setCart] = React.useState([]);
  const [totalAmount, setTotalAmount] = React.useState(0);
  const [paidFor, setPaidFor] = React.useState(false);
  const [loaded, setLoaded] = React.useState(false);

  let paypalRef = React.useRef();

  React.useEffect(() => {
    window.paypal
      .Buttons({
        createOrder: (data, actions) => {
          return actions.order.create({
            purchase_units: [
              {
                description: "Multimedia Events",
                amount: {
                  currency_code: "USD",
                  value: 1000,
                },
              },
            ],
          });
        },
        onApprove: async (data, actions) => {
          const order = await actions.order.capture();
          setPaidFor(true);
          console.log(order);
        },
        onError: (err) => {
          setError(err);
          console.error(err);
        },
      })
      .render(paypalRef.current);
  }, []);

  const addToCart = (product) => {
    setCart((prevState) => [...prevState, product]);
    setTotalAmount((prevTotal) => prevTotal + Math.round(product.price / 58));
  };

  const removeFromCart = (id) => {
    const indexToRemove = cart.findIndex((product) => product.id === id);
    let newCart = JSON.parse(JSON.stringify(cart));
    newCart.splice(indexToRemove, 1);

    setCart(newCart);
    //setTotalAmount((prevTotal) => prevTotal - Math.round(product.price / 58));
  };

  const productsList = [
    { id: 1, name: "Pre-Boda", image: "../../assets/pre-wedding.jpg", price: 1000 },
    { id: 2, name: "Boda", image: "../../assets/wedding.jpg", price: 5000 },
    { id: 3, name: "Cumpleaños", image: "../../assets/birthday.jpg", price: 3000 },
    { id: 4, name: "Video de evento", image: "../../assets/video-event.jpg", price: 4000 },
  ];

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
            <div ref={paypalRef} />
          </div>
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
