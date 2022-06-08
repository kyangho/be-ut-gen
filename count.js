const fs = require("fs");
const _ = require("lodash");
const path = require("path");
const unzipper = require("unzipper");
const resolve = require("path").resolve;
const { readdir } = require("fs").promises;

var tree = [];
var dirs = [];

async function getAllPaths(dir) {
  const dirents = await readdir(dir, {
    withFileTypes: true,
  });
  const files = await Promise.all(
    dirents.map((dirent) => {
      const res = resolve(dir, dirent.name);
      if (res.endsWith(".log")) {
        dirs.push(res);
      }
      if (dirent.isDirectory()) {
        return getAllPaths(res);
      } else {
        return res;
      }
    })
  );
  return Array.prototype.concat(...files);
}

async function arrangeIntoTree(paths) {
  for (var i = 0; i < paths.length; i++) {
    var path = paths[i];
    var currentLevel = tree;
    for (var j = 0; j < path.length; j++) {
      var part = path[j];
      var existingPath = findWhere(currentLevel, "name", part);

      if (existingPath) {
        currentLevel = existingPath.children;
      } else {
        var dirPath;
        var data;
        if (part.endsWith('.log')){
          dirs.forEach(dir => {
            if (dir.includes(part)){
              dirPath = dir;
            }
          })
          data = await readFile(dirPath);
          var methods = await analyzeData(data);
        }
        var newNode = {
          name: part,
          path: dirPath,
          children: [],
        };

        currentLevel.push(newNode);
        currentLevel = newNode.children;
      }
    }
  }
  return tree;
}
function findWhere(array, key, value) {
  t = 0;
  while (t < array.length && array[t][key] != value) {
    t++;
  }
  if (t < array.length) {
    return array[t];
  } else {
    return false;
  }
}

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

function updateNode(data, key, kvalue, property, pvalue) {
  for (let i = 0; i < data.length; i++){
    if (data[i].children.length > 0 ){
      updateNode(data[i].children, key, kvalue, property, pvalue);
    }
    if (data[i][key] == kvalue){
      data[i][property] = pvalue;
      return data;
    }
  }
}


async function analyzeData(data){
  var methods = [];
  var intervals = [];
  
  var curMethod;
  var findKey = (data, key) => {
    for(let i = 0; i < data.length; i++){
      if (data[i]['key'] == key){
        return i;
      } 
    }
    return -1;
  }

  var compareInterval = function(in1, in2){
    if(in2[0] - in1[0] >= 0 && in2[1] - in1[1] >= 0){
      if (in2[0] > in1[1]){
        return [in1, in2];
      }
      return [[in1[0], in2[1]]];
    } else if (in2[0] - in1[0] <= 0 && in2[1] - in1[1] <= 0){
      if (in1[0] > in2[1]){
        return [in2, in1];
      }
      return [[in2[0], in1[1]]];
    } else if (in2[0] - in1[0] <= 0 && in2[1] - in1[1] >= 0){
      return [in2];
    } else if (in2[0] - in1[0] >= 0 && in2[1] - in1[1] <= 0){
      return [in1];
    }
  }
  var calcInterval = async function (data) {
    var intervals = [];
    data.forEach(e => {
      if(!e.startLine){
        e.startLine = e.endLine;
      }else if (!e.endLine){
        e.endLine = e.startLine;
      }
      
      if (intervals.length == 0){
        intervals.push([e.startLine, e.endLine])
      }else{
        var interval;
        for (let i = 0; i < intervals.length; i++){
          interval = compareInterval(intervals[i],  [e.startLine, e.endLine]);
          if (interval.length == 1 && interval[0] != intervals[i]){
            intervals[i] = interval[0];
          }else if (interval.length == 2){
            interval.forEach(j => {
              if (intervals[i] != j){
                intervals.push(j);
              }
            })
          }
        }
      }
    });
    return intervals;
    // console.log(intervals);
  }

  var calcLOC = function(data){
    let result = 0;
    data.forEach(d => {
      result += d[1] - d[0] + 1;
    })
    return result;
  }

  data.split("\n").forEach(line => {
    if (line.match(/\[\w+\]/g)){
      var methodName = line.replace(/\[|\]/g, "");
      if(methods.findIndex((ele) => ele['name'] === methodName) == -1 || methods.length == 0){
        var method = {
          name: methodName,
          keys: []
        }
        methods.push(method);
      }
      var i = methods.findIndex((ele) => ele['name'] === methodName);
      if (i != -1){
        curMethod = methods[i];
      }
    }
    if (line.match(/\w+\s+-\s+\w+:\s+{.*}/g)){
      var keys = curMethod['keys'];
      var startLine;
      var endLine;
      var id;
      line = line.replace(/:\s*{\s*/g, ' - ').replace(/\s*}/g, '').replace(',', ' - ');
      var attr = line.split(/\s+-\s+/g);
      id = parseInt(attr[1]);
      

      let index = findKey(keys, id);
      if (attr[0] == 'IN'){
        startLine = parseInt(attr[2].replace(/[^\d]/g, ''));
      }else if (attr[0] == 'OUT'){
        endLine = attr[2].replace(/[^\d]/g, '') != '' ? parseInt(attr[2].replace(/[^\d]/g, '')) : null;
      }
      if (index != -1){
        if (attr[0] == 'IN'){
          keys[index]['startLine'] = startLine
        }else if (attr[0] == 'OUT'){
          keys[index]['endLine'] = endLine;
        }
      }else {
        keys.push({
          key: id,
          startLine: startLine,
          endLine: endLine
        })
      }
      curMethod['keys'] = keys;
    }
  })
  for (let i = 0; i < methods.length; i++){
    intervals = await calcInterval(methods[i].keys);
    methods[i].intervals = intervals; 
    methods[i].LOC = calcLOC(intervals);
  }
  methods.forEach(m => {
    console.log(m.name);
    console.log(m.intervals);
    console.log(m.LOC);
  })
  console.log("=================================");
}

async function readFile(dir) {
  const readFiles = await fs.readFileSync(dir, "utf8", function (err, data) {
    return data;
  });
  return readFiles;
}

const start = async function () {
  const files = await getAllPaths("log");
  for (let i = 0; i < dirs.length; i++) {
    var path = dirs[i];
    path = path.replace(/.+log\\/g, "");
    path = [path.split(/\/|\\/g)];
    tree = await arrangeIntoTree(path);
  }
  for (let i = 0; i < dirs.length; i++) {
    var key;
    if (dirs[i].endsWith(".log")) {
      key = dirs[i].replace(/.*(\\|\/)/g, "");
    }

    updateNode(tree, 'name', 'Manage.log', 'ky', 'HAHAHA');
    // console.log(bfs(tree, "name", key));
  }
};

start();