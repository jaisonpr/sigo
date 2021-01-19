'use strict';
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var NormaSchema = new Schema({
  orgao: {
    type: String
  },
  numero: {
    type: String
  },
  versao: {
    type: String
  },
  status: {
    type: [{
      type: String,
      enum: ['Em vigor', 'Cancelada']
    }],
    default: ['Em vigor']
  }
});

module.exports = mongoose.model('Normas', NormaSchema);