import React from "react";
import Typography from "@material-ui/core/Typography";
import { makeStyles } from "@material-ui/core";


const useStyles = makeStyles((theme) => ({
    title: {
      fontWeight: "bold",
      marginTop: "250px",
      textAlign: "center",
      
    },
  }));

const Page404 = () => {

  const classes = useStyles();

  return (
    <>
      <Typography className={classes.title} variant="h3" gutterBottom>
        404. Esta págnina no existe
      </Typography>
    </>
  );
};

export default Page404;
