config.module.rules.push(
    {
        test: /\.css$/,
        loader: 'to-string-loader!css-loader'
    }
);