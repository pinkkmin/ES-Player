<template>
  <div>
    <div>
      <ul v-infinite-scroll="load" class="infinite-list">
        <el-card class="head">
          <span class="icon">
            <i class="el-icon-trophy-1" style="font-size: 35px; color: #89177D" />
          </span>
          <span class="head-team">{{ team }}</span>
          <span class="text">{{ date }}</span>
          <el-date-picker
            v-model="date"
            type="month"
            class="picker"
            format="yyyy 年 MM 月"
            value-format="yyyy-MM"
            placeholder="选择日期"
          />
          <el-button class="button" plain @click="backToday">回到本月</el-button>
        </el-card>
      </ul>
    </div>
    <div>
      <ul
        v-infinite-scroll="load"
        v-infinite-scroll-disabled="disabled"
        class="list"
      >
        <el-card v-for="i in count" :key="i" class="match-box-card">
          <div slot="header">
            <span>{{ macthDate[i-1].month }}月</span>
          </div>
          <el-table
            :data="macthDate[i-1].data"
            stripe
            :show-header="false"
            style="width: 100%"
          >
            <el-table-column prop="date" label="  日期" width="150" />
            <el-table-column prop="status" label="状态" width="100" />
            <el-table-column prop="away_icon" label="" width="170">
              <el-avatar :size="55" :fit="cover" :src="getIcon()" />
            </el-table-column>
            <el-table-column prop="away_name" label="客队" width="120" />
            <el-table-column prop="score" label="比分" width="150" />
            <el-table-column prop="home_icon" label="" width="170">
              <el-avatar :size="55" :fit="cover" :src="home_icon" />
            </el-table-column>
            <el-table-column prop="home_name" label="主队" width="120" />
            <el-table-column prop="data" label="数据统计" width="170">
              <el-link href="/404" target="_blank" :underline="false">赛后数据</el-link>
            </el-table-column>
          </el-table>
        </el-card>
      </ul>
    </div>
    <p v-if="loading" style="margin-left: 50%;">
      <i class="el-icon-loading" style="font-size: 30px;" />加载中...</p>
    <p v-if="noMore" style="margin-left: 50%;"> <i class="el-icon-lollipop" style="font-size: 30px;" />没有更多了</p>
  </div>
</template>
<script>
export default {
  data() {
    return {
      team: '开拓者',
      count: 2,
      loading: false,
      date: new Date().getFullYear() + '-' + new Date().getMonth(),
      home_icon: 'http://mat1.gtimg.com/sports/nba/logo/new/22.png',
      macthDate: [{
        month: 4,
        data: [
          {
            date: new Date().getMonth() + '月 ' + new Date().getDay() + ' 日 ' + new Date().getHours() + ':00',
            away_name: '掘金',
            home_name: '开拓者',
            score: '124-110',
            data: '测试',
            status: '未开始',
            away_icon: 'http://mat1.gtimg.com/sports/nba/logo/1602/14.png',
            home_icon: 'http://mat1.gtimg.com/sports/nba/logo/new/22.png'
          },
          {
            date: new Date().getMonth() + '月 ' + new Date().getDay() + ' 日 ' + new Date().getHours() + ':00',
            away_name: '掘金',
            home_name: '开拓者',
            score: '111-110',
            status: '已结束',
            away_icon: 'http://mat1.gtimg.com/sports/nba/logo/1602/26.png',
            home_icon: 'http://mat1.gtimg.com/sports/nba/logo/new/22.png'
          }
        ]
      },
      {
        month: 5,
        data: [
          {
            date: new Date().getMonth() + '月 ' + new Date().getDay() + ' 日 ' + new Date().getHours() + ':00',
            away_name: '掘金',
            home_name: '开拓者',
            score: '124-110',
            status: '已结束',
            away_icon: 'http://mat1.gtimg.com/sports/nba/logo/1602/14.png',
            home_icon: 'http://mat1.gtimg.com/sports/nba/logo/new/22.png'
          },
          {
            date: new Date().getMonth() + '月 ' + new Date().getDay() + ' 日 ' + new Date().getHours() + ':00',
            away_name: '爵士',
            home_name: '开拓者',
            score: '111-110',
            status: '已结束',
            away_icon: 'http://mat1.gtimg.com/sports/nba/logo/1602/26.png',
            home_icon: 'http://mat1.gtimg.com/sports/nba/logo/new/22.png'
          },
          {
            date: new Date().getMonth() + '月 ' + new Date().getDay() + ' 日 ' + new Date().getHours() + ':00',
            away_name: '掘金',
            home_name: '开拓者',
            score: '101-110',
            status: '已结束',
            away_icon: 'http://mat1.gtimg.com/sports/nba/logo/1602/27.png',
            home_icon: 'http://mat1.gtimg.com/sports/nba/logo/new/22.png'
          }
        ]
      }
      ]
    }
  },
  computed: {
    noMore() {
      return this.count >= 20
    },
    disabled() {
      return this.loading || this.noMore
    }
  },
  methods: {
    getIcon() {
      return 'http://mat1.gtimg.com/sports/nba/logo/1602/20187.png'
    },
    load() {
      this.loading = true
      setTimeout(() => {
        this.count = 10
        this.loading = false
      }, 2000)
    },
    backToday() {
      this.date = new Date().getFullYear() + '-' + new Date().getMonth()
    }
  }
}
</script>
<style lang="scss">
.head{
    width: 100%;
    height:65px;
    margin-bottom: 10px;
    background: url(//mat1.gtimg.com/sports/images/img_r/behindli/nbaschedule/headline_icon.png) no-repeat 0 0;
    .icon{
        float: left;
    }
    .head-team {
     margin-left: 15px;
     font-size: 25px;
    }
    .text{
        color: red;
        font-size: 27px;
        text-align: center;
        margin-left: 32%;
        float: center;
    }
  }
  .picker{
      width: 20px;
      float: right;
  }
  .button{
      margin-right: 10px;
      float: right;
  }
  .match-text {
    font-size: 14px;
  }
  .match-item {
    margin-bottom: 18px;
  }
  .el-card__header {
    height: 50px;
    border-bottom: 1px solid #EBEEF5;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    background-color: #6db2ee;
   }
.match-box-card {
    width: 100%;
    border-radius: 3px;
    margin-bottom: 20px;
    float: left;
}
</style>
