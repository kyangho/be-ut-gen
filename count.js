const fs = require("fs")
const _ = require('lodash');
const path = require('path');
const unzipper = require("unzipper");
const resolve = require('path').resolve;
const {
    readdir
} = require('fs').promises;

var tree = [""];
var paths = []


async function getFiles(dir) {
    const dirents = await readdir(dir, {
        withFileTypes: true
    });
    const files = await Promise.all(dirents.map((dirent) => {
        const res = resolve(dir, dirent.name);
        if (dirent.isDirectory()){
            paths.push(res);
            return getFiles(res);
        }else{
            return res;
        }
    }));
    return Array.prototype.concat(...files);
}


fs.readFile("Main.log", "utf8", function (err, data) {
  var child;
  tree[0].child = child;

  var methods = data.match(/\[\w+\]/g);
  var parent = [];
  var darr = data.match(/(.+ - .+ - .+)/g).forEach((line) => {
    const [prefix, key, lineNumber] = line.split(/\s+-\s+/g);
    var child;
  });
  
});
function arrangeIntoTree(paths) {
    var tree = [];

    for (var i = 0; i < paths.length; i++) {
        var path = paths[i];
        var currentLevel = tree;
        for (var j = 0; j < path.length; j++) {
            var part = path[j];

            var existingPath = findWhere(currentLevel, 'name', part);

            if (existingPath) {
                currentLevel = existingPath.children;
            } else {
                var newPart = {
                    name: part,
                    children: [],
                }

                currentLevel.push(newPart);
                currentLevel = newPart.children;
            }
        }
    }
    return tree;
}
function findWhere(array, key, value) {
    t = 0; 
    while (t < array.length && array[t][key] !== value) { t++; }; 

    if (t < array.length) {
        return array[t]
    } else {
        return false;
    }
}
const start = async function(){
    const files = await getFiles('log');
    for(let i = 0; i < paths.length; i++){
        var path = paths.pop();
        path = path.replace(/.+log\\/g, "");
        paths = [path.split('\\'), ...paths];
    }
    var tree = arrangeIntoTree(paths);
    console.log(JSON.stringify(tree, null, 2));
}

start();