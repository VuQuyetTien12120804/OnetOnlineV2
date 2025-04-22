const express = require('express') //import thư viện
const app = express()
const port = 3000 // cổng server chạy

app.get('/', (req, res) => {
    console.log("Vào trang chủ")
    res.send('<h1 style="color: red;">Trang chủ Web</h1>')
  })

app.get('/home', (req, res) => {
    console.log("Vào trang home")
    res.send('<h1 style="color: blue;">Trang Home Web</h1>')
})

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})