/**
 * Created by zotova on 23.08.2016.
 */
var path = require('path');

//babel.transform(input, {
//    presets: [require("babel-preset-es2015")],
//});

module.exports = {
    entry: [
        
        'babel-polyfill',
        '../src/main/webapp/resources/js/app/app.js'],

    output: {
        path: __dirname,
        filename: '../src/main/webapp/resources/js/built/bundle.js'
    },
    resolveLoader: {
        modulesDirectories: [ "./node_modules", "./node_modules/babel-loader"],

    },
    resolve: { 
        
        root: [
            path.join(__dirname, ".", "node_modules"),
          
            path.resolve(__dirname, "node_modules"),
            path.resolve("../src/main/webapp/js/app")
        ],
        extensions: ['', '.js', '.json']
    },
    module: {
        loaders: [{
            test: /\.js?$/,
            loader: "babel-loader",
            exclude: /node_modules/,
            query: {
                presets: ["babel-preset-react", "babel-preset-stage-0"].map(require.resolve)
            }
        }]
    },
  
};

