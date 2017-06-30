var webpackConfig = require("C:\\my_workspace\\vue.kt\\build\\webpack.config.js");
webpackConfig.resolve.modules.push("C:\\my_workspace\\vue.kt\\build\\classes\\java\\test\\vuekt_test.js");
var test = ""

module.exports = function (config) {
config.set({
    "basePath": "C:\\my_workspace\\vue.kt\\build",
    "frameworks": [
        "qunit"
    ],
    "reporters": [
        "progress"
    ],
    "files": [
        "C:\\my_workspace\\vue.kt\\build\\classes\\java\\test\\vuekt_test.js"
    ],
    "exclude": [
        "*~",
        "*.swp",
        "*.swo"
    ],
    "port": 9876,
    "runnerPort": 9100,
    "colors": false,
    "autoWatch": true,
    "browsers": [
        "PhantomJS"
    ],
    "captureTimeout": 5000,
    "singleRun": false,
    "preprocessors": {
        "C:\\my_workspace\\vue.kt\\build\\classes\\java\\test\\vuekt_test.js": [
            "webpack"
        ]
    },
    "plugins": [
        "karma-phantomjs-launcher",
        "karma-qunit",
        "karma-webpack"
    ],
    "client": {
        "clearContext": false,
        "qunit": {
            "showUI": true,
            "testTimeout": 5000
        }
    },
    "webpack": webpackConfig
})
};