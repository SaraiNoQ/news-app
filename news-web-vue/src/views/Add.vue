<template>
  <div>
    <a-form
      ref="addNewsRef"
      :model="formState"
      :label-col="labelCol"
      :wrapper-col="wrapperCol"
      :rules="rules"
    >
      <a-form-item label="新闻标题" name="title">
        <a-input v-model:value="formState.title" />
      </a-form-item>
      <a-form-item label="编辑人" name="author">
        <a-input v-model:value="formState.author" />
      </a-form-item>
      <a-form-item label="新闻类型" name="type">
        <a-select
          v-model:value="formState.value"
          mode="multiple"
          style="width: 60%"
          placeholder="请选择你新闻包含的类型，最多可选择三个"
          :options="typeOptions"
          @change="handleChange"
        ></a-select>
      </a-form-item>
      <a-form-item label="新闻内容" name="content">
        <a-textarea v-model:value="formState.content" style="height: 150px; max-height: 300px;"></a-textarea>
      </a-form-item>
      <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
        <a-button type="primary" @click="onAddNews">新增</a-button>
        <a-button style="margin-left: 20px" @click="resetForm">重置</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref, onBeforeMount, toRaw } from 'vue'
import type { UnwrapRef } from 'vue'
import type { Rule } from 'ant-design-vue/es/form'
import { message } from 'ant-design-vue'
import Axios from '../utils/axios'

interface FormState {
  title: string,
  author: string,
  content: string,
  value: string[],
}
interface NewsType {
    id: string,
    type: string,
    uri: string,
}

const addNewsRef = ref()
const labelCol = { style: { width: '150px' } }
const wrapperCol = { span: 14 }
const formState: UnwrapRef<FormState> = reactive({
  title: '',
  author: '',
  content: '',
  value: []
})
const typeOptions = ref<Array<{ label: string; value: string; }>>([])

onBeforeMount(async () => {
  const res = await Axios.get('/backend/showNewsType')
  if (res.status === 200) {
    const resData = res.data.data
    resData.forEach((item: NewsType) => {
      const obj = {
        label: item.type,
        value: item.type
      }
      typeOptions.value.push(obj)
    })
  }
})

// 限制可选择的类型数量
const handleChange = (value: string[]) => {
  if (value.length > 3) {
    value.pop()
    message.warning('最多选择三条类型！')
  }
}

const rules: Record<string, Rule[]> = {
  title: [{ required: true, trigger: 'change', message: '标题不能为空！' }],
  author: [{ required: true, trigger: 'change', message: '编辑人不能为空！' }],
  content: [{ required: true, trigger: 'change', message: '内容不能为空！' }]
}

const onAddNews = async () => {
  addNewsRef.value.validateFields(['title', 'author', 'content'])
    .then(async () => {
      const fd = new FormData()
      fd.append('title', formState.title)
      fd.append('author', formState.author)
      fd.append('content', formState.content)
      fd.append('type', formState.value[0])
      const res = await Axios.get('/web/addNews', {
        params: {
          title: formState.title,
          author: formState.author,
          content: formState.content,
          type: formState.value[0]
        }
      })
      if (res.status === 200) {
        if (res.data.success) {
          message.success('新闻添加成功！')
          resetForm()
        } else {
          message.error('新闻添加成功！')
          console.log('add news error', res)
        }
      }
    }).catch((err: any) => {
      console.error(err)
    })
}

const resetForm = () => {
  formState.title = ''
  formState.author = ''
  formState.content = ''
  formState.value = []
//   addNewsRef.value.resetFields()
}
</script>
