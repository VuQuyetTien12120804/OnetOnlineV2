const express = require('express');
const router = express.Router();
const COMMON = require('./COMMON');
const mongoose = require('mongoose');
const carModel = require('./carModel');

router.get('/', (req, res) => {
    res.send('vao api mobile');
});

// API lấy danh sách xe
router.get('/list', async (req, res) => {
    await mongoose.connect(COMMON.uri);
    let cars = await carModel.find();
    console.log(cars);
    res.send(cars);
});

// API thêm xe
router.post('/add_xe', async (req, res) => {
    try {
        await mongoose.connect(COMMON.uri);
        let car = req.body;
        console.log(car);

        let kq = await carModel.create(car);
        console.log(kq);

        let cars = await carModel.find();
        res.send(cars);
    } catch (error) {
        console.error("Lỗi thêm xe:", error);
        res.status(500).send({ message: "Lỗi server!" });
    }
});

module.exports = router;
