module.exports = {
    devServer: {
        port: 8011,
        proxy: {
            '/': {
                target: 'http://localhost:8010',
                changeOrigin: true,
                ws: false
            }
        }
    }
};