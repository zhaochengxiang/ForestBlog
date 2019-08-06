module.exports = {
    devServer: {
        proxy: {
            '/': {
                target: 'http://localhost:8010',
                changeOrigin: true,
                ws: false
            }
        }
    }
};