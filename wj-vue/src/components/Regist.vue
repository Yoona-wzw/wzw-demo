<template>
  <body>
  <el-form :model="registFrom" ref="registFrom" :rules="rules" class="login-container" label-position="left"
           label-width="0px">
    <h3 class="login-title">Create your account</h3>
    <el-form-item prop="username">
      <el-input type="text" v-model="registFrom.username" placeholder="username"/>
    </el-form-item>
    <el-form-item prop="password">
      <el-input type="password" v-model="registFrom.password" placeholder="password" show-password/>
    </el-form-item>
    <el-form-item style="width:100%">
      <el-button type="primary" plain style="width:100%;background: #505458;border:none" @click="regist('registFrom')">
        Sign up
      </el-button>
    </el-form-item>
    <span @click="goback" style="color:#6097ff;cursor: pointer"> <i class="el-icon-back"> Go Back</i> </span>
  </el-form>
  </body>
</template>

<script>
  export default {
    name: "Regist",
    data() {
      var validateUsername=(role,value,callback)=>{
        this.$axios.post('/regValidate',{username:value}).then(resp=>{
          if(resp.data=='用户已存在'){
            callback(new Error('用户名已注册'))
          }else{
            callback();
          }

        })
      }
      return {
        message: '',
        registFrom: {
          username: '',
          password: ''
        },
        rules: {
          username: [{required:true,message:'请输入用户名',trigger:'blur'},
            {min:2,max:10,message:'长度在2~10个字符',trigger:'blur'},
            {
              required:true,
              pattern:/^[\u4e00-\u9fa5_a-zA-Z0-9.·-]+$/,
              message:'姓名不能含有特殊字符',
              trigger:'blur'
            },{
            validator:validateUsername,trigger:'blur'
            }],
          password: [{required:true,message:'请输入密码',trigger:'blur'},
            {min:4,max:6,message:'长度在4~6个字符',trigger:'blur'}]
        },
        responseResult: []
      }
    },
    methods: {
      regist(registFrom) {
        this.$refs[registFrom].validate((valid) => {
          if (valid) {
            this.$axios.post('/reg', {
              username: this.registFrom.username,
              password: this.registFrom.password
            }).then(successResponse => {
              if (successResponse.data.code == 200) {
                this.$notify({
                  title: '',
                  message: '注册成功！',
                  type: 'success',
                  duration: 2500
                })
                this.$router.replace({path: '/login'})
              } else {
                this.$notify({
                  title: '',
                  message: successResponse.data.message,
                  type: 'error',
                  duration: 2500
                })
              }
            }).catch(failResponse => {
              console.log("error")
            })
          }
      else
        {
          return false;
        }
      })
      },
      goback() {
        this.$router.replace('/login')
      }
    }
  }
</script>

<style scoped>

  .login-container {
    padding: 35px 35px 15px 35px;
    width: 350px;
    margin: 150px auto;
    border: 1px solid #eaeaea;
    border-radius: 15px;
    background: #fff;
    box-shadow: 0 0 25px #cac6c6;
    background-clip: padding-box;
  }

  body {
    margin: 0px;
    background: url("../assets/login.jpg") no-repeat;
    background-position: center;
    width: 100%;
    height: 100%;
    background-size: cover;
    position: fixed;
  }

  .login-title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }
</style>

