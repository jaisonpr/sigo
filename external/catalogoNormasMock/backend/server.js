const 
  express = require('express'),
  mongoose = require('mongoose'), 
  bodyParser = require('body-parser'),
  cors = require('cors');

  
const Workout = require('./api/models/norma');
mongoose.set('useUnifiedTopology', true);
mongoose.set('useNewUrlParser', true);
mongoose.connect('mongodb://mongo:27018/normas-db')
  .then((db) => {
    console.log('Connected correctly to database server');
  })
  .catch(err => {
    console.log("Can't connect to the database!", err);
    console.error(err);
    process.exit();
  });


const app = express();
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());


app.use(function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "X-Requested-With");
  next();
});


var routes = require('./api/routes/normasRoutes'); 
routes(app); 

app.use(function(req, res) {
  res.status(404).send({url: req.originalUrl + ' not found'})
});


const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}.`);
});