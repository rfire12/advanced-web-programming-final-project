import React, { Component } from "react";
import clsx from "clsx";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import "../assets/normalize.css";
import Header from "./Header/Header";
import Products from "./Products/Products";
import { makeStyles } from "@material-ui/core";

const drawerWidth = 280;

const useStyles = makeStyles((theme) => ({
  container: {
    transition: theme.transitions.create(["margin", "width"], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    margin: "100px 40px"
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
      <Route path="/">
        <Header sidebarStatus={sidebarStatus} handleSidebarOpen={handleSidebarOpen} handleSidebarClose={handleSidebarClose} />
      </Route>
      <Switch>
        <div
          className={clsx(classes.container, {
            [classes.containerShift]: sidebarStatus,
          }) }
        >
          <Route path="/productos">
            <Products />
          </Route>
        </div>
      </Switch>
    </Router>
  );
};

export default App;
