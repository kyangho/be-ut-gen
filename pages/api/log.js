import nextConnect from 'next-connect';
import multerInstance from './middleware/upload';
const {
    parse
} = require("java-parser");
const fs = require("fs")
const _ = require('lodash');
const path = require('path');
const unzipper = require("unzipper");
const resolve = require('path').resolve;
const {
    readdir
} = require('fs').promises;
const AdmZip = require("adm-zip");

const sloc = require('node-sloc')

export const config = {
  api: {
      bodyParser: false,
  },
};

var tree = [];
var dirs = [];
const appPath = process.env.ROOT || '';

async function getFiles(dir) {
  const dirents = await readdir(dir, {
    withFileTypes: true,
  });
  const files = await Promise.all(
    dirents.map((dirent) => {
      const res = resolve(dir, dirent.name);
      return dirent.isDirectory() ? getFiles(res) : res;
    })
  );
  return Array.prototype.concat(...files);
}

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
        let methods;
        let dirPath;
        let data;
        let newNode;
        let parent = '';
        let key = '';
        if (j > 0){
          for (let k = 0; k < j; k++){
            parent += path[k] + '/';
            
          }
        }
        for (let k = 0; k <= j; k++){
          key += String(k + 1);
        }
        if (part.endsWith(".log")) {
          dirs.forEach((dir) => {
            if (dir.includes(part)) {
              dirPath = dir;
            }
          });
          data = await readFile(dirPath);
          methods = await analyzeData(data);
          var loc = 0;
          methods.forEach((e) => {
            loc += e.LOC;
          });
          newNode = {
            key: parseInt(key),
            name: part,
            parent: parent,
            path: dirPath,
            methods: methods,
            loc: loc,
            children: [],
          };
        } else {
          newNode = {
            key: parseInt(key),
            name: part,
            parent: parent,
            path: dirPath,
            children: [],
          };
        }
        currentLevel.push(newNode);
        currentLevel = newNode.children;
      }
    }
  }
  return tree;
}
function findWhere(array, key, value) {
  let t = 0;
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
  for (let i = 0; i < data.length; i++) {
    if (data[i].children.length > 0) {
      updateNode(data[i].children, key, kvalue, property, pvalue);
    }
    if (data[i][key] == kvalue) {
      data[i][property] = pvalue;
      return data;
    }
  }
}

async function analyzeData(data) {
  var methods = [];
  var intervals = [];

  var curMethod;
  var findKey = (data, key) => {
    for (let i = 0; i < data.length; i++) {
      if (data[i]["key"] == key) {
        return i;
      }
    }
    return -1;
  };

  var compareInterval = function (in1, in2) {
    if (in2[0] - in1[0] >= 0 && in2[1] - in1[1] >= 0) {
      if (in2[0] > in1[1]) {
        return [in1, in2];
      }
      return [[in1[0], in2[1]]];
    } else if (in2[0] - in1[0] <= 0 && in2[1] - in1[1] <= 0) {
      if (in1[0] > in2[1]) {
        return [in2, in1];
      }
      return [[in2[0], in1[1]]];
    } else if (in2[0] - in1[0] <= 0 && in2[1] - in1[1] >= 0) {
      return [in2];
    } else if (in2[0] - in1[0] >= 0 && in2[1] - in1[1] <= 0) {
      return [in1];
    }
  };
  var calcInterval = async function (data) {
    var intervals = [];
    data.forEach((e) => {
      if (!e.startLine) {
        e.startLine = e.endLine;
      } else if (!e.endLine) {
        e.endLine = e.startLine;
      }

      if (intervals.length == 0) {
        intervals.push([e.startLine, e.endLine]);
      } else {
        var interval;
        for (let i = 0; i < intervals.length; i++) {
          interval = compareInterval(intervals[i], [e.startLine, e.endLine]);
          if (interval.length == 1 && interval[0] != intervals[i]) {
            intervals[i] = interval[0];
          } else if (interval.length == 2) {
            interval.forEach((j) => {
              if (intervals[i] != j) {
                intervals.push(j);
              }
            });
          }
        }
      }
    });
    return intervals;
  };

  var calcLOC = function (data) {
    let result = 0;
    data.forEach((d) => {
      result += d[1] - d[0] + 1;
    });
    return result;
  };

  data.split("\n").forEach((line) => {
    if (line.match(/\[\w+\]/g)) {
      var methodName = line.replace(/\[|\]/g, "");
      if (
        methods.findIndex((ele) => ele["name"] === methodName) == -1 ||
        methods.length == 0
      ) {
        var method = {
          name: methodName,
          keys: [],
        };
        methods.push(method);
      }
      var i = methods.findIndex((ele) => ele["name"] === methodName);
      if (i != -1) {
        curMethod = methods[i];
      }
    }
    if (line.match(/\w+\s+-\s+\w+:\s+{.*}/g)) {
      var keys = curMethod["keys"];
      var startLine;
      var endLine;
      var id;
      line = line
        .replace(/:\s*{\s*/g, " - ")
        .replace(/\s*}/g, "")
        .replace(",", " - ");
      var attr = line.split(/\s+-\s+/g);
      id = parseInt(attr[1]);

      let index = findKey(keys, id);
      if (attr[0] == "IN") {
        startLine = parseInt(attr[2].replace(/[^\d]/g, ""));
      } else if (attr[0] == "OUT") {
        endLine =
          attr[2].replace(/[^\d]/g, "") != ""
            ? parseInt(attr[2].replace(/[^\d]/g, ""))
            : null;
      }
      if (index != -1) {
        if (attr[0] == "IN") {
          keys[index]["startLine"] = startLine;
        } else if (attr[0] == "OUT") {
          keys[index]["endLine"] = endLine;
        }
      } else {
        keys.push({
          key: id,
          startLine: startLine,
          endLine: endLine,
        });
      }
      curMethod["keys"] = keys;
    }
  });
  for (let i = 0; i < methods.length; i++) {
    intervals = await calcInterval(methods[i].keys);
    methods[i].intervals = intervals;
    methods[i].LOC = calcLOC(intervals);
  }
  return methods;
}

