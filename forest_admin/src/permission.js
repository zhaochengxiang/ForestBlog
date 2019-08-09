import router from './router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

NProgress.configure({ showSpinner: false })

router.beforeEach((to, from, next) => {
  NProgress.start() 
  if (window.localStorage.getItem('token')) { 
    if (to.path === '/') {
      next({ path: '/home' })
      NProgress.done() 
    } else {
      next()
    }
  } else {
    if (to.path == '/') {
      next()
    } else {
      next('/')
      NProgress.done() 
    }
  }
})

router.afterEach(() => {
  NProgress.done() 
})
