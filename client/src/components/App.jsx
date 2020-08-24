import React, { Component } from "react";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import "../assets/normalize.css";
import LandingPage from './LandingPage/LandingPage';

const App = () => {
    return (
      <Router>
          <Switch>
            <Route path="/">
                <LandingPage />
            </Route>
          </Switch>
      </Router>
    );
  }

export default App;
