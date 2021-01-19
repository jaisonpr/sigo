'use strict';

var mongoose = require('mongoose'),
    Workout = mongoose.model('Normas');

exports.getNorma = function (req, res) {
    console.log("---> exports.getNorma ");

    Workout.findById(req.params.orgao, req.params.numero, function (err, workout) {
        if (err)
            res.send(err);
        res.json(workout);
    });
};
