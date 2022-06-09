import JSONParser from "formidable/src/parsers/JSON";

const appPath = process.env.ROOT || '';
const fs = require("fs")
const path = require('path');

function getMethodData(data){

}

function getClassData(data){

}

export default function handler(req, res) {
    const { slug } = req.query
    let uri = slug.join('\/');
    //mathc path: /testcase/method
    //match path: /testcase/class
    //match path: 
    console.log(uri);
    if (uri.match(/\w+\/method/g)){
        let testcase = uri.split('\/')[0];
        let method = uri.split('\/');
        console.log(path.join(appPath, `public/testcases/${testcase}.json`));
        let data = fs.readFileSync(path.join(appPath, `public/testcases/${testcase}.json`));
        data = JSON.parse(data);
        console.log(data);
        res.end(`${data}`);
        
    } else if (uri.match(/\w+\/all/g)){
        let testcase = uri.split('\/')[0];
        let method = uri.split('\/');
        console.log(path.join(appPath, `public/testcases/${testcase}.json`));
        let data = fs.readFileSync(path.join(appPath, `public/testcases/${testcase}.json`));
        // data = JSON.parse(data);
        // console.log(data);
        res.end(`${data}`);
        
    }else{
        res.end(`Hello world`);
    }
  }
