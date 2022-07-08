<template>
  <a-menu
    v-model:selectedKeys="selectedKeys"
    theme="dark"
    mode="inline"
  >
    <a-menu-item
      v-for="(item, index) in router.options.routes[0].children"
      :key="index"
    >
      <router-link :to="item.path">
        <home-outlined v-if="index===0" />
        <user-outlined v-else-if="index===1" />
        <form-outlined v-else-if="index===2" />
        <file-add-outlined v-else-if="index===3" />
        <profile-outlined v-else />
        <slot name="title">
          <span>{{item.name}}</span>
        </slot>
      </router-link>
    </a-menu-item>
  </a-menu>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import {
  UserOutlined,
  HomeOutlined,
  FormOutlined,
  FileAddOutlined,
  ProfileOutlined
} from '@ant-design/icons-vue'

const router = useRouter()
const selectedKeys = ref<string[]>(['0'])
</script>

<style lang="less">
</style>
