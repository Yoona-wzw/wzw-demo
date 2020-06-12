<template>
  <body>
  <el-form :model="loginFrom" ref="loginFrom" class="login-container" label-position="left" label-width="0px" :rules="rules">
    <h3 class="login-title">White Jotter</h3>
    <el-form-item prop="username">
     <el-input type="text" v-model="loginFrom.username" placeholder="username"/>
    </el-form-item>
    <el-form-item prop="password">
    <el-input type="password" v-model="loginFrom.password" placeholder="password" show-password/>
    </el-form-item>
    <el-checkbox class="checkbox" v-model="checked">Rember Me</el-checkbox>
    <el-form-item style="width:100%">
      <el-button type="primary" plain style="width:100%;background: #505458;border:none" @click="login('loginFrom')">Sign in</el-button>
    </el-form-item>
    <!--<el-form-item style="width:100%">
      <el-button type="primary" plain style="width:100%;background: #505458;border:none" @click="regist">Sign up</el-button>
    </el-form-item>
-->
    <a @click="regist">Click here to create an account<i class="el-icon-right" style="width:5px;"></i></a>
  </el-form>
  </body>
</template>

<script>
  export default {
    name: "Login",
    data() {
      return {
        message:'',
        loginFrom: {
          username: '',
          password: ''
        },
        rules:{
          username:[{required:true,message:'请输入用户名',trigger:'blur'},
            {min:2,max:10,message:'长度在2~10个字符',trigger:'blur'},
            {
              required:true,
              pattern:/^[\u4e00-\u9fa5_a-zA-Z0-9.·-]+$/,
              message:'姓名不能含有特殊字符',
              trigger:'blur'
            }],
          password:[{required:true,message:'请输入密码',trigger:'blur'},
            {min:4,max:6,message:'长度在4~6个字符',trigger:'blur'}]
        },
        responseResult: [],
        checked:false
      }
    },
    methods: {
      login(loginFrom) {
        this.$refs[loginFrom].validate((valid) => {
          if(valid){
            this.$axios.post('/login?rm='+this.checked, {
              username: this.loginFrom.username,
              password: this.loginFrom.password
            }).then(successResponse => {
              if (successResponse.data.code == 200) {
                this.$notify({
                  title:this.loginFrom.username,
                  message:'欢迎登录！',
                  type:'success',
                  duration:2500
                })
                this.$store.commit('login',this.loginFrom)
                var path=this.$route.query.redirect
                this.$router.replace({path:path==='/login'||path===undefined?'/index':path})
              }else{
                this.$notify({
                  title:'',
                  message:'用户名或密码错误！',
                  type:'error',
                  duration:2500
                })
              }
            }).catch(failResponse => {
              console.log("error")
            })
          }else{
            return false;
          }
        })

      },
      regist(){
        this.$router.replace({path:'/regist'})

      }
    }
  }
</script>

<style scoped>

#sp1{
  font-size: 14px;
  color:red;
}
  .login-container{
    padding:35px 35px 15px 35px;
    width:350px;
    margin:150px auto;
    border:1px solid #eaeaea;
    border-radius: 15px;
    background:#fff;
    box-shadow: 0 0 25px #cac6c6;
    background-clip: padding-box;
  }
  body{
    position:fixed;
    top:0;
    left:0;
    background: url("../assets/login.jpg") no-repeat center;
    width:100%;
    height:100%;
    background-size:cover;
    -webkit-background-size: cover;
    -o-background-size: cover;
    background-position: center 0;
    zoom:1;

  }
.login-title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}
  .checkbox{
    margin-right:255px;
  }
  a{
    cursor:pointer;
    text-decoration: none;
    color: #6097ff;
  }
</style>
