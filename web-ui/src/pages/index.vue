<template>
  <v-container>
    <v-row>
      <v-col cols="6">
        <v-autocomplete clearable chips label="标签" :items="tags"
                        item-title="name" item-value="id"
                        multiple variant="outlined"></v-autocomplete>
      </v-col>
      <v-col cols="6">
        <div>
          <v-btn @click="showCreateTagDialog = true">新增</v-btn>
        </div>
      </v-col>
    </v-row>
    <v-row no-gutters>
      <v-btn @click="loadObjects">搜索</v-btn>
      <v-btn @click="showObjectUploadDialog = true">上传</v-btn>
      <v-btn>删除</v-btn>
    </v-row>

    <v-row dense>
      <v-col v-for="item in objects" :key="item.id" cols="12" md="4">
        <v-card class="mx-auto" color="surface-variant" max-width="344">
          <template v-slot>
            <div style="display: flex" @click="() => {
              showObjectDetailDialog = true;
              object = item;
              console.log(object)
            }">
              <v-img :width="300" aspect-ratio="16/9" cover :src="'/api/v1/object/' + item.name"></v-img>
            </div>
            <div>
              <v-checkbox :label="item.name"></v-checkbox>
            </div>
          </template>
        </v-card>
      </v-col>
    </v-row>
  </v-container>

  <v-dialog v-model="showCreateTagDialog" width="400">
    <v-sheet class="pe-2" :height="200" :width="400" border>
      <p class="font-weight-medium ma-2">请输入标签名</p>
      <v-text-field class="ma-2" v-model="newTagName"></v-text-field>
      <v-btn class="ma-2" type="submit" @click="createTag">提交</v-btn>
    </v-sheet>
  </v-dialog>

  <v-dialog v-model="showObjectUploadDialog" width="400">
    <v-card title="请选择文件" class="mx-auto" min-width="400">
      <v-file-input clearable multiple v-model="files" label="File input" variant="solo-inverted"></v-file-input>
      <v-autocomplete :items="tags" v-model="fileTags" clearable chips label="标签"
                      item-title="name" item-value="id"
                      multiple variant="outlined"></v-autocomplete>
      <v-btn type="submit" @click="uploadObjects">提交</v-btn>
    </v-card>
  </v-dialog>

  <v-dialog v-model="showObjectDetailDialog" width="800">
    <v-sheet :width="800" border style="text-align: center">
      <v-img width="100%" aspect-ratio="16/9" cover :src="'/api/v1/object/' + object.name"></v-img>
      <v-autocomplete :items="tags" v-model="object.tagIds"
                      item-title="name" item-value="id"
                      clearable chips label="标签" class="ma-4"
                      multiple variant="outlined"></v-autocomplete>
      <v-btn width="100%" type="submit" @click="updateObject">保存</v-btn>
    </v-sheet>
  </v-dialog>

</template>

<script lang="ts" setup>
import {ref} from "vue";

const { proxy } = getCurrentInstance()

const objects = ref([])
const tags = ref([])

const showCreateTagDialog = ref(false)
const newTagName =ref<string>()

const showObjectUploadDialog = ref(false)
const files = ref([])
const fileTags = ref([])

const showObjectDetailDialog = ref(false)
const object = ref()

const loadTags = () => {
  proxy.$request.get(`/api/v1/tag/search`).then((response: any) => {
    tags.value = response.data.tags;
  });
}

const createTag = () => {
  proxy.$request.post(`/api/v1/tag/create`, {"name": newTagName.value}).then((response: any) => {
    loadTags()
    closeCreateTagDialog()
  });
}

const uploadObjects = () => {
  // files 和 fileTags 为 proxy类型
  const targetFiles = files.value.map(f => f)
  const targetTagIds = fileTags.value.map(f => f)
  if (targetFiles.length <= 0) {
    closeObjectUploadDialog()
    return
  }
  console.log(targetFiles)
  console.log(targetTagIds)
  const formData = new FormData()
  targetFiles.forEach(f => formData.append("files", f))
  targetTagIds.forEach(t => formData.append("tagIds", t))
  proxy.$request.post(`/api/v1/object/upload`, formData, {
    headers: {
      "Content-Type": 'multipart/form-data'
    }
  }).then((response: any) => {
    loadObjects()
  }).finally(() => {
    closeObjectUploadDialog()
  });
}

const updateObject = () => {
  console.log(object.value)
  proxy.$request.post(`/api/v1/object/update`, {
    "objectId": object.value.id,
    "tagIds": object.value.tagIds,
  }).then((response: any) => {
    loadObjects()
  }).finally(() => {
    closeObjectDetailDialog()
  });
}

const closeCreateTagDialog = () => {
  newTagName.value = undefined
  showCreateTagDialog.value = false
}

const closeObjectUploadDialog = () => {
  files.value = []
  fileTags.value = []
  showObjectUploadDialog.value = false
}

const closeObjectDetailDialog = () => {
  showObjectDetailDialog.value = false
  object.value = {}
}

const loadObjects = () => {
  proxy.$request.post(`/api/v1/object/search`, {}).then((response: any) => {
    const list = response.data.objects
    list.forEach(o => {
      o.tagIds = []
      if (o.tags) {
        o.tags.forEach(t => o.tagIds.push(t.id))
      }
    })
    objects.value = list
  });
}

loadTags()
loadObjects()

</script>
