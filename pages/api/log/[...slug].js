import { SlowBuffer } from "buffer";
import JSONParser from "formidable/src/parsers/JSON";

const appPath = process.env.ROOT || "";
const fs = require("fs");
const path = require("path");

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

function getMethodData(data, className, methodName) {
    let q = [];
    q.push(...data);
    while (q.length > 0) {
        let node = q.pop();
        node[methods].forEach((method) => {
            if (method.name == methodName) {
                return method;
            }
        });

        if (node["children"] && node["children"].length > 0) {
            for (let j = 0; j < node["children"].length; j++) {
                q = [node["children"][j], ...q];
            }
        }
    }
}


function getAllClass(data) {
    let result = [];
    let q = [];
    q.push(...data);
    while (q.length > 0) {
        let node = q.pop();
        if (node["children"] && node["children"].length <= 0) {
            result.push(node);
        }

        if (node["children"] && node["children"].length > 0) {
            for (let j = 0; j < node["children"].length; j++) {
                q = [node["children"][j], ...q];
            }
        }
    }

    var index = 1;
    function reGenKey(data) {
        for (let i = 0; i < data.length; i++) {
            data[i].key = i + 1;
            if (data[i].methods.length > 0) {
                for (let j = 0; j < data[i].methods.length; j++) {
                    data[i].methods[j].key = String(i + 1) + String (j + 1)
                }
                data[i].children = data[i].methods;
            }
        }
    }
    reGenKey(result);
    return result;
}

function getClassData(data, className) { }

export default function handler(req, res) {
    const { slug } = req.query;
    //mathc path: /testcase/method
    //match path: /testcase/class
    //match path: /testcase/
    if (slug.length == 2){
        let data = [];
        if (slug[1] == 'all') {
            let dirs = fs.readdirSync(
                path.join(appPath, `public/${slug[0]}`)
            );
            dirs.forEach(dir => {
                data.push(dir.replace(/.+${slug[0]}\/|'\\/g, '').replace(/.json$/g, ''))
            })
            data = JSON.stringify(data);
            res.end(`${data}`);
        }
    }
    else if (slug.length == 3){
        let projectToken = slug[0];
        let testcase = slug[1];
        if (slug[2] == 'method') {
            let data = fs.readFileSync(
                path.join(appPath, `public/testcases/${testcase}.json`)
            );
            data = JSON.parse(data);
            res.end(`${data}`);
        } else if (slug[2] == 'class') {
            let data = fs.readFileSync(
                path.join(appPath, `public/${projectToken}/${testcase}.json`)
            );
            data = JSON.parse(data);
            let result = getAllClass(data);
            res.end(`${JSON.stringify(result)}`);
        } else if (slug[2] == 'all') {
            let data = fs.readFileSync(
                path.join(appPath, `public/${projectToken}/${testcase}.json`)
            );
            // data = JSON.parse(data);
            // console.log(data);
            res.end(`${data}`);
        } else{
            res.end(`Hello world`);
        }
    }else{
        res.end(`Hello world`);
    }
}
