<template>
  <ul v-infinite-scroll="load" style="overflow:auto" class="infinite-list" infinite-scroll-disabled="disabled">
    <el-card v-for="i in count" :key="i" class="box-card">
      <div  class="card__header" style="height:12px ;">
        <el-badge value="新" class="item" :type="status" hidden="1>2 ? 'true' : false">
          <span class="title"> {{ list[i%2].title + list[i%2].status }}</span>
          <span class="time"> {{ list[i%2].time }}</span>
        </el-badge>
        <el-button style="float: right; margin-right:5px;" type="danger" icon="el-icon-delete" size="mini" circle @click="deletemsg"></el-button>
      </div>
      <el-divider></el-divider>
      <div class="text item"> {{ list[i%2].msg }} </div>
    </el-card>
  </ul>
</template>

<script>
export default {
  data() {
    const list = [
      { title: '一个标题',
        time: '2020年6月26日 18:00',
        status: 'false',
        msg: '周深、腾格尔、罗翔、党妹将分享他们与B站的故事！六级UP主周深唱着《起风了》回家啦，腾格尔分深唱着《起风了》回家啦，腾格尔分享如享如何在B站做全新的自己，罗翔讲述都在他在B站的授业与 '
      },
      { title: '周深、腾格尔、罗翔、党妹将分享他们与B站的故事！六级UP主周深唱',
        time: '2020年7月26日 18:00',
        status: 'true',
        msg: '周深、腾格尔、罗翔、党妹将分享他们与B站的故事！六级UP主周深唱着《起风了》回家啦，腾格尔分深唱着《起风了》回家啦，腾格尔分享如享如何在B站做全新的自己，罗翔讲述都在他在B站的授业与 '
      }
    ]
    return {
      list: list,
      count: 4,
      loading: false
    }
  },
  computed: {
      noMore () {
        return this.count >= 20
      },
      disabled () {
        return this.loading || this.noMore
      }
    },
  methods: {
    load () {
        this.loading = true
        setTimeout(() => {
          this.count += 2
          this.loading = false
        }, 2000)
      },
    deletemsg() {
      this.$message({
        showClose: true,
        message: '消息移除成功',
        type: 'success'
      })
    }
  }
}
</script>
<style>
.title {
  color: #333;
  font-weight: 700;
}
.bottom {
  color: #666;
  padding-left: 8px;
}
.time{
  color: #999;
  font-size: 14px;
  line-height: 22px;
  margin: 0 10px;

}
.text{
  word-break: break-word;
}
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }
  .box-card {
    height:140px;
    width: 95%;
    margin-top:20px;
  }
  .card__header {
    height: 50px;

    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    
   }
</style>
