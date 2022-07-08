<template>
  <a-modal
    v-model:visible="visible"
    title="添加用户"
    :confirm-loading="confirmLoading"
    @ok="handleOk"
  >
    <div style="margin: 0 40px">
      <a-form
        ref="addUserForm"
        :model="formState"
        :rules="rules"
      >
        <a-form-item
          label="昵称"
          name="username"
        >
          <a-input v-model:value="formState.username" autocomplete="off" />
        </a-form-item>
        <a-form-item
          label="密码"
          name="password"
        >
          <a-input-password v-model:value="formState.password" autocomplete="off" />
        </a-form-item>
        <a-form-item
          label="邮箱"
          name="email"
        >
          <a-input v-model:value="formState.email" autocomplete="off" />
        </a-form-item>
      </a-form>
    </div>
  </a-modal>

  <div class="content" v-if="loading">
    <a-spin size="large" />
  </div>
  <div v-else>
    <a-button
      class="editable-add-btn"
      style="margin-bottom: 8px"
      @click="showModal"
    >添加</a-button>
    <a-table bordered :data-source="dataSource" :columns="columns">
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'operation'">
          <a-popconfirm
            v-if="dataSource.length"
            title="该操作无法撤销，确认删除该用户？"
            @confirm="onDelete(record)"
          >
            <a>删除</a>
          </a-popconfirm>
        </template>
      </template>
    </a-table>
  </div>
</template>
<script lang="ts" setup>
import { ref, onBeforeMount, reactive } from 'vue'
import type { Ref } from 'vue'
import Axios from '../utils/axios'
import type { Rule } from 'ant-design-vue/es/form'
import { message } from 'ant-design-vue'

interface DataItem {
  id: string,
  username: string,
  email: string,
  password?: string,
}

const columns = [
  {
    title: '序号',
    dataIndex: 'id'
  },
  {
    title: '用户名',
    dataIndex: 'username'
  },
  {
    title: '邮箱',
    dataIndex: 'email',
    width: '50%'
  },
  {
    title: '操作',
    dataIndex: 'operation'
  }
]
const dataSource: Ref<DataItem[]> = ref([])

const onDelete = async (key: DataItem) => {
  try {
    const res = await Axios.get('/web/deleteUser', {
      params: {
        id: key.id
      }
    })
    if (res.status === 200) {
      message.success('删除成功')
      dataSource.value = dataSource.value.filter(item => key.id !== item.id)
    }
  } catch (error) {
    console.log('delete type', error)
  }
}
// modal
const visible = ref<boolean>(false)
const confirmLoading = ref<boolean>(false)
const newsType = ref<string>('')
const formState = reactive({
  username: '',
  email: '',
  password: ''
})

const addUserForm = ref()
const rules: Record<string, Rule[]> = {
  username: [{ required: true, message: '请输入昵称' }],
  password: [{ required: true, message: '请输入密码' }, { min: 6, message: '密码长度不能小于6位' }],
  email: [{ required: true, message: '请输入邮箱' }, { type: 'email', message: '请输入正确的邮箱' }]
}

const showModal = () => {
  visible.value = true
}
const handleOk = async () => {
  addUserForm.value.validateFields(['username', 'password', 'email'])
    .then(async () => {
      confirmLoading.value = true
      try {
        const res = await Axios.get('/web/addUser', {
          params: {
            username: formState.username,
            password: formState.password,
            email: formState.email
          }
        })
        if (res.status === 200) {
          if (!res.data.success) {
            message.error('添加类型失败！')
            return
          }
          try {
            const resp = await Axios.post('/backend/showAllUsers')
            if (resp.status === 200) {
              dataSource.value = resp.data.data
            }
          } catch (error) {
            console.log('show all users', error)
          }
          message.success('添加类型成功！')
          newsType.value = ''
          visible.value = false
        }
      } catch (error) {
        console.log(error)
      } finally {
        confirmLoading.value = false
      }
    }).catch((error: any) => {
      console.log(error)
    })
}

const editUser = (key: DataItem) => {
  console.log(key)
}

const loading = ref<boolean>(true)
onBeforeMount(async () => {
  loading.value = true
  try {
    const res = await Axios.post('/backend/showAllUsers')
    if (res.status === 200) {
      dataSource.value = res.data.data
    }
  } catch (error) {
    console.log('error', error)
  } finally {
    loading.value = false
  }
})

</script>
<style lang="less">
.editable-cell {
  position: relative;
  .editable-cell-input-wrapper,
  .editable-cell-text-wrapper {
    padding-right: 24px;
  }

  .editable-cell-text-wrapper {
    padding: 5px 24px 5px 5px;
  }

  .editable-cell-icon,
  .editable-cell-icon-check {
    position: absolute;
    right: 0;
    width: 20px;
    cursor: pointer;
  }

  .editable-cell-icon {
    margin-top: 4px;
    display: none;
  }

  .editable-cell-icon-check {
    line-height: 28px;
  }

  .editable-cell-icon:hover,
  .editable-cell-icon-check:hover {
    color: #108ee9;
  }

  .editable-add-btn {
    margin-bottom: 8px;
  }
}

.editable-cell:hover .editable-cell-icon {
  display: inline-block;
}

.content {
  text-align: center;
  background: rgba(0, 0, 0, 0.04);
  height: 100%;
  padding-top: 32vh;
  border-radius: 4px;
}
</style>
