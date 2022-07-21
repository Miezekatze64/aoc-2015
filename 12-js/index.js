const {readFileSync} = require('fs');
const content = readFileSync("./input.txt");
const json = JSON.parse(content);

function sum(json, part2=false) {
    let count = 0;
    for (let key in json) {
        let val = json[key];
        if (typeof(val) === 'number') {
            count += val;
        } else if (typeof(val) === 'object') {
            if (!Array.isArray(val) && part2 && Object.values(val).includes('red')) continue;
            count += sum(val, part2);
        }
    }
    return count;
}

console.log("Part 1: " + sum(json));
console.log("Part 2: " + sum(json, true));
