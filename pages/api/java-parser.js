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
var fileJava = null;

export const config = {
    api: {
        bodyParser: false,
    },
};
const appPath = process.env.ROOT || '';

const apiRoute = nextConnect({

    onError(error, req, res) {
        res.status(501).json({
            error: `Sorry something Happened! ${error.message}`
        });
    },
    onNoMatch(req, res) {
        res.status(405).json({
            error: `Method '${req.method}' Not Allowed`
        });
    },
});
async function zipFile(zipPath) {
    try {
        const zip = new AdmZip();
        zip.addLocalFolder(zipPath);
        zip.writeZip("json_results.zip");
    } catch (e) {}
}

function getAllPaths(obj, key, prev = '') {
    const result = []

    for (let k in obj) {
        let path = prev + (prev ? '.' : '') + k;
        if (k == key) {
            result.push(path)
        } else if (typeof obj[k] == 'object') {
            result.push(...getAllPaths(obj[k], key, path))
        }
    }
    
    return result
}

async function getFiles(dir) {
    const dirents = await readdir(dir, {
        withFileTypes: true
    });
    const files = await Promise.all(dirents.map((dirent) => {
        const res = resolve(dir, dirent.name);
        return dirent.isDirectory() ? getFiles(res) : res;
    }));
    return Array.prototype.concat(...files);
}
var bodyParser = require('body-parser')
apiRoute.use(bodyParser.urlencoded({
    extended: true
}));
apiRoute.use(multerInstance)
    .get(async (req, res) => {
        var data = fs.readFileSync(`${appPath}/public/upload/${fileJava.originalname}`);
        res.setHeader("Content-Disposition", `filename=${fileJava.originalname}`)

        return res.json(data);
    })
    .post(async (req, res) => {
        // apply them
        fileJava = req.file;
        if (fileJava) {

            if (path.extname(fileJava.originalname) === '.zip') {
                fs.mkdirSync(path.join(appPath, 'public/output'), {
                    recursive: true
                });

                fs.mkdirSync(path.join(appPath, 'public/json_results'), {
                    recursive: true
                });

                await fs.createReadStream(`${appPath}/public/upload/${fileJava.originalname}`).pipe(unzipper.Extract({
                    path: `${appPath}/public/output`
                })).promise()

                const files = await getFiles(path.join(appPath, 'public/output'));

                for (const filePath of files) {
                    if (fs.lstatSync(filePath).isFile()) {
                        if (path.extname(filePath) === '.java') {
                            const data = fs.readFileSync(filePath);
                            const cst = parse(data.toString());
                            const paths = getAllPaths(cst, 'tokenType');
                            _.omit(cst, paths);
                            const outputFile = path.join(appPath, 'public/json_results', `${filePath.replace(path.join(appPath, 'public/output'), '')}.json`);
                            fs.mkdirSync(path.dirname(outputFile), {
                                recursive: true
                            });
                            fs.writeFileSync(outputFile, JSON.stringify(cst, null, 2));
                            await zipFile(path.join(appPath, 'public/json_results'));
                        }
                    } else {
                        entry.autodrain();
                    }
                }

                return res.json("success");
            }
        } else {
            return res.json("fail");
        }
    })

export default apiRoute;