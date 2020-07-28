<template>
  <div class="main">
    <div class="basic">
      <div class="avatar" float>
        <el-avatar :size="120" :src="circleUrl" />
      </div>
      <div class="team">
        <div class="name">{{ team }}</div>
        <ul class="ul">
          <li><span class="align-right">联盟排名:</span><span class="span-right">第{{ sort }}名</span></li>
          <li><span class="align-right">教练:</span><span class="span-right">{{ coach }}</span></li>
          <li><span class="align-right">主场:</span><span class="span-right">{{ home }}</span></li>
          <li><span class="align-right">城市:</span><span class="span-right">{{ city }}</span></li>
        </ul>
      </div>
      <div class="score-container">
        <div class="score"><span class="score-left"><em>{{ score.win }}</em> 胜</span> <span class="score-right"><em>{{ score.fail }}</em> 负</span></div>
        <el-progress class="progress" :text-inside="true" :stroke-width="15" :percentage="100*score.win/(score.win+score.fail)" />
        <div class="score-text">{{ score.season }}常规赛</div>
      </div>
    </div>
    <div>
      <el-table
        class="table"
        :data="tableData"
        height="500"
        style="width: 100%"
      >
        <el-table-column prop="date" width="80">
          <el-link href="https://element.eleme.io" target="_blank" :underline="false">
            <el-avatar :size="50" :src="circleUrl" />
          </el-link>
        </el-table-column>
        <el-table-column prop="name" label="姓名" width="150" />
        <el-table-column prop="number" label="号码" width="80" />
        <el-table-column prop="position" label="位置" width="80" />
        <el-table-column prop="height" label="身高(cm)" width="110" />
        <el-table-column prop="weight" label="体重(kg)" width="110" />
        <el-table-column prop="age" label="年龄" width="100" />
        <el-table-column prop="score" label="场均得分" width="110" />
        <el-table-column prop="rebound" label="场均篮板" width="110" />
        <el-table-column prop="assist" label="场均助攻" width="110" />
        <el-table-column prop="steal" label="场均抢断" width="110" />
        <el-table-column prop="foul" label="场均犯规" width="110" />
      </el-table>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      team: '开拓者',
      sort: 9,
      coach: '特里-斯托茨',
      home: '摩达中心',
      city: '广州',
      circleUrl: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
      score: {
        win: 53,
        fail: 12,
        season: '19-20'
      },
      tableData: [{
        date: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
        name: '王小虎',
        number: 5,
        position: '前锋',
        weight: '100.2',
        height: '205.3'
      },
      {
        date: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
        name: '王小虎',
        number: 15,
        position: '前锋',
        weight: '100.2',
        height: '205.3'
      },
      {
        date: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
        name: '王小虎',
        number: 25,
        position: '前锋',
        weight: '100.2',
        height: '205.3'
      },
      {
        date: '2016-05-01',
        name: '王小虎',
        number: 5,
        position: '前锋',
        weight: '100.2',
        height: '205.3'
      },
      {
        date: '2016-05-08',
        name: '厄桑-伊厄桑-伊利亚索瓦',
        number: 57,
        position: '前锋',
        weight: '100.2',
        height: '205.3'
      },
      {
        date: '2016-05-06',
        name: '王小虎',
        number: 7,
        position: '前锋',
        weight: '100.2',
        height: '205.3'
      },
      {
        date: '2016-05-07',
        name: '王小虎',
        number: 45,
        position: '前锋',
        weight: '100.2',
        height: '205.3'
      },
      {
        date: '2016-05-07',
        name: '王小虎',
        number: 5,
        position: '前锋',
        weight: '100.2',
        height: '205.3'
      },
      {
        date: '2016-05-07',
        name: '王小虎',
        number: 5,
        position: '前锋',
        weight: '100.2',
        height: '205.3'
      },
      {
        date: '2016-05-07',
        name: '王小虎',
        number: 5,
        position: '前锋',
        weight: '100.2',
        height: '205.3'
      },
      {
        date: '2016-05-07',
        name: '王小虎',
        number: 5,
        position: '前锋',
        weight: '100.2',
        height: '205.3'
      }
      ]
    }
  }
}
</script>
<style lang="scss">
.table{
  margin-top: 20px;
  float: left;
  margin-left: 0px;
  border-top: 1px solid #a0a0a0;
}
.avatar{
  float: left;
  margin-right: 20px;
}
.team{
  width: 170px;
  float: left;
  border-right: 1px solid blue;
  .name{
    font-size: 28px;
    font-family:"Helvetica Neue","Microsoft YaHei",Arial,sans-serif;
    vertical-align: top;
  }
  .ul{
    list-style: none;
    margin-top: 10px;
    padding: 0px;
    .align-right{
      width: 56px;
      text-align: right;
      color: #a0a0a0;
      margin-right: 10px;
    }
  }
}
.score-container {
    margin-left: 62px;
    width: 260px;
    float: left;
}
.score {
    width: 260px;
    margin-top: 20px;
    margin-bottom: 10px;
    color: #a0a0a0;
}
.progress{
  margin-bottom: 10px;
}
.score-text{
  margin-left: 85px;
}
.score-left {
    width: 100px;
    font-size: 30px;
    color: blueviolet;
}
.score-right {
    width: 100px;
    font-size: 30px;
    color: red;
    text-align: right;
    float: right;
}
 </style>
