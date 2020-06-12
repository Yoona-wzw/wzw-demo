<template>
    <el-container>
      <el-aside>
      <switch></switch>
        <SideMenu @indexSelect="listByCategory" ref="sideMenu"></SideMenu>
      </el-aside>
      <el-main class="main-div">
      <Books class="books-area" ref="booksArea"></Books>
      </el-main>
    </el-container>
</template>

<script>
    import SideMenu from "./SideMenu";
    import Books from "./Books";
    export default {
        name: "LibraryIndex",
      components: {Books, SideMenu},
      methods:{
        listByCategory(){
          var cid=this.$refs.sideMenu.cid
          var url='categories/'+cid+'/books'
          this.$axios.get(url).then(resp=>{
            if(resp&&resp.status==200){
              this.$refs.booksArea.books=resp.data
            }
          })
        }
      }
    }
</script>

<style scoped>
.books-area{
  width:1100px;
  margin-left:auto;
  margin-rigth:auto;
}
  .main-div{
    position: relative;
  }
</style>
