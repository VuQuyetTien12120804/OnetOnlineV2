var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  // server xử lý dữ liệu và đẩy data về view
  res.render('index', { title: 'Bài 1 - Tạo Template HBS' });
});

module.exports = router;
