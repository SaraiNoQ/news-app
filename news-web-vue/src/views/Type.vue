<template>
  <a-modal
    v-model:visible="visible"
    title="添加新闻类型"
    :confirm-loading="confirmLoading"
    @ok="handleOk"
  >
    <div style="margin: 0 40px">
      <a-form-item label="类型">
        <a-input v-model:value="newsType" autocomplete="off" />
      </a-form-item>
    </div>
  </a-modal>

  <div class="content" v-if="loading">
    <a-spin size="large" />
  </div>
  <div  v-else>
    <a-button
      class="editable-add-btn"
      style="margin-bottom: 8px"
      @click="showModal"
    >添加</a-button>
    <a-table bordered :data-source="dataSource" :columns="columns">
      <template #bodyCell="{ column, text, record }">
        <template v-if="column.dataIndex === 'type'">
          <div class="editable-cell">
            <div v-if="editableData[record.key]" class="editable-cell-input-wrapper">
              <a-input v-model:value="editableData[record.key].type" @pressEnter="save(record.key)" />
              <check-outlined class="editable-cell-icon-check" @click="save(record.key)" />
            </div>
            <div v-else class="editable-cell-text-wrapper">
              {{ text || ' ' }}
              <edit-outlined class="editable-cell-icon" @click="edit(record.key)" />
            </div>
          </div>
        </template>
        <template v-else-if="column.dataIndex === 'operation'">
          <a-popconfirm
            v-if="dataSource.length"
            title="该操作无法撤销，确认删除该新闻类型？"
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
import { reactive, ref, onBeforeMount } from 'vue'
import type { Ref, UnwrapRef } from 'vue'
import { CheckOutlined, EditOutlined } from '@ant-design/icons-vue'
import { cloneDeep } from 'lodash-es'
import Axios from '../utils/axios'
import { message } from 'ant-design-vue'

interface DataItem {
  id: string,
  type: string,
  uri: string,
}

const columns = [
  {
    title: '序号',
    dataIndex: 'id',
    width: '20%'
  },
  {
    title: '类型',
    dataIndex: 'type'
  },
  {
    title: '地址',
    dataIndex: 'uri',
    width: '50%'
  },
  {
    title: '操作',
    dataIndex: 'operation'
  }
]
const dataSource: Ref<DataItem[]> = ref([])
const editableData: UnwrapRef<Record<string, DataItem>> = reactive({})

const edit = (key: string) => {
  editableData[key] = cloneDeep(dataSource.value.filter(item => key === item.id)[0])
}
const save = (key: string) => {
  Object.assign(dataSource.value.filter(item => key === item.id)[0], editableData[key])
  delete editableData[key]
}

const onDelete = async (key: { id: string, type: string }) => {
  try {
    const res = await Axios.get('/web/deleteNewsType', {
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
const showModal = () => {
  visible.value = true
}
const handleOk = async () => {
  confirmLoading.value = true
  try {
    const res = await Axios.get('/web/addNewsType', {
      params: {
        type: newsType.value
      }
    })
    if (res.status === 200) {
      if (!res.data.success) {
        message.error('添加类型失败！')
        return
      }
      const resp = await Axios.get('/backend/showNewsType')
      if (resp.status === 200) {
        const respData = resp.data.data
        respData.forEach((item: {type: string, id: string, uri: string}) => {
          if (item.type === newsType.value) {
            dataSource.value.push({
              id: item.id,
              type: item.type,
              uri: item.uri || ''
            })
          }
        })
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
}

const loading = ref<boolean>(true)
onBeforeMount(async () => {
  loading.value = true
  try {
    const res = await Axios.get('/backend/showNewsType')
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
