import React, { Component } from "react";
import clsx from "clsx";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import "../assets/normalize.css";
import Header from "./Header/Header";
import Products from "./Products/Products";
import { makeStyles } from "@material-ui/core";
import Login from "./Login/Login";
import SignUp from "./SignUp/SignUp";
import OrdersHistory from "./OrdersHistory/OrdersHistory";
import PendingOrders from "./PendingOrders/PendingOrders";
import AllOrdersHistory from "./AllOrdersHistory/AllOrdersHistory";
import Charts from "./Charts/Charts";
import SignUpEmployee from "./SignUpEmployee/SignUpEmployee";

const drawerWidth = 280;

const useStyles = makeStyles((theme) => ({
  container: {
    transition: theme.transitions.create(["margin", "width"], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    margin: "100px 40px",
  },
  containerShift: {
    width: `calc(100% - ${drawerWidth}px)`,
    marginLeft: drawerWidth,
    transition: theme.transitions.create(["margin", "width"], {
      easing: theme.transitions.easing.easeOut,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
}));

const App = () => {
  const [sidebarStatus, setSidebarStatus] = React.useState(false);
  const classes = useStyles();

  const handleSidebarOpen = () => {
    setSidebarStatus(true);
  };

  const handleSidebarClose = () => {
    setSidebarStatus(false);
  };

  return (
    <Router>
      <Switch>
        <Route path="/iniciar-sesion">
          <Login />
        </Route>
        <Route path="/registrarse">
          <SignUp />
        </Route>
        <Route path="/">
          <Header sidebarStatus={sidebarStatus} handleSidebarOpen={handleSidebarOpen} handleSidebarClose={handleSidebarClose} />
        </Route>
      </Switch>
      <Switch>
        <div
          className={clsx(classes.container, {
            [classes.containerShift]: sidebarStatus,
          })}
        >
          <Route path="/productos">
            <Products />
          </Route>
          <Route path="/historial-compras">
            <OrdersHistory />
          </Route>
          <Route path="/solicitudes-pendientes">
            <PendingOrders />
          </Route>
          <Route path="/compras-realizadas">
            <AllOrdersHistory />
          </Route>
          <Route path="/graficos">
            <Charts />
          </Route>
          <Route path="/crear-empleado">
            <SignUpEmployee />
          </Route>
        </div>
      </Switch>
    </Router>
  );
};

export default App;
