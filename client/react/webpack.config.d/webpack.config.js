config.devServer = config.devServer || {}
config.devServer.historyApiFallback = true

config.output.filename = 'burger-builder-client-react.js'
config.output.publicPath = '/'

config.module.rules.push({
    test: /\.css$/i,
    use: ['style-loader', 'css-loader']
})
