import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActionArea from "@material-ui/core/CardActionArea";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import CardMedia from "@material-ui/core/CardMedia";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";

const useStyles = makeStyles({
  root: {
    width: 300,
    opacity: "1",
  },
  media: {
    height: 140,
  },
  price: {
    fontWeight: "bold",
    marginLeft: "8px",
  },
  added: {
    color: "green",
    fontWeight: "bold",
    paddingLeft: "60px",
  },
  selected: {
    opacity: "0.5",
  },
});

const ProductCard = ({ product = {}, addToCart, removeFromCart }) => {
  const classes = useStyles();

  const onSelect = () => {
    if (selected) {
      removeFromCart(product.id);
    } else {
      addToCart(product);
    }

    setSelected(prevState => !prevState);
  };

  const [selected, setSelected] = React.useState(false);
  const cardClasses = selected ? `${classes.root} ${classes.selected}` : classes.root;

  return (
    <Card className={cardClasses} onClick={onSelect}>
      <CardActionArea>
        <CardMedia className={classes.media} image={product.image} title={product.title} />
        <CardContent>
          <Typography gutterBottom variant="h5" component="h2">
            {product.name}
          </Typography>
          <Typography variant="body2" color="textSecondary" component="p">
            {product.description}
          </Typography>
        </CardContent>
      </CardActionArea>
      <CardActions>
        <Typography variant="subtitle2" gutterBottom className={classes.price}>
          Precio: RD${product.price}.00
        </Typography>
        {selected && (
          <Typography variant="subtitle2" gutterBottom className={classes.added}>
            AÑADIDO
          </Typography>
        )}
      </CardActions>
    </Card>
  );
};

export default ProductCard;
