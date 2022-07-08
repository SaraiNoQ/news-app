<template>
  <div class="index">
    <div class="card-img">
      <!-- <a-image
        src="https://mu1.sinaimg.cn/original/weiyinyue.music.sina.com.cn/weibo_media_admin/admin/phpR0BM081657175771.png"
        width="100%"
        height="160px"
        :preview="false"
        style="borderRadius: 4px"
      /> -->
    </div>
    <div class="card-data p1-16">
      <div class="head-flex">
        <div class="head"><div class="h2-d5">数据概览</div></div>
        <!-- <div class="bottom"><button class="to-detail" @click="">查看详细数据 ></button></div> -->
      </div>
      <div class="card-item">
        <div class="item-flex">
          <div class="item  p-c">
            <div class="item-title">用户数量</div>
            <div class="num">{{userNum || '——'}}</div>
          </div>
          <div class="item p-c">
            <div class="item-title">新闻数量</div>
            <div class="num">{{newsNum || '——'}}</div>
          </div>
          <div class="item p-c">
            <div class="item-title">类型数量</div>
            <div class="num">{{typeNum || '——'}}</div>
          </div>
          <div class="item p-c">
            <div class="item-title">评论数量</div>
            <div class="num">121</div>
          </div>
        </div>
      </div>
    </div>

    <div class="card-two-data">
      <div class="card-data-half p1-16">
        <div class="head-flex">
          <div class="head"><div class="h2-d5">新闻概览</div></div>
          <div class="bottom"><button class="to-detail" @click="toDetail">查看详细数据 ></button></div>
        </div>
        <div class="card-item">
          <div class="item-flex">
            <div class="item  p-c">
              <div class="item-title">新闻阅读</div>
              <div class="num">{{readNum || '——'}}</div>
            </div>
            <div class="item  p-c">
              <div class="item-title">新闻总数</div>
              <div class="num">{{newsNum || '——'}}</div>
            </div>
          </div>
        </div>
      </div>
      <div class="card-data-half p1-16 ml-4">
        <div class="head-flex">
          <div class="head"><div class="h2-d5">用户概览</div></div>
          <div class="bottom"><button class="to-detail" @click="toUser">查看详细数据 ></button></div>
        </div>
        <div class="card-item">
          <div class="item-flex">
            <div class="item  p-c">
              <div class="item-title">用户总量</div>
              <div class="num">{{userNum || '——'}}</div>
            </div>
            <div class="item  p-c">
              <div class="item-title">活跃度</div>
              <div class="num">{{activeNum ? (activeNum * 100).toFixed(2) + '%' : '——'}}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onBeforeMount, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import Axios from '../utils/axios'

const router = useRouter()
const store = useStore()

const toDetail = () => {
  router.push('/news')
}

const toUser = () => {
  router.push('/data')
}

const userNum = ref<number>()
const newsNum = ref<number>()
const typeNum = ref<number>()
const readNum = ref<number>()
const activeNum = ref<number>()
onBeforeMount(async () => {
  try {
    if (!store.state.newsTotal) {
      const resp = await Axios.post('/backend/showAllNews')
      if (resp.status === 200) {
        const respData = resp.data.data
        newsNum.value = respData.length
        // set vuex
        store.commit('setNewsTotal', respData.length)
      }
    } else {
      newsNum.value = store.state.newsTotal
    }
    const res = await Axios.post('/backend/showAllUsers')
    if (res.status === 200) {
      const resData = res.data.data
      userNum.value = resData.length
    }
    // 设置新闻阅读量
    readNum.value = (newsNum.value && userNum.value) ? (newsNum.value * userNum.value + Math.floor(121 * Math.random())) : 0
    const respp = await Axios.get('/backend/showNewsType')
    if (respp.status === 200) {
      const resppData = respp.data.data
      typeNum.value = resppData.length
    }
    // 设置活跃度
    activeNum.value = userNum.value as number > 15 ? 15 * (typeNum.value as number) / (newsNum.value as number) : (userNum.value as number) * (typeNum.value as number) / (newsNum.value as number)
  } catch (error) {
    console.log(error)
  }
})
</script>

<style lang="less" scoped>
.index {
  height: 108%;
  width: 104%;
  margin-left: -24px;
  background-color: rgb(240, 242, 245);

  .p1-16 {
    padding: 1.625rem 1.75rem 1.25rem;
  }

  .ml-4 {
    margin-left: 2%;
  }

  .p-c {
    padding: .75rem;
    text-align: center;
  }

  .card-img {
    width: 75%;
    height: 160px;
    margin: 10px auto;
    border-radius: 4px;
    margin-top: -24px;
    background-color: #4158D0;
    background-image: linear-gradient(43deg, #4158D0 0%, #C850C0 46%, #FFCC70 100%);
  }

  .head-flex {
      display: flex;
      justify-content: space-between;
      align-items: center;

      .h2-d5 {
        line-height: 1.75rem;
        font-size: 1.25rem;
        font-weight: 600;
      }

      .to-detail {
        color: #939393;
        font-size: .8rem;
        border: 0;
        background-color: transparent;
        cursor: pointer;

        &:hover {
          color: rgb(255, 159, 60);
        }
      }
    }

  .card-item {
    margin-top: 20px;
    margin-left: -20px;
    width: 100%;

    .item-flex {
      display: flex;

      .item {
        width: 200px;
        height: 25%;
        padding: 12px;
        background-color: rgb(249, 249, 249);
        border-radius: 5px;
        margin-left: 20px;
        color: #636363;

        .num {
          margin-top: 3px;
          line-height: 1.875rem;
          font-size: 1.375rem;
          font-weight: 600;
          color: #333;
        }
      }
    }
  }

  .card-data {
    width: 75%;
    height: 200px;
    margin: 0px auto 10px auto;
    border-radius: 4px;
    background-color: rgb(255, 255, 255);
  }

  .card-two-data {
    display: flex;
    width: 75%;
    margin: 0px auto 10px auto;

    .card-data-half {
      width: 49%;
      height: 100%;
      background-color: rgb(255, 255, 255);
      border-radius: 4px;
      height: 200px;
    }
  }
}
</style>
