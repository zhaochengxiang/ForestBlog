
const path = require('path')

module.exports = {
  chainWebpack: config => {
    config.module.rules.delete('svg') 
    config.module
      .rule('svg-sprite-loader')
      .test(/\.svg$/)
      .use('svg-sprite-loader')
      .loader('svg-sprite-loader')
      .options({
        symbolId: 'icon-[name]'
      })
   },
  configureWebpack: config => {
    Object.assign(config, {
        resolve: {
            extensions: [".js", ".vue", ".json", ],
            alias: {
              "@": path.resolve(__dirname, "./src"),
            }
        }
    });
  },
  devServer: {
      port: 8011,
      proxy: {
          '/': {
              target: 'http://localhost:8010',
              changeOrigin: true,
              ws: false
          }
      }
  },
};