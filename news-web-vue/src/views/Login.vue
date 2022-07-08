<template>
  <div class="my-login-form">
    <a-form
      :model="formState"
      name="normal_login"
      class="login-form"
      @finish="onFinish"
      @finishFailed="onFinishFailed"
    >
      <a-form-item
        label="账号"
        name="username"
        :rules="[{ required: true, message: '请输入用户名！' }]"
      >
        <a-input v-model:value="formState.username">
          <template #prefix>
            <UserOutlined class="site-form-item-icon" />
          </template>
        </a-input>
      </a-form-item>

      <a-form-item
        label="密码"
        name="password"
        :rules="[{ required: true, message: '请输入密码！' }]"
      >
        <a-input-password v-model:value="formState.password">
          <template #prefix>
            <LockOutlined class="site-form-item-icon" />
          </template>
        </a-input-password>
      </a-form-item>

      <div class="login-form-wrap">
        <a-form-item name="remember" no-style>
          <a-checkbox v-model:checked="formState.remember">记住我</a-checkbox>
        </a-form-item>
        <a href="" style="marginTop: 4px;">点击注册</a>
        <!-- <a class="login-form-forgot" href="">Forgot password</a> -->
      </div>

      <a-form-item>
        <a-button
          :disabled="disabled"
          type="primary"
          html-type="submit"
          class="login-form-button"
          :loading="loading"
          @click="loginSubmit"
        >
          登录
        </a-button>
      </a-form-item>
    </a-form>
  </div>
</template>
<script lang="ts">
import { defineComponent, reactive, computed, ref } from 'vue'
import { UserOutlined, LockOutlined } from '@ant-design/icons-vue'
import { message } from 'ant-design-vue'
import { useRouter } from 'vue-router'
import Axios from '../utils/axios'

interface FormState {
  username: string;
  password: string;
  remember: boolean;
}
export default defineComponent({
  components: {
    UserOutlined,
    LockOutlined
  },
  setup () {
    const router = useRouter()
    const formState = reactive<FormState>({
      username: localStorage.getItem('username') || '',
      password: localStorage.getItem('password') || '',
      remember: true
    })
    const onFinish = (values: any) => {
      console.log('Success:', values)
    }

    const onFinishFailed = (errorInfo: any) => {
      console.log('Failed:', errorInfo)
    }
    const disabled = computed(() => {
      return !(formState.username && formState.password)
    })

    const loading = ref<boolean>(false)
    const setSession = (bool: boolean) => {
      try {
        if (bool) {
          localStorage.setItem('username', formState.username)
          localStorage.setItem('password', formState.password)
        } else {
          localStorage.setItem('login', 'true')
        }
      } catch (error) {
        console.log(error)
      }
    }
    const loginSubmit = async () => {
      loading.value = true
      try {
        const fd = new FormData()
        fd.append('username', formState.username)
        fd.append('password', formState.password)
        const res = await Axios.post('/backend/login', fd)
        if (res.status === 200 && res.data.success) {
          message.success('登录成功！')
          setSession(formState.remember)
          router.replace('/')
          loading.value = false
        } else {
          message.error('登录失败，请检查账号密码是否正确！')
          loading.value = false
        }
      } catch (error) {
        console.log('login error', error)
      } finally {
        loading.value = false
      }
    }
    return {
      formState,
      onFinish,
      onFinishFailed,
      disabled,
      loading,
      loginSubmit
    }
  }
})
</script>
<style scoped>
.login-form-wrap {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.login-form-forgot {
  margin-bottom: 24px;
}
.login-form-button {
  width: 100%;
  margin-top: 20px;
}
body {
  overflow: hidden;
}

.my-login-form {
  margin: 30vh auto 0 auto;
  width: 35vw;
  background-color: #fff;
  padding: 50px 70px 40px 50px;
  /* border: 2px solid rgb(24, 144, 255); */
  border-radius: 4px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.12);
}
</style>
