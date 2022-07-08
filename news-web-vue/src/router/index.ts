import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/Home.vue'),
    meta: {
      keepAlive: true
    },
    children: [
      {
        path: '/about',
        name: '首页',
        component: () => import('../views/About.vue'),
        meta: {
          icon: 'home-outlined',
          keepAlive: true
        }
      },
      {
        path: '/data',
        name: '用户管理',
        component: () => import('../views/Data.vue'),
        meta: {
          icon: 'user-outlined'
        }
      },
      {
        path: '/news',
        name: '新闻管理',
        component: () => import('../views/News.vue'),
        meta: {
          icon: 'user-outlined'
        }
      },
      {
        path: '/add',
        name: '新增新闻',
        component: () => import('../views/Add.vue')
      },
      {
        path: '/type',
        name: '类型管理',
        component: () => import('../views/Type.vue')
      }
    ]
  },
  {
    path: '/login',
    name: '登录',
    component: () => import('../views/Login.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

router.beforeEach(async (to, from, next) => {
  const toPath = ['/login']
  if (toPath.includes(to.path)) next()
  const token: string | null = window.localStorage.getItem('login') || window.localStorage.getItem('username')
  console.log('token', token)
  if (!token) {
    return next('/login')
  } else {
    next()
  }
})

export default router
