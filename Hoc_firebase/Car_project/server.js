
const express = require('express');

const app = express();

const port = 3000;

const bodyParser = require("body-parser");
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.listen(port, () => {
    console.log(`Example app listening on port ${port}`)
});

const COMMON = require('./COMMON');

const uri = COMMON.uri;

const mongoose = require('mongoose');

const carModel = require('./carModel');

const apiMobile = require('./api');
app.use('/api', apiMobile);

app.get('/', async (req, res) => {
    await mongoose.connect(uri);

    let cars = await carModel.find();

    console.log(cars);

    res.send(cars);

})

app.post('/add_xe', async (req, res) => {
    await mongoose.connect(uri);

    // let car = {
    //     ten: 'xe 3',
    //     namSX: 2024,
    //     hang: 'Mitsubishi',
    //     gia: 7500
    // }

    let car = req.body;

    console.log(car)

    let kq = await carModel.create(car);
    console.log(kq);

    let cars = await carModel.find();

    res.send(cars);

})

app.delete('/xoa/:id', async (req, res) => {
    try {
        await mongoose.connect(uri);
        let id = req.params.id;
        console.log(id);
        
        let result = await carModel.deleteOne({ _id: id });
        if (result.deletedCount === 0) {
            return res.status(404).send({ message: "Không tìm thấy xe với ID này!" });
        }

        res.send({ message: "Xóa xe thành công!" });
    } catch (error) {
        console.error(error);
        res.status(500).send({ message: "Lỗi server!" });
    }
});


app.put('/update/:id', async (req, res) => {
    try {
        await mongoose.connect(uri);

        let id = req.params.id;
        let updateData = req.body;

        let result = await carModel.updateOne({ _id: id }, updateData);
        if (result.nModified === 0) {
            return res.status(404).send({ message: "Không tìm thấy xe để cập nhật!" });
        }

        let updatedCars = await carModel.find();
        res.send(updatedCars);
    } catch (error) {
        console.error(error);
        res.status(500).send({ message: "Lỗi server!" });
    }
});
