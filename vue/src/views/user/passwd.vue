<template>
  <div class="passwd-container">
      <el-form ref="Form" :model="form" :rules="Rules"  auto-complete="on">
      <el-form-item class="oldPasswd" prop="password" label=" 旧密码  :" >
        <el-input
         ref="password"
         :type="passwordType"
         placeholder="请输入密码"
         v-model="form.oldPasswd" />
          <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>

        <el-form-item class = "newPasswd" prop="password" label=" 新密码  : " >
        <el-input
          :key="newType"
          ref="password"
          v-model="form.newPasswd"
          :type="newType"
          placeholder="请输入新的密码"
          name="newPassword"
          tabindex="2"
          auto-complete="on"
        />
        <span class="show-pwd" @click="showNewPwd">
          <svg-icon :icon-class="newType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
         <el-form-item class = "againPasswd" prop="password" label="确认密码:">
        <el-input 
          :key="againType"
          ref="password"
          v-model="form.againPasswd"
          :type="againType"
          placeholder="确认新的密码"
          name="againPassword"
          tabindex="2"
          auto-complete="on"
        />
        <span class="show-pwd" @click="showAgainPwd">
          <svg-icon :icon-class="againType === 'password' ? 'eye' : 'eye-open'" />
        </span>

      </el-form-item>
      <el-form-item class="button">
         <el-button  type="success" round plain @click="onReSet" icon="el-icon-refresh-left" >重置</el-button>
        <el-button  type="primary" round plain   @click="onSubmit" icon="el-icon-check" >确认</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('The password can not be less than 6 digits'))
      } else {
        callback()
      }
    }
    return {
      form: {
        oldPasswd: '',
        newPasswd: '',
        againPasswd: ''
      },
      Rules: {
        oldPasswd: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      loading: false,
      passwordType: 'password',
      newType: 'password',
      againType: 'password',
      redirect: undefined
    }
  },
  methods: {
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
    },
    showNewPwd() {
      if (this.newType === 'password') {
        this.newType = ''
      } else {
        this.newType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    showAgainPwd() {
      if (this.againType === 'password') {
        this.againType = ''
      } else {
        this.againType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    onSubmit() {
      this.$message('submit!')
    },
    onReSet() {
      this.$message({
        message: 'cancel!',
        type: 'warning'
      })
    }
  }
}
</script>

<style lang="scss">
$cursor: #fff;
.passwd-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width:70%;
  }
    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
     .show-pwd {
    position: absolute;
    font-size: 16px;
    cursor: pointer;
    margin-left:5px;
    user-select: none;
  }
}
.button{
  margin-left: 55%;
  margin-top:100px;
 text-align: center;
}

.oldPasswd{
  margin-top:50px;
  margin-left:30px;
}
.newPasswd{
  margin-top:30px;
  margin-left:30px;
}
.againPasswd{
  margin-top:30px;
  margin-left:20px;
  .el-input {
    display: inline-block;
    height: 47px;
    width:69.5%;
  }
}
</style>

