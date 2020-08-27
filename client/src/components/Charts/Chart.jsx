import React from "react";
import clsx from "clsx";
import { useTheme } from "@material-ui/core/styles";
import { LineChart, Line, XAxis, YAxis, Label, ResponsiveContainer } from "recharts";
import Typography from "@material-ui/core/Typography";
import { makeStyles } from "@material-ui/core";
import Paper from "@material-ui/core/Paper";


const useStyles = makeStyles((theme) => ({
  paper: {
    padding: theme.spacing(2),
    display: "flex",
    overflow: "auto",
    flexDirection: "column",
  },
  fixedHeight: {
    height: 240,
  },
}));



const Chart = ({title, data = []}) => {
  const classes = useStyles();
  const theme = useTheme();
  const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight);


  return (
    <Paper className={fixedHeightPaper}>
      <Typography variant="h6" gutterBottom>
        {title}
      </Typography>
      <ResponsiveContainer>
        <LineChart
          data={data}
          margin={{
            top: 16,
            right: 16,
            bottom: 0,
            left: 24,
          }}
        >
          <XAxis dataKey="xAxis" stroke={theme.palette.text.secondary} />
          <YAxis stroke={theme.palette.text.secondary}>
            <Label angle={270} position="left" style={{ textAnchor: "middle", fill: theme.palette.text.primary }}>
              Sales ($)
            </Label>
          </YAxis>
          <Line type="monotone" dataKey="yAxis" stroke={theme.palette.primary.main} dot={false} />
        </LineChart>
      </ResponsiveContainer>
    </Paper>
  );
};

export default Chart;
