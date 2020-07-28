<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="120px">
      <el-form-item >
        <h1 class="header">2020年6月23日你来到这里，今天是你加入中心的第{{ form.date }}天！</h1>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" />
      </el-form-item>
      <el-form-item label="性别" prop="region">
        <el-select v-model="form.sex" placeholder="">
          <el-option label="男" value=""></el-option>
          <el-option label="女" value="girl"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">Create</el-button>
        <el-button @click="onCancel">Cancel</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        name: '',
        email: '',
        sex: '',
        date: '250'
      }
    }
  },
  methods: {
    onSubmit() {
      this.$message('submit!')
    },
    onCancel() {
      this.$message({
        message: 'cancel!',
        type: 'warning'
      })
    }
  }
}
</script>

<style lang="scss">
.app-container {
  
  .header{
  position: relative;
  color: chartreuse;
  }
  .el-input {
    display: inline-block;
    height: 47px;
    width:70%;
  }  
}
</style>

