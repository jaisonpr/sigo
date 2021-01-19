'use strict';
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var NormaSchema = new Schema({
  orgao: {
    type: String
  },
  numero: {
    type: Integer
  },
  versao: {
    type: Integer
  },
  status: {
    type: [{
      type: String,
      enum: ['Em vigor', 'Cancelada']
    }],
    default: ['Em vigor']
  },
  Created_date: {
    type: Date,
    default: Date.now
  }
});

module.exports = mongoose.model('Normas', NormaSchema);