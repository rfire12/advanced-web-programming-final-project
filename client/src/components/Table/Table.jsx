import React from "react";
import Link from "@material-ui/core/Link";
import { makeStyles } from "@material-ui/core/styles";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Typography from "@material-ui/core/Typography";
import Paper from "@material-ui/core/Paper";

const useStyles = makeStyles((theme) => ({
  seeMore: {
    marginTop: theme.spacing(3),
  },
  columnHead: {
    fontWeight: "bold",
  },
  title: {
    fontWeight: "bold",
    marginLeft: "7px",
  },
  paper: {
    padding: theme.spacing(2),
    display: "flex",
    overflow: "auto",
    flexDirection: "column",
  },
}));

function preventDefault(event) {
  event.preventDefault();
}

const CustomTable = ({ title = "", fields = [], head = {}, data = [] }) => {
  const classes = useStyles();
  return (
    <Paper className={classes.paper}>
      <Typography className={classes.title} variant="h6" gutterBottom>
        {title}
      </Typography>
      <Table size="small">
        <TableHead>
          <TableRow>
            {fields.map((headTitle) => (
              <TableCell key={headTitle} className={classes.columnHead}>
                {head[headTitle]}
              </TableCell>
            ))}
          </TableRow>
        </TableHead>
        <TableBody>
          {data.map((row) => (
            <TableRow key={row.id}>
              {fields.map((field) => (
                <TableCell>{row[field]}</TableCell>
              ))}
            </TableRow>
          ))}
        </TableBody>
      </Table>
      <div className={classes.seeMore}></div>
    </Paper>
  );
};

export default CustomTable;
