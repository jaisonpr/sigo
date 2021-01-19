'use strict';

module.exports = function(app) {

    var controller = require('../controllers/normasController');

    app.route('/api/v1/normas/:orgao/:numero').get(controller.getNorma);
};