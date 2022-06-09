import JSONParser from "formidable/src/parsers/JSON";

const appPath = process.env.ROOT || '';
const fs = require("fs")
const path = require('path');

function bfs(array, key, value) {
    let q = [];
    q.push(...array);
    while (q.length > 0) {
      node = q.pop();
      if (node[key] == value) {
        return node;
      }
      if (node["children"] && node["children"].length > 0) {
        for (let j = 0; j < node["children"].length; j++) {
          q = [node["children"][j], ...q];
        }
      }
    }
  }

function getMethodData(data, className, methodName){
    let q = [];
    q.push(...data);
    while (q.length > 0) {
      node = q.pop();
      node[methods].forEach(method => {
          if (method.name == methodName){
              return method;
          }
      })
     
      if (node["children"] && node["children"].length > 0) {
        for (let j = 0; j < node["children"].length; j++) {
          q = [node["children"][j], ...q];
        }
      }
    }
}

function getClassData(data, className){

}

export default function handler(req, res) {
    const { slug } = req.query
    let uri = slug.join('\/');
    //mathc path: /testcase/method
    //match path: /testcase/class
    //match path: /testcase/
    console.log(uri);
    if (uri.match(/\w+\/method/g)){
        let testcase = uri.split('\/')[0];
        let method = uri.split('\/');
        console.log(path.join(appPath, `public/testcases/${testcase}.json`));
        let data = fs.readFileSync(path.join(appPath, `public/testcases/${testcase}.json`));
        data = JSON.parse(data);
        console.log(getMethodData(data, 'main'));
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
