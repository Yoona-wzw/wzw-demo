<template>
    <el-upload
    class="image-upload"
    action="http://localhost:8843/api/admin/content/books/covers"
    with-credentials
    :on-preview="handlePreview"
    :on-remove="handleRemove"
    :before-remove="beforeRemove"
    :on-success="handleSuccess"
    multiple
    :limit="1"
    :on-exceed="handleExceed"
    :file-list="fileList">
    <el-button size="small" type="primary" >点击上传</el-button>
      <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
    </el-upload>
</template>

<script>
    export default {
        name: "ImageUpload",
      data(){
          return{
            fileList:[],
            url:''
          }
      },
      methods:{
        handleRemove (file, fileList) {
        },
        handlePreview (file) {
        },
        handleExceed (files, fileList) {
          this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
        },
        beforeRemove (file, fileList) {
          return this.$confirm(`确定移除 ${file.name}？`)
        },
        handleSuccess (response) {
          this.url = response
          this.$emit('onUpload')
          this.$message.warning('上传成功')
        },
        clear () {
          this.$refs.upload.clearFiles()
        },
       /* beforeAvatarUpload(file){
            const isJPG=file.type==='image/jpeg';
            const isPNG=file.type==='image/png';
            const isLt=file.size /1024/1024 <0.5;
            if(!isJPG||!isPNG){
              this.$message.error("封面图片只能上传jpg/png")
            }
            if(!isLt){
              this.$message.error("上传图片大小不能超过500kb")
            }
            return isPNG&&isJPG&&isLt
        }*/
      }
    }
</script>

<style scoped>

</style>
