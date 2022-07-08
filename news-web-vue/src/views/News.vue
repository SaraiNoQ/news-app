<template>
  <a-modal
    v-model:visible="editVisible"
    title="编辑新闻"
    :confirm-loading="editConfirmLoading"
    @ok="handleEditOk"
    style="width: 650px"
  >
    <div style="margin: 0 40px">
      <a-form-item label="标题">
        <a-input v-model:value="editTitle" autocomplete="off" />
      </a-form-item>
      <a-form-item label="作者">
        <a-input v-model:value="editAuthor" autocomplete="off" />
      </a-form-item>
      <a-form-item label="类型">
        <a-select
          v-model:value="editType"
          :options="typeOptions"
        ></a-select>
      </a-form-item>
      <a-form-item label="内容">
        <a-textarea v-model:value="editContent" autocomplete="off" style="height: 200px; max-height: 450px;" />
      </a-form-item>
    </div>
  </a-modal>

  <div class="content" v-if="loading">
    <a-spin size="large" />
  </div>
  <a-table bordered :data-source="dataSource" :columns="columns" v-else>
    <template #bodyCell="{ column, record }">
      <template v-if="column.dataIndex === 'operation'">
        <a-popconfirm
          v-if="dataSource.length"
          title="该操作无法撤销，确认删除该新闻类型？"
          @confirm="onDelete(record.id)"
        >
          <a>删除</a>
        </a-popconfirm>
        <span class="edit-span"><a @click="editNews(record)">编辑</a></span>
      </template>
    </template>
  </a-table>
</template>

<script lang="ts" setup>
import { ref, onBeforeMount } from 'vue'
import type { Ref } from 'vue'
import Axios from '../utils/axios'
import { message } from 'ant-design-vue'

interface DataItem {
  id: string,
  type: string,
  title: string,
  content: string,
  author?: string,
}

const columns = [
  {
    title: '序号',
    dataIndex: 'id',
    width: '7%'
  },
  {
    title: '类型',
    dataIndex: 'type'
  },
  {
    title: '标题',
    dataIndex: 'title',
    width: '25%'
  },
  {
    title: '内容',
    dataIndex: 'content',
    width: '45%'
  },
  {
    title: '操作',
    dataIndex: 'operation'
  }
]
const dataSource: Ref<DataItem[]> = ref([])

const onDelete = async (key: string) => {
  try {
    console.log(key)
    const res = await Axios.get('/web/deleteNews', {
      params: {
        id: key
      }
    })
    if (res.status === 200) {
      message.success('删除成功')
      dataSource.value = dataSource.value.filter(item => item.id !== key)
    }
  } catch (error) {
    console.log('delete news', error)
  }
}

// add modal
// const visible = ref<boolean>(false)
// const confirmLoading = ref<boolean>(false)
// const newsTitle = ref<string>('')
// const newsContent = ref<string>('')
// const newsType = ref<string>('')
const typeOptions = ref<Array<{ label: string; value: string; }>>([])

// const handleOk = async () => {
//   confirmLoading.value = true
//   try {
//     const res = await Axios.get('/web/addNews', {
//       params: {
//         title: newsTitle.value,
//         author: 'admin',
//         type: newsType.value,
//         content: newsContent.value
//       }
//     })
//     if (res.status === 200) {
//       if (!res.data.success) {
//         message.error('添加新闻失败！')
//         return
//       }
//       try {
//         const resp = await Axios.get('/backend/showNewsContent', {
//           params: {
//             title: newsTitle.value
//           }
//         })
//         if (resp.status === 200) {
//           const respData = resp.data.data
//           dataSource.value.push({
//             id: respData.id.toString(),
//             type: newsType.value,
//             title: newsTitle.value,
//             content: newsContent.value
//           })
//         }
//       } catch (error) {
//         console.log('check add', error)
//       }
//       message.success('添加类型成功！')
//       resetForm()
//       visible.value = false
//     }
//   } catch (error) {
//     console.log(error)
//   } finally {
//     confirmLoading.value = false
//   }
// }

// const resetForm = () => {
//   newsTitle.value = ''
//   newsContent.value = ''
//   newsType.value = ''
// }

// edit modal
const editVisible = ref<boolean>(false)
const editConfirmLoading = ref<boolean>(false)
const editTitle = ref<string>('')
const editAuthor = ref<string>('')
const editType = ref<string>('')
const editContent = ref<string>('')
const editId = ref<string>('')

const editNews = (key: DataItem) => {
  editTitle.value = key.title
  editAuthor.value = key?.author || ''
  editType.value = key.type
  editContent.value = key.content
  editId.value = key.id
  editVisible.value = true
}

const handleEditOk = async () => {
  editConfirmLoading.value = true
  try {
    const fd = new FormData()
    fd.append('id', editId.value)
    fd.append('title', editTitle.value)
    fd.append('author', editAuthor.value)
    fd.append('type', editType.value)
    fd.append('content', editContent.value)
    const res = await Axios.post('/web/editNews', fd)
    if (res.status === 200) {
      dataSource.value.forEach(e => {
        if (e.id === editId.value) {
          e.title = editTitle.value
          e.author = editAuthor.value
          e.type = editType.value
          e.content = editContent.value
        }
      })
      message.success('修改新闻成功！')
    }
  } catch (error) {
    console.log('edit news', error)
  } finally {
    editConfirmLoading.value = false
  }
}

const loading = ref<boolean>(true)
onBeforeMount(async () => {
  loading.value = true
  try {
    const res = await Axios.post('/backend/showAllNews')
    if (res.status === 200) {
      dataSource.value = res.data.data
    }
    const res1 = await Axios.get('/backend/showNewsType')
    if (res1.status === 200) {
      const resData = res1.data.data
      resData.forEach((item: { type: string }) => {
        typeOptions.value.push({
          label: item.type,
          value: item.type
        })
      })
    }
  } catch (error) {
    console.log('error', error)
  } finally {
    loading.value = false
  }
})
</script>

<style lang="less" scoped>
.edit-span {
  display: inline-block;
  margin-left: 20px;
}

.content {
  text-align: center;
  background: rgba(0, 0, 0, 0.04);
  height: 100%;
  padding-top: 32vh;
  border-radius: 4px;
}
</style>
