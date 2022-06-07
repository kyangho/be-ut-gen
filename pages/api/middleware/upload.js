
const multer = require("multer");
const util = require("util");
const fs = require('fs');
const path = require('path');

var storage = multer.diskStorage({
  destination: (req, file, cb) => {
      fs.mkdir("public/upload",
      { recursive: true }, (err) => {
        if (err) {
          return console.error(err);
        }
        cb(null, "public/upload");
      });
  },
  filename: (req, file, cb) => {
    const name = file.originalname.split(" ").join();
    if (path.extname(name) === '.zip') {
      cb(null, name);
    } else {
      cb("File tải lên phải là file java code");
    }
  },
});
var upload = multer({ storage: storage }).single("file");
var multerInstance = util.promisify(upload);

export default multerInstance ;
