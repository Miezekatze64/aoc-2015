//@ts-ignore
//@ts-nocheck

const { log } = require('console');

const { readFileSync } = require('fs');

const escapeString = eval;

const unescapeString = function(str) {
    return (str+'')
    .replaceAll('\\', '\\\\')
    .replaceAll('"', '\\"');
}

String.prototype.replaceAll = function(search, replacement) {
    var target = this;
    return target.split(search).join(replacement);
};

function getFileContent() {
    let content = '';
    
    try {
        content = readFileSync("./input.txt");
    } catch (e) {
        console.error(e);
    }

    return content+'';
}

var totalLen = 0;
getFileContent().split('\n').forEach((str) => {
    let l1 = str.length;
    let esc = escapeString(str+'');
    if (esc == undefined) return;
    let l2 = esc.length;

    let length = l1-l2;
    totalLen += length;
});
log("Part 1: " + totalLen);

totalLen = 0;
getFileContent().split('\n').forEach((str) => {
    let l1 = str.length;
    let esc = unescapeString(str+'');
    if (l1 == 0) return;
    let l2 = esc.length+2;

    let length = l2-l1;
    totalLen += length;
});
log("Part 2: " + totalLen);
