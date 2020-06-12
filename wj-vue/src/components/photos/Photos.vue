<template>
  <div class="div-photos">
    <el-backtop :bottom="100" target=".div-photos">Top</el-backtop>
    <el-row>
      <el-input @keyup.enter.native="searchPhotos" placeholder="search..."
                prefix-icon="el-icon-search" size="small" style="width:300px;margin-right:10px;" v-model="key"
                clearable>

      </el-input>
      <el-button size="small" type="primary" icon="el-icon-search" @click="searchPhotos">搜索</el-button>
    </el-row>

    <div class="lazy_div1">
      <el-image style="weight:100%;height:80%;" v-for="item in books.slice((currentPage-1)*pagesize,currentPage*pagesize)" :key="item.id" :src="item.path"  lazy></el-image>
    </div>
    <el-row class="page-div">
    <el-pagination @current-change="handleCurrentChange"
                   :current-page="currentPage" :page-size="pagesize" :total="books.length"></el-pagination>
    </el-row>
  </div>
</template>

<script>
    export default {
        name: "Photos",
  data(){
    return{
      books:[],
      currentPage:1,
      pagesize:3,
      q:'',
      key:''
    }
  },
      mounted:function(){
        this.showPhotos();
      },
      methods:{
        showPhotos(){
          this.$axios.get('photos/search?page='+this.currentPage+'&q='+this.q).then(resp=>{
            this.books=resp.data
          })
        },
        handleCurrentChange: function (currentPage) {
          this.currentPage = currentPage
        },
        searchPhotos(){
          this.q=this.key
          this.showPhotos();
        }
      }

    }
</script>

<style scoped>

.lazy_div1{
  margin-top:20px;
}
  .div-photos{
    position:relative;
  }
  .page-div{
    position: relative;
    bottom:0px;
  }
</style>
