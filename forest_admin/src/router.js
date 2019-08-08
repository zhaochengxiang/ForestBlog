import Vue from "vue";
import Router from "vue-router";
import Login from "./views/Login.vue";
import Home from "./views/Home.vue";
import ArticleList from "./views/ArticleList.vue";
import ArticlePost from "./views/ArticlePost.vue";
import CategoryList from "./views/CategoryList.vue";
import TagList from "./views/TagList.vue";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      name: "登录",
      hidden: true,
      component: Login
    },
    {
      path: '/home',
      name: '',
      component: Home,
      hidden: true
    }, {
      path: '/home',
      component: Home,
      name: '文章',
      iconCls: 'fa fa-file-text-o',
      children: [
        {
          path: '/articleList',
          name: '全部文章',
          component: ArticleList,
          meta: {
            keepAlive: false
          }
        }, 
        {
          path: '/articlePost',
          name: '发表文章',
          component: ArticlePost,
          meta: {
            keepAlive: false
          }
        },
        {
          path: '/articleEdit',
          name: '修改文章',
          component: ArticlePost,
          hidden: true,
          meta: {
            keepAlive: false
          }
        },
        {
          path: '/categoryList',
          name: '全部分类',
          component: CategoryList,
          meta: {
            keepAlive: true
          }
        },
        {
          path: '/tagList',
          name: '全部标签',
          component: TagList,
          meta: {
            keepAlive: true
          }
        },
      ]
    }
  ]
});