async function readFile(dir) {
  const readFiles = await fs.readFileSync(dir, "utf8", function (err, data) {
    return data;
  });
  return readFiles;
}

const startAnalyze = async () => {
  for (let i = 0; i < dirs.length; i++) {
    let path = dirs[i];
    path = path.replace(/.+log\\/g, "");
    path = [path.split(/\/|\\/g)];
    tree = await arrangeIntoTree(path);
  }
  for (let i = 0; i < dirs.length; i++) {
    let options = {
      path: dirs[i], // Required. The path to walk or file to read.
      extensions: ["java"], // Additional file extensions to look for. Required if ignoreDefault is set to true.
      ignorePaths: ["node_modules"], // A list of directories to ignore. Supports glob patterns.
      ignoreDefault: false, // Whether to ignore the default file extensions or not
      logger: console.log, // Optional. Outputs extra information to if specified.
    };
    // sloc(options).then((res) => {
    //   var path = dirs[i];
    //   let name = path.replace(/.*\\|\//g);
    //   console.log(dirs[i]);
    //   console.log(res.sloc);
    //   if (res != null) {
    //     updateNode(tree, "name", name, "sloc", res.sloc);
    //   }
    // });
  }
  return tree;
};

// start();


const apiRoute = nextConnect({
  onError(error, req, res) {
    res.status(501).json({
      error: `Sorry something Happened! ${error.message}`,
    });
  },
  onNoMatch(req, res) {
    res.status(405).json({
      error: `Method '${req.method}' Not Allowed`,
    });
  },
});

var bodyParser = require("body-parser");
apiRoute.use(
  bodyParser.urlencoded({
    extended: true,
  })
);
apiRoute
  .use(multerInstance)
  .get(async (req, res) => {
    
    return res.json('hello');
  })
  .post(async (req, res) => {
    // apply them
    // var data = await startAnalyze();
    var fileJava = req.file;
    console.log(req.file);
    if (fileJava) {
      if (path.extname(fileJava.originalname) === ".zip") {
        fs.mkdirSync(path.join(appPath, "public/output"), {
          recursive: true,
        });

        fs.mkdirSync(path.join(appPath, "public/json_results"), {
          recursive: true,
        });

        await fs
          .createReadStream(`${appPath}/public/upload/${fileJava.originalname}`)
          .pipe(
            unzipper.Extract({
              path: `${appPath}/public/output`,
            })
          )
          .promise();
        dirs = await getAllPaths(path.join(appPath, `public/output/${fileJava.originalname.replace('.zip', '')}`));

        let data = await startAnalyze();
        fs.mkdirSync(path.join(appPath, "public/testcases"), {
          recursive: true,
        });
        fs.writeFileSync(path.join(appPath, `public/testcases/${fileJava.originalname.replace('.zip', '')}.json`), JSON.stringify(data));
        return res.json(data);
      }
    } else {
      return res.json("fail");
    }
  });

export default apiRoute;
