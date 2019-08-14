import Vue from "vue"
import Router from "vue-router"
import Login from "./views/Login.vue"
import Layout from "./views/Layout/Layout.vue"
import Dashboard from "./views/Dashboard.vue"
import ArticleList from "./views/ArticleList.vue"
import ArticlePost from "./views/ArticlePost.vue"
import CategoryList from "./views/CategoryList.vue"
import TagList from "./views/TagList.vue"

Vue.use(Router)

export const constantRouterMap = [
  {
    path: "/login",
    hidden: true,
    component: Login
  },
  {
    path: '',
    component: Layout,
    redirect: 'dashboard',
    children: [
      {
        path: 'dashboard',
        component: Dashboard,
        name: 'dashboard',
        meta: { title: '首页', icon: 'dashboard', noCache: true }
      }
    ]
  },
  {
    path: '/article',
    component: Layout,
    name: 'article',
    redirect: 'noredirect',
    alwaysShow: true,
    meta: { title: '文章', icon: 'chart' },
    children: [
      {
        path: '/list',
        name: 'list',
        component: ArticleList,
        meta: { title: '全部文章', noCache: true }
      }, 
      {
        path: '/post',
        name: 'post',
        component: ArticlePost,
        meta: { title: '发表文章', noCache: true }
      },
      {
        path: '/edit',
        name: 'edit',
        component: ArticlePost,
        hidden: true,
        meta: { title: '修改文章', noCache: true }
      },
      {
        path: '/categoryList',
        name: 'category',
        component: CategoryList,
        meta: { title: '全部分类', noCache: true }
      },
      {
        path: '/tagList',
        name: 'tag',
        component: TagList,
        meta: { title: '全部标签', noCache: true }
      },
    ]
  }
]

export default new Router({
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
