<template>
  <div class="med">
    <el-row >
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin/dashboard' }">管理中心</el-breadcrumb-item>
        <el-breadcrumb-item :to="{path:'/admin/content/book'}">活动管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{path: '/admin/content/article'}">文章管理</el-breadcrumb-item>
        <el-breadcrumb-item >编辑器</el-breadcrumb-item>
      </el-breadcrumb>
    </el-row>
    <el-row>
      <el-input v-model="article.articleTitle" style="margin:10px 0px;font-size:18px;" placeholder="请输入标题"></el-input>
    </el-row>
    <el-row>
      <mavon-editor  v-model="article.articleContentMd" style="height:600px;" ref="md" @save="saveArticles" fontSize="16px"
                     >
        <button type="button" class="op-icon el-icon-document" :title="'摘要/封面'" slot="left-toolbar-after"
                @click="dialogVisible = true"></button>
      </mavon-editor>
    </el-row>
    <el-dialog :visible.sync="dialogVisible" width="30%">
      <el-divider content-position="left">摘要</el-divider>
      <el-input
        type="textarea"
        v-model="article.articleAbstract"
        rows="6"
        maxlength="255"
        show-word-limit></el-input>
      <el-divider content-position="left">封面</el-divider>
      <div style="margin-top: 20px">
        <el-input v-model="article.articleCover" autocomplete="off" placeholder="图片 URL"></el-input>
        <ImageUpload @onUpload="uploadImg" ref="imgUpload" style="margin-top: 5px"></ImageUpload>
      </div>
      <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
        </span>
    </el-dialog>

  </div>

</template>
<script>
    import ImageUpload from "@/components/common/ImageUpload";
    import dateFFt from "@/components/utils/date"
    export default {
        name: "ArticleEditor",
      components: {ImageUpload},
      data(){
          return{
            article:{
              articleTitle:'',
              articleContentMd:'',
              articleAbstract:'',
              articleCover:'',
              articleContentHtml:'',
              articleDate:dateFFt.dateFtt("yyyy-MM-dd hh:mm:ss",new Date())
            },
            dialogVisible:false,

          }
      },
      mounted(){
          if(this.$route.params.article){
            this.article = this.$route.params.article
          }
      },
      methods:{
        saveArticles (value, render) {
          console.log(this.article)
          // value 是 md，render 是 html
          this.$confirm('是否保存并发布文章?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
              this.$axios
                .post('/admin/content/article', {
                  articleTitle: this.article.articleTitle,
                  articleContentMd: value,
                  articleContentHtml: render,
                  articleAbstract: this.article.articleAbstract,
                  articleCover: this.article.articleCover,
                  articleDate: this.article.articleDate
                }).then(resp => {
                if (resp && resp.status === 200) {
                  this.$message({
                    type: 'info',
                    message: '已保存成功'
                  })
                }
              })
            }
          ).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消发布'
            })
          })
        },
        uploadImg () {
          this.article.articleCover = this.$refs.imgUpload.url
        }
      }
    }
</script>

<style scoped>
.med{
  position: relative;
  background-color: white;
}
</style>
