<template>
  <el-container>
    <el-header>
      <el-menu
        text-color="#E6A23C"
        active-text-color="#409EFF"
        mode="horizontal"
        router
        @select="handleSelect"
      >
        <el-menu-item class="menu_item" index="/myteam/team">
          <i class="el-icon-basketball" style="font-size: 22px; color: red" />我的主队
        </el-menu-item>
        <el-menu-item class="menu_item" index="/myteam/match">
          <i class="el-icon-medal" style="font-size: 23px; color: blue" />赛程
        </el-menu-item>
        <el-menu-item class="menu_item" index="/myteam/data">
          <i class="el-icon-data-analysis" style="font-size: 23px; color: blue" />数据分析
        </el-menu-item>
        <el-menu-item class="menu_item" index="/myteam/person">
          <i class="el-icon-loading" style="font-size: 23px; color: blue" />人员变动
        </el-menu-item>
        <el-menu-item class="playerName">
          球员姓名:
          <el-input v-model="name" class="input" />
          <el-button class="_button" type="text" icon="el-icon-search" size="medium" />
        </el-menu-item>
      </el-menu>
    </el-header>
    <el-main class="test">
      <router-view />
    </el-main>
  </el-container>
</template>
<script>
export default {
  data() {
    return {
      name: ''
    }
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath)
    }
  }
}
</script>
<style lang="scss">
.test{
  background: rgb(244, 244, 244);
}
 .menu_item{
   font-size: 20px;
 }
  .playerName{
    margin-left: 50px;
    font-size: 15px;
  }
  .input{
    width: 300px;
    margin-left: 10px;
  }
._button{
  margin-left: 5px;
}
</style>
