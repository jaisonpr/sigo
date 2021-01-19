'use strict';

var mongoose = require('mongoose'),
    Workout = mongoose.model('Normas');

exports.getNorma = function (req, res) {
    Workout.find({
        "orgao": req.params.orgao,
        "numero": req.params.numero
    }, function (err, norma) {
        if (err)
            res.send(err);
        res.json(norma);
    });
};
